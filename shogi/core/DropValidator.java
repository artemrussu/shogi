package core;

import model.Board;
import model.piece.Piece;
import model.piece.impl.Pawn;

public class DropValidator {

    public static boolean isValidDrop(DropMove drop, Board board) {

        // only onto an empty square
        if (!board.isSquareEmpty(drop.to)) {
            return false;
        }

        // piece cannot be permanently frozen on this square
        if (drop.pieceType == PieceType.PAWN || drop.pieceType == PieceType.LANCE) {
            if (drop.color == Color.SENTE && drop.to.rank == 1) return false;
            if (drop.color == Color.GOTE  && drop.to.rank == 9) return false;
        }

        if (drop.pieceType == PieceType.KNIGHT) {
            if (drop.color == Color.SENTE && drop.to.rank <= 2) return false;
            if (drop.color == Color.GOTE  && drop.to.rank >= 8) return false;
        }

        // nifu: cannot drop a pawn onto a file where you already have another unpromoted pawn
        if (drop.pieceType == PieceType.PAWN) {
            for (Piece piece : board.getPiecesByColor(drop.color)) {
                if (piece instanceof Pawn && piece.getCoordinates().file == drop.to.file) {
                    return false;
                }
            }
        }

        return true;
    }
}