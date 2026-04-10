package piece;

import java.util.HashSet;
import java.util.Set;

/**
 * Interface defining orthogonal (horizontal and vertical) movement patterns.
 * Provides reusable logic for rook-like movements across the board.
 */
public interface IRook {
    
    /**
     * Generates a set of relative shifts for horizontal and vertical directions.
     * Covers distances from 1 to 8 squares in all four cardinal directions.
     */
    default Set<CoordinatesShift> getRookMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        // left to right (horizontal)
        for (int i = -8; i <= 8; i++) {
            if (i == 0)
                continue;

            result.add(new CoordinatesShift(i, 0));
        }

        // bottom to top (vertical)
        for (int i = -8; i <= 8; i++) {
            if (i == 0)
                continue;

            result.add(new CoordinatesShift(0, i));
        }

        return result;
    }
}