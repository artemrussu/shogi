package piece;

import pack.Color;
import pack.Coordinates;

public class PieceFEN {

	public Piece fromFenChar(char fenChar, Coordinates coordinates) {
        switch (fenChar) {
            case 'p':
                return new Pawn(Color.BLACK, coordinates);
                
            case 'P':
                return new Pawn(Color.WHITE, coordinates);

            case 'k':
                return new King(Color.BLACK, coordinates);

            default:
                throw new RuntimeException("Unknown FEN char!");
        }
    }
}
