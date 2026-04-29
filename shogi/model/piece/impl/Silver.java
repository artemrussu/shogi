package model.piece.impl;

import java.util.HashSet;
import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import core.PieceType;
import model.piece.Piece;

/**
 * Moves one square in any direction except sideways (left/right) and directly backward.
 * Can be promoted to a promoted silver.
 */
public class Silver extends Piece {

	public Silver(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}
	
    @Override
	public PieceType getPieceType() {
		return PieceType.SILVER;
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
