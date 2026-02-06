package piece;

import java.util.Set;

import pack.Color;
import pack.Coordinates;

public class Pawn extends Piece{

	public Pawn(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

	@Override
	protected Set<CoordinatesShift> getPieceMoves() {
		// TODO Auto-generated method stub
		return null; // not null
	}
	
}