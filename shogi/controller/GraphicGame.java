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
import model.board.Board;
import model.piece.Piece;
import model.piece.impl.King; 
import view.gui.GraphicRender;

public class GraphicGame {
    private final Board board;
    private GraphicRender renderer = new GraphicRender();

    private Coordinates selectedSquare = null;
    private Color currentTurn = Color.SENTE;
    
    private GameState currentGameState = GameState.ONGOING;
    private final List<GameStateChecker> checkers = List.of(
            new StalemateGameStateChecker(),
            new CheckmateGameStateChecker()
    );

    public GraphicGame(Board board) {
        this.board = board;
    }

    public void gameLoop() {
        renderer.init(board);
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

        Display.destroy();
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
                Coordinates clickedCoords = renderer.getCoordinatesFromMouse();

                if (clickedCoords == null) {
                    selectedSquare = null;
                    continue;
                }

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

        if (availableMoves.contains(clickedCoords)) {
            Move move = new Move(selectedSquare, clickedCoords);
            
            // NEW CHECK: Does this move put our own king in check?
            if (validateIfKingInCheckAfterMove(board, currentTurn, move)) {
                System.out.println("Invalid move: Your king is under attack!");
                selectedSquare = null; // Reset selection
                return; // Abort, the move is not made
            }
            
            board.makeMove(move);

            currentTurn = currentTurn.opposite();
            selectedSquare = null;

            currentGameState = determineGameState(board, currentTurn);
            
            if (currentGameState != GameState.ONGOING) {
                System.out.println("Game Ended: " + currentGameState);
            }
        } else {
            // Re-select if clicking another friendly piece
            if (!board.isSquareEmpty(clickedCoords) && board.getPiece(clickedCoords).getColor() == currentTurn) {
                selectedSquare = clickedCoords;
            } else {
                selectedSquare = null;
            }
        }
    }

    // Copied from InputCoordinates for use within the graphical environment
    private boolean validateIfKingInCheckAfterMove(Board board, Color color, Move move) {
        Board copy = (new BoardFEN()).copy(board);
        copy.makeMove(move);

        // Safely retrieve the king (in case you are testing without one)
        Piece king = copy.getPiecesByColor(color).stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .orElse(null);
                
        if (king == null) return false;

        return copy.isSquareAttackedByColor(king.getCoordinates(), color.opposite());
    }
}