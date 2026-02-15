package pack;

import java.util.Set;

import board.Board;
import board.BoardConsoleRenderer;
import board.BoardConsoleRenderer;
import piece.Piece;

public class Game {

	private final Board board;
	
	private BoardConsoleRenderer renderer = new BoardConsoleRenderer();
	
	public Game(Board board) {
		this.board = board;
	}
	
	public void gameLoop() {
		boolean isWhiteToMove = true; // in tafl games black's turn first (now its white)
		
		while (true) {
			/* render
			 * input
			 * make move
			 * pass move*/
			
			// render
			renderer.render(board);
			
			if (isWhiteToMove) {
				System.out.println("White to move");
			} else {
				System.out.println("Black to move");
			}
			
			// input
			Coordinates fromCoordinates = InputCoordinates.inputPieceCoordinatesForColor(
					isWhiteToMove ? Color.WHITE : Color.BLACK, board);
			
			Piece piece = board.getPiece(fromCoordinates);
			Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);
			
			renderer.render(board, piece);
			Coordinates toCoordinate = InputCoordinates.inputAvailableSquare(availableMoveSquares);
			
			// make move
			board.movePiece(fromCoordinates, toCoordinate);
			
			// pass move (color change)
			isWhiteToMove =! isWhiteToMove;
		}
	}
}
