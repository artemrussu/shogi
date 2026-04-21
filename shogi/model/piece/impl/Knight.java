package model.piece.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import model.piece.Piece;

/**
 * Represents the Knight piece.
 */
public class Knight extends Piece {

	public Knight(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

	/**
	 * Returns the set of relative shifts for the Knight.
	 */
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