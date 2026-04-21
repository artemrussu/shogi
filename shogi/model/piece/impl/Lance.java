package model.piece.impl;

import java.util.Set;

import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import model.piece.LongRangePiece;
import model.piece.traits.ILance;

/**
 * Represents the Lance piece.
 * Moves any distance forward in a straight line, but cannot jump over pieces.
 */
public class Lance extends LongRangePiece implements ILance {
    
    public Lance(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        return getLanceMoves(getColor());
    }
}