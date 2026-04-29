package controller;

import java.util.List;
import java.util.Set;

import controller.state.CheckmateGameStateChecker;
import controller.state.GameState;
import controller.state.GameStateChecker;
import controller.state.StalemateGameStateChecker;
import core.Color;
import core.Coordinates;
import core.InputCoordinates;
import core.Move;
import model.Board;
import model.piece.Piece;
import view.console.ConsoleRenderer;

/**
 * Controls the game logic and flow. Manages the game loop, turn transitions,
 * and user interaction.
 */
public class ConsoleGame {

	private final Board board;
	private ConsoleRenderer renderer = new ConsoleRenderer();

	private final List<GameStateChecker> checkers = List.of(new StalemateGameStateChecker(),
			new CheckmateGameStateChecker());

	public ConsoleGame(Board board) {
		this.board = board;
	}

	/**
	 * Starts and maintains the main game loop. Handles rendering, input validation,
	 * and piece movement until the game ends.
	 */
	public void gameLoop() {
		Color colorToMove = Color.SENTE;
		GameState state = determineGameState(board, colorToMove);

		while (state == GameState.ONGOING) {
			renderer.render(board);
			System.out.println(colorToMove == Color.SENTE ? "White to move" : "Black to move");

			Move move = InputCoordinates.inputMove(board, colorToMove, renderer);

			// buildMove decides: whether promotion is needed
			Move finalMove = buildMove(board, move);

			board.makeMove(finalMove);
			colorToMove = colorToMove.opposite();
			state = determineGameState(board, colorToMove);
		}

		renderer.render(board);
		System.out.println("Game ended with state = " + state);
	}

	private Move buildMove(Board board, Move move) {
	    Piece piece = board.getPiece(move.from);

	    if (!piece.canPromote(move.to)) {
	        return move;
	    }

	    if (piece.mustPromote(move.to)) {
	        System.out.println("Piece promotes automatically!");
	        return new Move(move.from, move.to, true);
	    }

	    boolean promote = InputCoordinates.inputPromotionChoice();
	    return new Move(move.from, move.to, promote);
	}

	private GameState determineGameState(Board board, Color color) {
		for (GameStateChecker checker : checkers) {
			GameState state = checker.check(board, color);

			if (state != GameState.ONGOING) {
				return state;
			}
		}

		return GameState.ONGOING;
	}

}