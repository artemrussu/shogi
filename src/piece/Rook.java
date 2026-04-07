package piece;

import java.util.Set;
import pack.Color;
import pack.Coordinates;

public class Rook extends LongRangePiece implements IRook {
	public Rook(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

	@Override
	protected Set<CoordinatesShift> getPieceMoves() {
		return getRookMoves();
	}
}