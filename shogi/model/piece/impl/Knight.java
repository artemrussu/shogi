package model.piece.impl;

import java.util.HashSet;
import java.util.Set;
import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import core.PieceType;
import model.piece.Piece;

/**
 * Moves two squares forward, then one square left or right; 
 * Jumps over pieces; 
 * Cannot move sideways or backward;
 * Can be promoted to a promoted knight (golden like);
 * Must be forcibly promoted when no more moves are available on the board.
 */
public class Knight extends Piece {

	public Knight(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}
	
	@Override
	public PieceType getPieceType() {
		return PieceType.KNIGHT;
	}
	
	@Override
	public boolean mustPromote(Coordinates target) {
	    if (getColor() == Color.SENTE) return target.rank <= 2;
	    return target.rank >= 8;
	}

	@Override
    protected Set<CoordinatesShift> getPieceMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        if (getColor() == Color.SENTE) {

            result.add(new CoordinatesShift(-1, -2));
            result.add(new CoordinatesShift(1, -2));
  
        } else {

            result.add(new CoordinatesShift(-1, 2));
            result.add(new CoordinatesShift(1, 2));
        }

        return result;
    }
}