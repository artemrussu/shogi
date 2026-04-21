package model.piece.impl;

import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import model.piece.LongRangePiece;
import model.piece.traits.IRook;

/**
 * Represents a Rook piece.
 * Moves any distance horizontally or vertically but cannot jump over other pieces.
 */
public class Rook extends LongRangePiece implements IRook {
    public Rook(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Returns the set of orthogonal movement vectors defined in IRook.
     */
    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        return getRookMoves();
    }
}