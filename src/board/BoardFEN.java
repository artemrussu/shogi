package board;

import java.io.File;

import pack.Coordinates;
import piece.PieceFEN;

public class BoardFEN {
	private final PieceFEN pieceFEN = new PieceFEN();

    public Board fromFEN(String fen) {
        // 3ppp3/4p4/4P4/p3P3p/ppPPKPPpp/p3P3p/4P4/4p4/3ppp3 b

        Board board = new Board();

        String[] parts = fen.split(" ");
        String piecePositions = parts[0];

        String[] fenRows = piecePositions.split("/");

        // ccls

        return board;
    }
}
