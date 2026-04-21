package core;

import core.Coordinates;
import core.File;

/**
 * Represents a specific square on the board.
 * Immutable object defined by a File (column) and a Rank (row).
 */
public class Coordinates {
    /** The vertical column (File) of the square. */
    public final File file;
    /** The horizontal row (Rank) of the square, typically 1 to 9. */
    public final Integer rank;

    public Coordinates(File file, Integer rank) {
        this.file = file;
        this.rank = rank;
    }

    /**
     * Creates new coordinates by applying a relative shift.
     * TODO: Optional<Coordinates>
     */
    public Coordinates shift(CoordinatesShift shift) {
        return new Coordinates(File.values()[this.file.ordinal() + shift.fileShift], this.rank + shift.rankShift);
    }

    /**
     * Validates if the given shift stays within the legal 9x9 board boundaries.
     */
    public boolean canShift(CoordinatesShift shift) {
        int f = file.ordinal() + shift.fileShift;
        int r = rank + shift.rankShift;

        if ((f < 0) || (f > 8))
            return false;
        if ((r < 1) || (r > 9))
            return false;

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Coordinates that = (Coordinates) obj;

        if (file != that.file)
            return false;
        return rank.equals(that.rank);
    }

    @Override
    public int hashCode() {
        int result = file.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }
}