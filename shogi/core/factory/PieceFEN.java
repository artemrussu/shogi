package core.factory;

import core.Color;
import core.Coordinates;
import model.piece.King;
import model.piece.Piece;
import model.piece.impl.Bishop;
import model.piece.impl.Gold;
import model.piece.impl.Knight;
import model.piece.impl.Lance;
import model.piece.impl.Pawn;
import model.piece.impl.Rook;
import model.piece.impl.Silver;
import model.piece.impl.Tokin;
import model.piece.impl.prom.Dragon;
import model.piece.impl.prom.Horse;

/**
 * Factory class that creates Piece objects from FEN characters.
 */
public class PieceFEN {

	/**
	 * Maps a FEN character to a specific Piece implementation. Uppercase characters
	 * represent SENTE, lowercase represent GOTE.
	 */
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

		case 'h':
			return new Horse(Color.GOTE, coordinates);

		case 'H':
			return new Horse(Color.SENTE, coordinates);

		case 'd':
			return new Dragon(Color.GOTE, coordinates);

		case 'D':
			return new Dragon(Color.SENTE, coordinates);

		case 't':
			return new Tokin(Color.GOTE, coordinates);

		case 'T':
			return new Tokin(Color.SENTE, coordinates);
			
			

		default:
			throw new RuntimeException("Unknown FEN char!");
		}
	}
}