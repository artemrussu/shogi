package controller;

import controller.state.GameState;
import core.Color;
import core.InputCoordinates;
import core.Move;
import model.Board;
import model.piece.Piece;
import view.console.ConsoleRenderer;

/**
 * Controls the game logic and flow (consol version).
 */
public class ConsoleGame extends Game {

    private final ConsoleRenderer renderer = new ConsoleRenderer();

    public ConsoleGame(Board board) {
        super(board);
    }

    @Override
    public void gameLoop() {
        currentGameState = determineGameState(board, currentTurn);

        while (currentGameState == GameState.ONGOING) {
            renderer.render(board);
            System.out.println(currentTurn == Color.SENTE ? "White to move" : "Black to move");

            Move move;
            if (InputCoordinates.inputWantsDrop(currentTurn, board)) {
                move = InputCoordinates.inputDropMove(board, currentTurn);
            } else {
                move = buildMove(InputCoordinates.inputMove(board, currentTurn, renderer));
            }

            executeMove(move);
        }

        renderer.render(board);
    }

    private Move buildMove(Move move) {
        Piece piece = board.getPiece(move.from);
        if (!piece.canPromote(move.to))  return move;
        if (piece.mustPromote(move.to)) {
            System.out.println("Piece promotes automatically!");
            return new Move(move.from, move.to, true);
        }
        return new Move(move.from, move.to, InputCoordinates.inputPromotionChoice());
    }
}