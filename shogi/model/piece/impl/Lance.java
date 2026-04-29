package model.piece.impl;

import java.util.HashSet;
import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import core.PieceType;
import model.piece.Piece;

/**
 * Moves any distance forward in a straight line, but cannot jump over pieces;
 * Can be promoted to a promoted lance (golden like);
 * Must be forcibly promoted when no more moves are available on the board.
 */
public class Lance extends Piece {
    
    public Lance(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }
    
    @Override
	public PieceType getPieceType() {
		return PieceType.LANCE;
	}
	
	@Override
    public boolean mustPromote(Coordinates target) {
        if (getColor() == Color.SENTE) return target.rank == 1;
        return target.rank == 9;
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        for (int i = 1; i <= 8; i++) {
            if (getColor() == Color.SENTE) {
                result.add(new CoordinatesShift(0, -i));
             
            } else {
                result.add(new CoordinatesShift(0, i));
                
            }
        }

        return result;
    }
}