package piece;

import java.util.Set;
import pack.Color;
import pack.Coordinates;

public class Bishop extends LongRangePiece implements IBishop {
	public Bishop(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

	@Override
	protected Set<CoordinatesShift> getPieceMoves() {
		return getBishopMoves();
	}
}
