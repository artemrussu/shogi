package piece;

import pack.Color;
import pack.Coordinates;

public class PieceFEN {

	public Piece fromFENChar(char fenChar, Coordinates coordinates) {
		switch (fenChar) {
		case 'p':
			return new Pawn(Color.GOTE, coordinates);

		case 'P':
			return new Pawn(Color.SENTE, coordinates);

		case 'l':
			return new Lance(Color.GOTE, coordinates);

		case 'L':
			return new Lance(Color.SENTE, coordinates);

		case 'n':
			return new Knight(Color.GOTE, coordinates);

		case 'N':
			return new Knight(Color.SENTE, coordinates);

		case 's':
			return new Silver(Color.GOTE, coordinates);

		case 'S':
			return new Silver(Color.SENTE, coordinates);

		case 'g':
			return new Gold(Color.GOTE, coordinates);

		case 'G':
			return new Gold(Color.SENTE, coordinates);

		case 'b':
			return new Bishop(Color.GOTE, coordinates);

		case 'B':
			return new Bishop(Color.SENTE, coordinates);

		case 'r':
			return new Rook(Color.GOTE, coordinates);

		case 'R':
			return new Rook(Color.SENTE, coordinates);

		case 'k':
			return new King(Color.GOTE, coordinates);

		case 'K':
			return new King(Color.SENTE, coordinates);

		default:
			throw new RuntimeException("Unknown FEN char!");
		}
	}
}
