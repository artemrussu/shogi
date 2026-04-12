package piece;

import java.util.HashSet;
import java.util.Set;
import pack.Color;
import pack.Coordinates;

/**
 * Represents the Pawn piece.
 * Typically the most basic unit, moving one square forward.
 */
public class Pawn extends Piece {

    public Pawn(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Returns the set of relative shifts for the Pawn.
     */
    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
            
        		Set<CoordinatesShift> result = new HashSet<>();
        		
        		if (getColor() == Color.SENTE) {
        			result.add(new CoordinatesShift(0, -1));
        		} else {
        			result.add(new CoordinatesShift(0, 1));
        		}
        		
        		return result;
        }

}