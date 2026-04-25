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
import model.board.Board;
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

			if (colorToMove == Color.SENTE) {
				System.out.println("White to move");
			} else {
				System.out.println("Black to move");
			}

			Move move = InputCoordinates.inputMove(board, colorToMove, renderer);

			// make move
			board.makeMove(move);

			// pass move
			colorToMove = colorToMove.opposite();

			state = determineGameState(board, colorToMove);
		}

		renderer.render(board);
		System.out.println("Game ended with state = " + state);
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