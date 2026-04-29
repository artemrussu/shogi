package core.factory;

import core.Color;
import core.Coordinates;
import core.PieceType;
import model.piece.Piece;
import model.piece.impl.*;
import model.piece.impl.prom.*;

public class PieceFactory {

    public static Piece create(PieceType type, Color color, Coordinates coordinates) {
        if (type == PieceType.PAWN)             return new Pawn(color, coordinates);
        if (type == PieceType.LANCE)            return new Lance(color, coordinates);
        if (type == PieceType.KNIGHT)           return new Knight(color, coordinates);
        if (type == PieceType.SILVER)           return new Silver(color, coordinates);
        if (type == PieceType.GOLD)             return new Gold(color, coordinates);
        if (type == PieceType.BISHOP)           return new Bishop(color, coordinates);
        if (type == PieceType.ROOK)             return new Rook(color, coordinates);
        if (type == PieceType.KING)             return new King(color, coordinates);
        if (type == PieceType.TOKIN)            return new Tokin(color, coordinates);
        if (type == PieceType.HORSE)            return new Horse(color, coordinates);
        if (type == PieceType.DRAGON)           return new Dragon(color, coordinates);
        if (type == PieceType.PROMOTED_LANCE)   return new PromotedLance(color, coordinates);
        if (type == PieceType.PROMOTED_KNIGHT)  return new PromotedKnight(color, coordinates);
        if (type == PieceType.PROMOTED_SILVER)  return new PromotedSilver(color, coordinates);

        throw new RuntimeException("Unknown piece type: " + type);
    }
}