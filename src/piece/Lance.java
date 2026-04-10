package piece;

import java.util.HashSet;
import java.util.Set;

import pack.Color;
import pack.Coordinates;

/**
 * Represents the Lance piece.
 * Typically characterized by long-range forward movement.
 */
public class Lance extends Piece {

    public Lance(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Returns the set of relative shifts for the Lance.
     * Note: Current implementation uses a 3x3 grid placeholder.
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

}