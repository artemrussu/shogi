package controller;

import java.util.Set;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import controller.state.GameState;
import core.Color;
import core.Coordinates;
import core.DropMove;
import core.DropValidator;
import core.InputCoordinates;
import core.Move;
import core.PieceType;
import menu.GameConfig;
import model.Board;
import model.piece.Piece;
import sound.SoundManager;
import view.gui.AssetManager;
import view.gui.BoardPerspective;
import view.gui.GraphicRender;

public class GraphicGame extends Game {

    private final GraphicRender renderer = new GraphicRender();
    private final AssetManager assets = new AssetManager();

    private Coordinates selectedSquare = null;
    private Move pendingMove = null;
    private PieceType selectedHandPiece = null;
    private Set<Coordinates> availableMoves = Set.of();
    
    private final BoardPerspective initialPerspective;
    private final boolean          rotatePerspective;
    
    private final SoundManager sound = new SoundManager();

    public GraphicGame(Board board, GameConfig config) {
        super(board);
        this.rotatePerspective = (config.mode == GameConfig.Mode.VS_HUMAN);

        if (rotatePerspective) {
            // local 2-player: SENTE always starts at bottom — SENTE moves first
            this.initialPerspective = new BoardPerspective(Color.SENTE);
        } else {
            // vs AI: player's chosen color stays at bottom always
            this.initialPerspective = new BoardPerspective(config.playerColor);
        }
    }

    @Override
    public void gameLoop() {
        renderer.init(board, assets);
        if (!assets.loadAssets()) {
            System.err.println("Critical error: Assets could not be loaded.");
            return;
        }
        
        sound.init();

        renderer.setPerspective(initialPerspective);
        
        currentGameState = determineGameState(board, currentTurn);

        while (!Display.isCloseRequested()) {
            if (currentGameState == GameState.ONGOING) handleInput();

            Piece selectedPiece = (selectedSquare != null) ? board.getPiece(selectedSquare) : null;
            renderer.render(board, selectedPiece, availableMoves, currentTurn);
            renderer.processEvents();
            Display.sync(60);
        }

        assets.dispose();
        sound.shutdown();
        Display.destroy();
    }

    private void handleInput() {
        while (Mouse.next()) {
            if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {

                int mouseX = Mouse.getEventX();
                int mouseY = Display.getHeight() - Mouse.getEventY();

                if (pendingMove != null) {
                    handlePromotionDialog(mouseX, mouseY);
                    continue;
                }

                PieceType handPiece = renderer.getHandPieceFromMouse(currentTurn);
                if (handPiece != null) {
                    selectedHandPiece = handPiece;
                    selectedSquare    = null;
                    availableMoves    = DropValidator.getValidDropSquares(handPiece, currentTurn, board); // <- это должно быть
                    continue;
                }

                Coordinates clickedCoords = renderer.getCoordinatesFromMouse();
                if (clickedCoords == null) {
                    selectedSquare = null;
                    selectedHandPiece = null;
                    availableMoves = Set.of();
                    continue;
                }

                if (selectedHandPiece != null) {
                    tryToDrop(clickedCoords);
                } else if (selectedSquare == null) {
                    selectPiece(clickedCoords);
                } else {
                    tryToMove(clickedCoords);
                }
            }
        }
    }

    private void handlePromotionDialog(int mouseX, int mouseY) {
        Boolean choice = renderer.getPromotionDialogChoice(mouseX, mouseY);
        if (choice == null) return;

        if (choice) sound.playPromote();

        Move finalMove = new Move(pendingMove.from, pendingMove.to, choice);
        pendingMove = null;
        renderer.hidePromotionDialog();
        executeMove(finalMove);
    }

    private void selectPiece(Coordinates clickedCoords) {
        if (!board.isSquareEmpty(clickedCoords)) {
            Piece piece = board.getPiece(clickedCoords);
            if (piece.getColor() == currentTurn) {
                selectedSquare = clickedCoords;
                availableMoves = piece.getAvailableMoveSquares(board);
            }
        }
    }

    private void tryToMove(Coordinates clickedCoords) {
        Piece pieceToMove = board.getPiece(selectedSquare);

        if (!availableMoves.contains(clickedCoords)) {
            if (!board.isSquareEmpty(clickedCoords) &&
                    board.getPiece(clickedCoords).getColor() == currentTurn) {
                selectedSquare = clickedCoords;
                availableMoves = board.getPiece(clickedCoords).getAvailableMoveSquares(board);
            } else {
                selectedSquare = null;
                availableMoves = Set.of();
            }
            return;
        }

        Move move = new Move(selectedSquare, clickedCoords);

        if (InputCoordinates.validateIfKingInCheckAfterMove(board, currentTurn, move)) {
            renderer.showMessage("Invalid move: king is under attack!");
            selectedSquare = null;
            availableMoves = Set.of();
            return;
        }

        if (pieceToMove.mustPromote(clickedCoords)) {
            sound.playCapture(!board.isSquareEmpty(clickedCoords));
            sound.playPromote();
            executeMove(new Move(move.from, move.to, true));
            return;
        }

        if (pieceToMove.canPromote(clickedCoords)) {
            sound.playCapture(!board.isSquareEmpty(clickedCoords));
            pendingMove = move;
            renderer.showPromotionDialog();
            return;
        }

        sound.playCapture(!board.isSquareEmpty(clickedCoords));
        executeMove(move);
    }

    private void tryToDrop(Coordinates target) {
        DropMove drop = new DropMove(selectedHandPiece, currentTurn, target);

        if (!DropValidator.isValidDrop(drop, board)) {
            renderer.showMessage("Invalid drop!");
            selectedHandPiece = null;
            return;
        }

        sound.playDrop();
        executeMove(drop);
        selectedHandPiece = null;
    }

    @Override
    protected void executeMove(Move move) {
        super.executeMove(move);
        availableMoves = Set.of();
        selectedSquare = null;

        if (rotatePerspective) {
            renderer.setPerspective(new BoardPerspective(currentTurn));
        }

        if (currentGameState != GameState.ONGOING) {
            sound.playGameEnd();
        }
    }
}