package piece;

import java.util.Set;
import pack.Color;
import pack.Coordinates;

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