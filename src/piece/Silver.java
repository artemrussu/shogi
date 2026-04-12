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
		return new HashSet<>(Arrays.asList(
				new CoordinatesShift(0, 1),
				new CoordinatesShift(1, 1),
				new CoordinatesShift(1, -1),
				new CoordinatesShift(-1, -1),
				new CoordinatesShift(-1, 1)));
	}

}