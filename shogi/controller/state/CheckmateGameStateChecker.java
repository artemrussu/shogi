package controller.state;

import java.util.List;
import java.util.Set;

import core.Color;
import core.Coordinates;
import core.Move;
import core.factory.BoardFEN;
import model.board.Board;
import model.piece.Piece;
import model.piece.impl.King;

public class CheckmateGameStateChecker extends GameStateChecker {
    @Override
    public GameState check(Board board, Color color) {
        // check if king in check
        // check that there is no move to prevent this check

        // we trust that there is king on the board
        Piece king = board.getPiecesByColor(color).stream().filter(piece -> piece instanceof King).findFirst().get();

        if (!board.isSquareAttackedByColor(king.getCoordinates(), color.opposite())) {
            return GameState.ONGOING;
        }

        List<Piece> pieces = board.getPiecesByColor(color);
        for (Piece piece : pieces) {
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

            for (Coordinates coordinates : availableMoveSquares) {
                Board clone = new BoardFEN().copy(board);
                clone.makeMove(new Move(piece.getCoordinates(), coordinates));

                Piece clonedKing = clone.getPiecesByColor(color).stream().filter(p -> p instanceof King).findFirst().get();

                if (!clone.isSquareAttackedByColor(clonedKing.getCoordinates(), color.opposite())) {
                    return GameState.ONGOING;
                }
            }
        }

        if (color == Color.SENTE) {
            return GameState.CHECKMATE_TO_WHITE_KING;
        } else {
            return GameState.CHECKMATE_TO_BLACK_KING;
        }
    }
}
