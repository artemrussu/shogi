package model.piece.traits;

import java.util.HashSet;
import java.util.Set;

import core.Color;
import core.CoordinatesShift;

/**
 * Interface for vertical forward long-range movement.
 */
public interface ILance {
    
    default Set<CoordinatesShift> getLanceMoves(Color color) {
        Set<CoordinatesShift> result = new HashSet<>();

        for (int i = 1; i <= 8; i++) {
            if (color == Color.SENTE) {
                result.add(new CoordinatesShift(0, -i));
             
            } else {
                result.add(new CoordinatesShift(0, i));
                
            }
        }

        return result;
    }
}