package model.piece.impl;

import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import model.piece.LongRangePiece;
import model.piece.traits.IBishop;

/**
 * Represents a Bishop piece.
 * Moves any distance diagonally but cannot jump over other pieces.
 */
public class Bishop extends LongRangePiece implements IBishop {
    public Bishop(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Returns the set of diagonal movement vectors defined in IBishop.
     */
    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        return getBishopMoves();
    }
}