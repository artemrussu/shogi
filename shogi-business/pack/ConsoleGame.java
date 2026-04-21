package pack;

import java.util.Set;
import board.Board;
import board.BoardConsoleRenderer;
import piece.Piece;

/**
 * Controls the game logic and flow. 
 * Manages the game loop, turn transitions, and user interaction.
 */
public class ConsoleGame {

    private final Board board;
    private BoardConsoleRenderer renderer = new BoardConsoleRenderer();

    public ConsoleGame(Board board) {
        this.board = board;
    }

    /**
     * Starts and maintains the main game loop.
     * Handles rendering, input validation, and piece movement until the game ends.
     */
    public void gameLoop() {
        boolean isWhiteToMove = true; 

        while (true) {
            // render
            renderer.render(board);

            if (isWhiteToMove) {
                System.out.println("White to move");
            } else {
                System.out.println("Black to move");
            }

            // input
            Coordinates fromCoordinates = InputCoordinates
                    .inputPieceCoordinatesForColor(isWhiteToMove ? Color.SENTE : Color.GOTE, board);

            Piece piece = board.getPiece(fromCoordinates);
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

            renderer.render(board, piece);
            Coordinates toCoordinate = InputCoordinates.inputAvailableSquare(availableMoveSquares);

            // make move
            board.movePiece(fromCoordinates, toCoordinate);

            // pass move
            isWhiteToMove = !isWhiteToMove;
        }
    }
}