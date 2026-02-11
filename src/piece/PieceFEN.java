package piece;

import pack.Color;
import pack.Coordinates;

public class PieceFEN {

	public Piece fromFENChar(char fenChar, Coordinates coordinates) {
        switch (fenChar) {
            case 'p':
                return new Pawn(Color.BLACK, coordinates);
                
            case 'P':
                return new Pawn(Color.WHITE, coordinates);

            case 'K':
                return new King(Color.WHITE, coordinates);

            default:
                throw new RuntimeException("Unknown FEN char!");
        }
    }
}
