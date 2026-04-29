package model.piece;

import java.util.HashSet;
import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import core.PieceType;

/**
 * Represents all pieces that move like a gold general.
 * Moves one square forward, backward, left, right, or diagonally forward;
 * cannot move diagonally backward.
 */
public abstract  class GoldLikePiece extends Piece {

	public GoldLikePiece(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}
	
	@Override
	public abstract PieceType getPieceType();

	@Override
	protected Set<CoordinatesShift> getPieceMoves() {
	    Set<CoordinatesShift> moves = new HashSet<>();

	    moves.add(new CoordinatesShift(0, 1));
	    moves.add(new CoordinatesShift(0, -1));
	    moves.add(new CoordinatesShift(1, 0));
	    moves.add(new CoordinatesShift(-1, 0));

	    if (getColor() == Color.SENTE) {
	        moves.add(new CoordinatesShift(-1, -1));
	        moves.add(new CoordinatesShift(1, -1));
	    } else {
	        moves.add(new CoordinatesShift(1, 1));
	        moves.add(new CoordinatesShift(-1, 1));
	    }

	    return moves;
	}
}
