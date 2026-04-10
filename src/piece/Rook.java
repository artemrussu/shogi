package piece;

import java.util.Set;
import pack.Color;
import pack.Coordinates;

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