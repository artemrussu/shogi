package model.piece.traits;

import java.util.HashSet;
import java.util.Set;

import core.CoordinatesShift;

/**
 * Interface defining diagonal movement patterns.
 * Can be reused by any piece that has bishop-like movement capabilities.
 */
public interface IBishop {
    
    /**
     * Generates a set of relative shifts for all four diagonal directions.
     * Covers distances from 1 to 8 squares.
     */
    default Set<CoordinatesShift> getBishopMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        // bottom-left to top-right (diagonal 1)
        for (int i = -8; i <= 8; i++) {
            if (i == 0)
                continue;

            result.add(new CoordinatesShift(i, i));
        }

        // top-left to bottom-right (diagonal 2)
        for (int i = -8; i <= 8; i++) {
            if (i == 0)
                continue;

            result.add(new CoordinatesShift(i, -i));
        }

        return result;
    }
}