package piece;

import java.util.Set;

import pack.Color;
import pack.Coordinates;

public class Horse extends LongRangePiece implements IBishop {

	public Horse(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

	@Override
	protected Set<CoordinatesShift> getPieceMoves() {
		Set<CoordinatesShift> moves = getBishopMoves();
		moves.addAll(Set.of(
		        new CoordinatesShift(0, 1),
		        new CoordinatesShift(0, -1),
		        new CoordinatesShift(1, 0),
		        new CoordinatesShift(-1, 0)
		        ));
		
		return moves;
	}

}
