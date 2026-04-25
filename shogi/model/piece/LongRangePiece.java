package model.piece;

import java.util.List;

import core.BoardUtils;
import core.Color;
import core.Coordinates;
import model.board.Board;

/**
 * Base class for pieces that can move across multiple empty squares in a specific direction.
 * Implements pathfinding logic to ensure the path to a target square is not blocked.
 */
public abstract class LongRangePiece extends Piece {
    public LongRangePiece(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    /**
     * Validates if a square is reachable by checking both standard rules and path availability.
     */
    @Override
    protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
        boolean result = super.isSquareAvailableForMove(coordinates, board);

        if (result) {
            return isSquareAvailableForAttack(coordinates, board);
        } else {
            return false;
        }
    }

    /**
     * Checks if the path between the piece and the target coordinates is clear of any other pieces.
     */
    @Override
    protected boolean isSquareAvailableForAttack(Coordinates coordinates, Board board) {
        List<Coordinates> coordinatesBetween;
        
        if (this.getCoordinates().file == coordinates.file) {
            coordinatesBetween = BoardUtils.getVerticalCoordinatesBetween(this.getCoordinates(), coordinates);
        } else if (this.getCoordinates().rank.equals(coordinates.rank)) {
            coordinatesBetween = BoardUtils.getHorizontalCoordinatesBetween(this.getCoordinates(), coordinates);
        } else {
            coordinatesBetween = BoardUtils.getDiagonalCoordinatesBetween(this.getCoordinates(), coordinates);
        }

        for (Coordinates c : coordinatesBetween) {
            if (!board.isSquareEmpty(c)) {
                return false;
            }
        }

        return true;
    }
}