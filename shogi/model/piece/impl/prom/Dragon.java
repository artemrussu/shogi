package model.piece.impl.prom;

import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import core.PieceType;
import model.piece.LongRangePiece;
import model.piece.traits.IRook;

/**
 * Moves like a rook (any number of squares vertically or horizontally,
 * cannot jump), plus one square in any diagonal direction (like a king's diagonal moves).
 */
public class Dragon extends LongRangePiece implements IRook {

	public Dragon(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

    @Override
	public PieceType getPieceType() {
		return PieceType.DRAGON;
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
