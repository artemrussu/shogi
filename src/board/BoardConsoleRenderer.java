package board;

import java.util.Set;

import pack.Color;
import pack.Coordinates;
import pack.File;
import piece.Piece;

public class BoardConsoleRenderer {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_WHITE_PIECE_COLOR = "\u001B[97m";
	public static final String ANSI_BLACK_PIECE_COLOR = "\u001B[30m";
	public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001B[47m";
	public static final String ANSI_BLACK_SQUARE_BACKGROUND = "\u001B[0;100m";
	public static final String ANSI_HIGHLIGHTED_SQUARE_BACKGROUND = "\u001B[45m";

	public void render(Board board, Piece pieceToMove) {
		Set<Coordinates> availableMoveSquares = Set.of();
		if (pieceToMove != null) {
			availableMoveSquares = pieceToMove.getAvailableMoveSquares(board);
		}

		for (int rank = 9; rank >= 1; rank--) {
			String line = " ";
			for (File file : File.values()) {
				// sprite
				Coordinates coordinates = new Coordinates(file, rank);
				boolean isHighlight = availableMoveSquares.contains(coordinates);

				if (board.isSquareEmpty(coordinates)) {
					line += getSpriteForEmptySquare(coordinates, isHighlight);
				} else {
					line += getPieceSprite(board.getPiece(coordinates), isHighlight);
				}
			}

			line += ANSI_RESET;
			System.out.println(line);
		}
	}

	public void render(Board board) {
		render(board, null);
	}

	private String getPieceSprite(Piece piece, boolean isHighlight) {
		return colorizeSprite(" " + selectSpriteForPiece(piece) + " ", piece.color,
				Board.isSquareDark(piece.coordinates), isHighlight);
	}

	private String selectSpriteForPiece(Piece piece) {
		switch (piece.getClass().getSimpleName()) {

		case "King":
			return "王";

		case "Pawn":
			return "歩";

		case "Lance":
			return "香";

		case "Knight":
			return "桂";

		case "Silver":
			return "銀ﾞ";

		case "Gold":
			return "金";

		case "Bishop":
			return "角";

		case "Rook":
			return "飛";
		}

		return piece.getClass().getSimpleName();
	}

	private String colorizeSprite(String sprite, Color pieceColor, boolean isSquareDark, boolean isHighlight) {
		// format = background color + font color + text
		String result = sprite;

		if (pieceColor == Color.SENTE) {
			result = ANSI_WHITE_PIECE_COLOR + result;
		} else {
			result = ANSI_BLACK_PIECE_COLOR + result;
		}

		if (isHighlight == true) {
			result = ANSI_HIGHLIGHTED_SQUARE_BACKGROUND + result;

		} else if (isSquareDark) {
			result = ANSI_BLACK_SQUARE_BACKGROUND + result;
		} else {
			result = ANSI_WHITE_SQUARE_BACKGROUND + result;
		}

		return result;
	}

	private String getSpriteForEmptySquare(Coordinates coordinates, boolean isHighlight) {
		return colorizeSprite("   ", Color.SENTE, Board.isSquareDark(coordinates), isHighlight);
	}

}
