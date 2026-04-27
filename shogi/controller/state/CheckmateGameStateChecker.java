package controller.state;
import controller.MoveValidator;
import core.Color;
import core.Coordinates;
import core.Move;
import model.board.Board;
import model.piece.Piece;

public class CheckmateGameStateChecker extends GameStateChecker {
    @Override
    public GameState check(Board board, Color color) {
        // ... (Keep the initial "is King in check" check) ...

        for (Piece piece : board.getPiecesByColor(color)) {
            for (Coordinates to : piece.getAvailableMoveSquares(board)) {
                // Use the utility!
                if (MoveValidator.isKingSafeAfterMove(board, color, new Move(piece.getCoordinates(), to))) {
                    return GameState.ONGOING; // Found a way out!
                }
            }
        }
        return (color == Color.SENTE) ? GameState.CHECKMATE_TO_BLACK_KING : GameState.CHECKMATE_TO_WHITE_KING;
    }
}