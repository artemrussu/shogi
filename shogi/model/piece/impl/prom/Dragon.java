package model.piece.impl.prom;

import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import model.piece.LongRangePiece;
import model.piece.traits.IRook;

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
