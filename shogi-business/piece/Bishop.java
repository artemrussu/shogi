package piece;

import java.util.Set;
import pack.Color;
import pack.Coordinates;

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