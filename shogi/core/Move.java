package core;

public class Move {
    public final Coordinates from, to;
    public final boolean promote;

    public Move(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
        this.promote = false;
    }

    public Move(Coordinates from, Coordinates to, boolean promote) {
        this.from = from;
        this.to = to;
        this.promote = promote;
    }
}