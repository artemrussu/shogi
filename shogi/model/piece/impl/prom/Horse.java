package model.piece.impl.prom;

import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import model.piece.LongRangePiece;
import model.piece.traits.IBishop;

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
