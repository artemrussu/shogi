package piece;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import pack.Color;
import pack.Coordinates;

/**
 * Represents the Silver General piece. Traditionally moves one square
 * diagonally or one square directly forward.
 */
public class Silver extends Piece {

	public Silver(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

	/**
	 * Returns the set of relative shifts for the Silver General.
	 */
	@Override
	protected Set<CoordinatesShift> getPieceMoves() {
		Set<CoordinatesShift> moves = new HashSet<>();

	    moves.add(new CoordinatesShift(1, 1));
	    moves.add(new CoordinatesShift(1, -1));
	    moves.add(new CoordinatesShift(-1, -1));
	    moves.add(new CoordinatesShift(-1, 1));

	    if (getColor() == Color.SENTE) {
	        moves.add(new CoordinatesShift(0, -1));
	    } else {
	        moves.add(new CoordinatesShift(0, 1));
	    }

	    return moves;
	}
}