package model.board;

import java.util.ArrayList;
import java.util.List;

import core.Coordinates;
import core.File;

/**
 * Collection of static utility methods for board coordinate geometry.
 * Provides helper functions to find paths between squares.
 */
public class BoardUtils {
    
    /**
     * Returns a list of coordinates located diagonally between source and target.
     * The result does not include the source or target coordinates themselves.
     */
    public static List<Coordinates> getDiagonalCoordinatesBetween(Coordinates source, Coordinates target) {

        List<Coordinates> result = new ArrayList<>();

        int fileShift = source.file.ordinal() < target.file.ordinal() ? 1 : -1;
        int rankShift = source.rank < target.rank ? 1 : -1;

        for (int fileIndex = source.file.ordinal() + fileShift, rank = source.rank + rankShift;
                fileIndex != target.file.ordinal() && rank != target.rank;
                fileIndex += fileShift, rank += rankShift) {
            result.add(new Coordinates(File.values()[fileIndex], rank));
        }

        return result;
    }

    /**
     * Returns a list of coordinates located vertically between source and target.
     * The result does not include the source or target coordinates themselves.
     */
    public static List<Coordinates> getVerticalCoordinatesBetween(Coordinates source, Coordinates target) {

        List<Coordinates> result = new ArrayList<>();

        int rankShift = source.rank < target.rank ? 1 : -1;

        for (int rank = source.rank + rankShift; rank != target.rank; rank += rankShift) {
            result.add(new Coordinates(source.file, rank));
        }

        return result;
    }

    /**
     * Returns a list of coordinates located horizontally between source and target.
     * The result does not include the source or target coordinates themselves.
     */
    public static List<Coordinates> getHorizontalCoordinatesBetween(Coordinates source, Coordinates target) {

        List<Coordinates> result = new ArrayList<>();

        int fileShift = source.file.ordinal() < target.file.ordinal() ? 1 : -1;

        for (int fileIndex = source.file.ordinal() + fileShift; fileIndex != target.file
                .ordinal(); fileIndex += fileShift) {
            result.add(new Coordinates(File.values()[fileIndex], source.rank));
        }

        return result;
    }

    /**
     * Internal test method to verify coordinate range logic.
     */
    public static void main(String[] args) {
        List<Coordinates> list = getHorizontalCoordinatesBetween(new Coordinates(File.D, 4),
                new Coordinates(File.H, 4));
        System.out.println("list = " + list);
    }
}