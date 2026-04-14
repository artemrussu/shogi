package piece;

/**
 * Represents a relative movement vector on the board.
 * Defines how many files and ranks a piece shifts from its current position.
 */
public class CoordinatesShift {
    /** The relative change in the horizontal (file) position. */
    public final int fileShift;
    /** The relative change in the vertical (rank) position. */
    public final int rankShift;

    public CoordinatesShift(int fileShift, int rankShift) {
        super();
        this.fileShift = fileShift;
        this.rankShift = rankShift;
    }
}