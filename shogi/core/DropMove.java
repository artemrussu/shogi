package core;

public class DropMove extends Move {
    public final PieceType pieceType;
    public final Color color;

    public DropMove(PieceType pieceType, Color color, Coordinates to) {
        super(null, to, false);
        this.pieceType = pieceType;
        this.color = color;
    }
}