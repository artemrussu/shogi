package controller;

import java.util.List;
import java.util.Set;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import controller.state.CheckmateGameStateChecker;
import controller.state.GameState;
import controller.state.GameStateChecker;
import controller.state.StalemateGameStateChecker;
import core.Color;
import core.Coordinates;
import core.Move;
import core.factory.BoardFEN;
import model.Board;
import model.piece.Piece;
import model.piece.impl.King;
import view.gui.AssetManager;
import view.gui.GraphicRender;

public class GraphicGame {
    private final Board board;
    private final GraphicRender renderer = new GraphicRender();
    private final AssetManager assets = new AssetManager();

    private Coordinates selectedSquare = null;
    private Color currentTurn = Color.SENTE;
    
    private Move pendingMove = null; // move waiting for promotion decision
    
    private GameState currentGameState = GameState.ONGOING;
    private final List<GameStateChecker> checkers = List.of(
            new StalemateGameStateChecker(),
            new CheckmateGameStateChecker()
    );

    public GraphicGame(Board board) {
        this.board = board;
    }

    public void gameLoop() {
        // 1. Initialize the renderer FIRST to create the OpenGL display context
        renderer.init(board, assets); 

        // 2. NOW load the assets (textures) because the OpenGL context is ready
        if (!assets.loadAssets()) {
            System.err.println("Critical error: Assets could not be loaded.");
            return;
        }
        
        currentGameState = determineGameState(board, currentTurn);

        while (!Display.isCloseRequested()) {
            if (currentGameState == GameState.ONGOING) {
                handleInput();
            }

            Piece selectedPiece = (selectedSquare != null) ? board.getPiece(selectedSquare) : null;
            renderer.render(board, selectedPiece);

            renderer.processEvents();
            Display.sync(60);
        }

        cleanup();
    }

    private GameState determineGameState(Board board, Color color) {
        for (GameStateChecker checker : checkers) {
            GameState state = checker.check(board, color);
            if (state != GameState.ONGOING) {
                return state;
            }
        }
        return GameState.ONGOING;
    }

    private void handleInput() {
        while (Mouse.next()) {
            if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {

                // if waiting for player's response — handle click on dialog button
                if (pendingMove != null) {
                    handlePromotionDialog();
                    continue;
                }

                Coordinates clickedCoords = renderer.getCoordinatesFromMouse();
                if (clickedCoords == null) { selectedSquare = null; continue; }

                if (selectedSquare == null) {
                    selectPiece(clickedCoords);
                } else {
                    tryToMove(clickedCoords);
                }
            }
        }
    }
    
    private void selectPiece(Coordinates clickedCoords) {
        if (!board.isSquareEmpty(clickedCoords)) {
            Piece piece = board.getPiece(clickedCoords);
            if (piece.getColor() == currentTurn) {
                selectedSquare = clickedCoords;
            }
        }
    }

    private void tryToMove(Coordinates clickedCoords) {
        Piece pieceToMove = board.getPiece(selectedSquare);
        Set<Coordinates> availableMoves = pieceToMove.getAvailableMoveSquares(board);

        if (!availableMoves.contains(clickedCoords)) {
            // switch selection or deselect
            if (!board.isSquareEmpty(clickedCoords)
                    && board.getPiece(clickedCoords).getColor() == currentTurn) {
                selectedSquare = clickedCoords;
            } else {
                selectedSquare = null;
            }
            return;
        }

        Move move = new Move(selectedSquare, clickedCoords);

        if (validateIfKingInCheckAfterMove(board, currentTurn, move)) {
            System.out.println("Invalid move: king is under attack!");
            selectedSquare = null;
            return;
        }

        // must promote
        if (pieceToMove.mustPromote(clickedCoords)) {
            executeMove(new Move(move.from, move.to, true));
            return;
        }

        // may promote — wait for player's decision
        if (pieceToMove.canPromote(clickedCoords)) {
            pendingMove = move; // remember the move, show dialog
            renderer.showPromotionDialog(); // you implement this in renderer
            return;
        }

        // without promotion
        executeMove(move);
    }

    private void handlePromotionDialog() {
        // renderer returns: true = "yes", false = "no", null = not clicked yet
        Boolean choice = renderer.getPromotionDialogChoice();
        if (choice == null) return; // wait

        Move finalMove = new Move(pendingMove.from, pendingMove.to, choice);
        pendingMove = null;
        renderer.hidePromotionDialog();
        executeMove(finalMove);
    }

    private void executeMove(Move move) {
        board.makeMove(move);
        currentTurn = currentTurn.opposite();
        selectedSquare = null;
        currentGameState = determineGameState(board, currentTurn);

        if (currentGameState != GameState.ONGOING) {
            System.out.println("Game Ended: " + currentGameState);
        }
    }

    private boolean validateIfKingInCheckAfterMove(Board board, Color color, Move move) {
        Board copy = (new BoardFEN()).copy(board);
        copy.makeMove(move);

        Piece king = copy.getPiecesByColor(color).stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .orElse(null);
                
        if (king == null) return false;
        return copy.isSquareAttackedByColor(king.getCoordinates(), color.opposite());
    }

    private void cleanup() {
        assets.dispose();
        Display.destroy();
    }
}