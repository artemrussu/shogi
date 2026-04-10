package pack;

import java.util.Scanner;
import java.util.Set;
import board.Board;
import piece.Piece;

/**
 * Utility class for handling user input via the console.
 * Validates coordinate strings and move availability.
 */
public class InputCoordinates {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads and validates a basic coordinate pair (e.g., "A1") from the console.
     * Continues prompting the user until a valid format is provided.
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
     * Prompts for a coordinate and ensures it contains a piece of the specified color.
     * Also checks if the selected piece has at least one legal move.
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
     * Prompts for a destination square and validates it against a set of allowed moves.
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
}