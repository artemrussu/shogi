package view.gui;

import core.Color;
import core.Coordinates;
import core.File;

public class BoardPerspective {

    private final Color bottomPlayer;

    public BoardPerspective(Color bottomPlayer) {
        this.bottomPlayer = bottomPlayer;
    }

    public int toCol(Coordinates c) {
        if (bottomPlayer == Color.GOTE) return c.file.ordinal();
        return 8 - c.file.ordinal();
    }

    public int toRow(Coordinates c) {
        if (bottomPlayer == Color.GOTE) return 9 - c.rank;
        return c.rank - 1;
    }

    public Coordinates toCoords(int col, int row) {
        if (bottomPlayer == Color.GOTE) {
            return new Coordinates(File.values()[col], 9 - row);
        }
        return new Coordinates(File.values()[8 - col], row + 1);
    }

    public BoardPerspective flip() {
        return new BoardPerspective(
            bottomPlayer == Color.SENTE ? Color.GOTE : Color.SENTE
        );
    }

    public Color getBottomPlayer() {
        return bottomPlayer;
    }
}