package board;

import pack.Coordinates;
import pack.File;
import piece.PieceFEN;

/**
 * Utility class for converting FEN (Forsyth–Edwards Notation) strings into Board objects.
 * Handles piece placement and row parsing logic.
 */
public class BoardFEN {
    /** Helper for converting individual characters into specific Piece objects. */
    private final PieceFEN pieceFEN = new PieceFEN();

    /**
     * Parses a FEN string and populates a new Board with the described position.
     * Expects 9 rows separated by '/' for a 9x9 board.
     */
    public Board fromFEN(String fen) {
        // 3ppp3/4p4/4P4/p3P3p/ppPPKPPpp/p3P3p/4P4/4p4/3ppp3 b (tafl)

        Board board = new Board();

        String[] parts = fen.split(" ");
        String piecePositions = parts[0];

        String[] fenRows = piecePositions.split("/");

        for (int i = 0; i < fenRows.length; i++) {
            String row = fenRows[i];
            int rank = 9 - i;

            int fileIndex = 0;
            for (int j = 0; j < row.length(); j++) {
                char fenChar = row.charAt(j);

                if (Character.isDigit(fenChar)) {
                    fileIndex += Character.getNumericValue(fenChar);
                } else {
                    File file = File.values()[fileIndex];
                    Coordinates coordinates = new Coordinates(file, rank);

                    board.setPiece(coordinates, pieceFEN.fromFENChar(fenChar, coordinates));
                    fileIndex++;
                }
            }
        }

        return board;
    }
}