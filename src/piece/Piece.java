package piece;

import java.util.HashSet;
import java.util.Set;

import pack.Color;
import pack.Coordinates;
import board.Board;

abstract public class Piece {
	public final Color color;
	public Coordinates coordinates;
	
	public Piece(Color color, Coordinates coordinates) {
		super();
		this.color = color;
		this.coordinates = coordinates;
	}
	
	public Set<Coordinates> getAvailableMoveSquares(Board board) {
		Set<Coordinates> result = new HashSet<>();
		
		for(CoordinatesShift shift : getPieceMoves()) {
			if (coordinates.canShift(shift)) {
				Coordinates newCoordinates = coordinates.shift(shift);
				
				if (isSquareAvailableForMove(newCoordinates, board)) {
					result.add(newCoordinates);
				}
			}
		}
		
		return result;
	}
	
	private boolean isSquareAvailableForMove(Coordinates сoordinates, Board board) {
		return board.isSquareEmpty(сoordinates) || board.getPiece(coordinates).color != color;
	}

	protected abstract Set<CoordinatesShift> getPieceMoves();
	// 
}