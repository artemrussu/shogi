package piece;

import java.util.HashSet;
import java.util.Set;

import pack.Color;
import pack.Coordinates;

/**
 * Represents a Gold General piece.
 * Traditionally moves one square in any direction except diagonally backwards.
 */
public class Gold extends Piece {

    public Gold(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Returns the set of relative shifts for this piece.
     * Current implementation allows movement to all 8 adjacent squares.
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