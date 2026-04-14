package piece;

import java.util.Set;

import pack.Color;
import pack.Coordinates;

public class Dragon extends LongRangePiece implements IRook {

	public Dragon(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

	@Override
	protected Set<CoordinatesShift> getPieceMoves() {
		Set<CoordinatesShift> moves = getRookMoves();
		moves.addAll(Set.of(
		        new CoordinatesShift(1, 1),
		        new CoordinatesShift(1, -1),
		        new CoordinatesShift(-1, 1),
		        new CoordinatesShift(-1, 1)
		        ));
		
		return moves;
	}

}
