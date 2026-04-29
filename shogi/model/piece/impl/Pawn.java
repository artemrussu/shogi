package model.piece.impl;

import java.util.HashSet;
import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import core.PieceType;
import model.piece.Piece;
import model.piece.impl.prom.Tokin;

/**
 * Moving one square forward;
 * Can be promoted to a token (golden like);
 * Must be forcibly promoted when no more moves are available on the board.
 */
public class Pawn extends Piece {

    public Pawn(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }
    
	@Override
	public PieceType getPieceType() {
		return PieceType.PAWN;
	}
	
	@Override
    public boolean mustPromote(Coordinates target) {
        if (getColor() == Color.SENTE) return target.rank == 1;
        return target.rank == 9;
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
            
        		Set<CoordinatesShift> result = new HashSet<>();
        		
        		if (getColor() == Color.SENTE) {
        			result.add(new CoordinatesShift(0, -1));
        		} else {
        			result.add(new CoordinatesShift(0, 1));
        		}
        		
        		return result;
        }
}