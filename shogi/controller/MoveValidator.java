package controller;

import core.Color;
import core.Move;
import core.factory.BoardFEN;
import model.board.Board;
import model.piece.Piece;
import model.piece.impl.King;

public class MoveValidator {
    public static boolean isKingSafeAfterMove(Board board, Color color, Move move) {
        // 1. Basic piece movement check
        Piece piece = board.getPiece(move.from);
        if (!piece.getAvailableMoveSquares(board).contains(move.to)) return false;

        // 2. The "King Safety" simulation
        Board clone = new BoardFEN().copy(board);
        clone.makeMove(move);
        
        Piece king = clone.getPiecesByColor(color).stream()
                          .filter(p -> p instanceof King).findFirst().orElse(null);
        
        if (king == null) return true; // For testing scenarios without a king
        return !clone.isSquareAttackedByColor(king.getCoordinates(), color.opposite());
    }
}