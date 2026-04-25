package model.piece.impl;

import java.util.HashSet;
import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import model.board.Board;
import model.piece.Piece;

/**
 * Represents the King piece. Moves exactly one square in any direction
 * (horizontal, vertical, or diagonal).
 */
public class King extends Piece {

	public King(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

	/**
	 * Generates all 8 possible adjacent movement vectors for the King.
	 */
	@Override
	protected Set<CoordinatesShift> getPieceMoves() {
		Set<CoordinatesShift> result = new HashSet<>();

		for (int fileShift = -1; fileShift <= 1; fileShift++) {
			for (int rankShift = -1; rankShift <= 1; rankShift++) {
				if ((fileShift == 0) && (rankShift == 0)) {
					continue;
				}

				result.add(new CoordinatesShift(fileShift, rankShift));
			}
		}

		return result;
	}

	@Override
	protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
		boolean result = super.isSquareAvailableForMove(coordinates, board);

		if (result) {
			return !board.isSquareAttackedByColor(coordinates, getColor().opposite());
		}

		return false;
	}
}