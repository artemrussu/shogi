package core;

import java.util.Scanner;
import java.util.Set;

import core.factory.BoardFEN;
import model.Board;
import model.Hand;
import model.piece.Piece;
import model.piece.impl.King;
import view.console.ConsoleRenderer;

/**
 * Utility class for handling user input. Validates coordinate
 * strings and move availability.
 */
public class InputCoordinates {

	private static final Scanner scanner = new Scanner(System.in);

	/**
	 * Reads and validates a basic coordinate pair.
	 */
	public static Coordinates input() {
		while (true) {
			System.out.println("Enter coordinates:");
			String line = scanner.nextLine();

			if (line.length() != 2) {
				System.out.println("Invalid (length)");
				continue;
			}

			char fileChar = line.charAt(0);
			char rankChar = line.charAt(1);

			if (!Character.isLetter(fileChar)) {
				System.out.println("Invalid (file)");
				continue;
			}

			if (!Character.isDigit(rankChar)) {
				System.out.println("Invalid (rank)");
				continue;
			}

			int rank = Character.getNumericValue(rankChar);
			if (rank < 1 || rank > 9) {
				System.out.println("Invalid (rank)");
				continue;
			}

			File file = File.fromChar(fileChar);
			if (file == null) {
				System.out.println("Invalid (file)");
				continue;
			}

			return new Coordinates(file, rank);
		}
	}

	/**
	 * Prompts for a coordinate and ensures it contains a piece of the specified
	 * color. Also checks if the selected piece has at least one legal move.
	 */
	public static Coordinates inputPieceCoordinatesForColor(Color color, Board board) {
		while (true) {
			Coordinates coordinates = input();

			if (board.isSquareEmpty(coordinates)) {
				System.out.println("Square is empty");
				continue;
			}

			Piece piece = board.getPiece(coordinates);

			if (piece.getColor() != color) {
				System.out.println("Wrong color");
				continue;
			}

			Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);
			if (availableMoveSquares.size() == 0) {
				System.out.println("Blocked piece");
				continue;
			}

			return coordinates;
		}
	}

	/**
	 * Prompts for a destination square and validates it against a set of allowed
	 * moves.
	 */
	public static Coordinates inputAvailableSquare(Set<Coordinates> coordinates) {
		while (true) {
			System.out.println("Enter your move for selected piece");
			Coordinates input = input();

			if (!coordinates.contains(input)) {
				System.out.println("Non-available square");
				continue;
			}

			return input;
		}
	}

	public static Move inputMove(Board board, Color color, ConsoleRenderer renderer) {
		while (true) {
			// input
			Coordinates sourceCoordinates = InputCoordinates.
					inputPieceCoordinatesForColor(color, board);

			Piece piece = board.getPiece(sourceCoordinates);
			Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

			renderer.render(board, piece);
			Coordinates targetCoordinates = InputCoordinates.
					inputAvailableSquare(availableMoveSquares);

			Move move = new Move(sourceCoordinates, targetCoordinates);

			if (validateIfKingInCheckAfterMove(board, color, move)) {
				System.out.println("Your king is under attack!");
				continue;
			}

			return move;
		}
	}
	
	/**
	 * PROMOTION
	 */
	public static boolean inputPromotionChoice() {
	    while (true) {
	        System.out.println("Promote piece? (y/n):");
	        String answer = scanner.nextLine().trim().toLowerCase();

	        if (answer.equals("y")) return true;
	        if (answer.equals("n")) return false;

	        System.out.println("Invalid input, enter y or n");
	    }
	}
	
	/********************************************
	 * DROP
	 */
	// asks which piece to drop from hand
	public static DropMove inputDropMove(Board board, Color color) {
	    Hand hand = board.hand;

	    // show what is in hand
	    System.out.println("Your hand:");
	    for (PieceType type : hand.getHand(color).keySet()) {
	        int count = hand.getHand(color).get(type);
	        if (count > 0) {
	            System.out.println("  " + type + " x" + count);
	        }
	    }

	    while (true) {

	        // choose piece
	        System.out.println("Enter piece type to drop (e.g. PAWN):");
	        String line = scanner.nextLine().trim().toUpperCase();

	        PieceType type;
	        try {
	            type = PieceType.valueOf(line);
	        } catch (IllegalArgumentException e) {
	            System.out.println("Unknown piece type");
	            continue;
	        }

	        if (!hand.has(color, type)) {
	            System.out.println("You don't have this piece in hand");
	            continue;
	        }

	        // choose square
	        System.out.println("Enter square to drop on:");
	        Coordinates target = input();

	        DropMove drop = new DropMove(type, color, target);

	        if (!DropValidator.isValidDrop(drop, board)) {
	            System.out.println("Invalid drop");
	            continue;
	        }

	        return drop;
	    }
	}

	// asks: move a piece or drop?
	public static boolean inputWantsDrop(Color color, Board board) {
	    // if hand is empty, immediately return false
	    if (board.hand.getHand(color).values().stream().allMatch(count -> count == 0)) {
	        return false;
	    }

	    while (true) {
	        System.out.println("Move piece (m) or drop from hand (d)?");
	        String answer = scanner.nextLine().trim().toLowerCase();

	        if (answer.equals("m")) return false;
	        if (answer.equals("d")) return true;

	        System.out.println("Enter m or d");
	    }
	}
	/********************************************/

	public static boolean validateIfKingInCheckAfterMove(Board board, Color color, Move move) {
		Board copy = (new BoardFEN()).copy(board);
		copy.makeMove(move);

		// we trust that there is king on the board
		Piece king = copy.getPiecesByColor(color).stream().filter(piece -> piece instanceof King).findFirst().get();
		return copy.isSquareAttackedByColor(king.getCoordinates(), color.opposite());
	}
}