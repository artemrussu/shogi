package controller;

import controller.state.CheckmateGameStateChecker;
import controller.state.GameState;
import controller.state.GameStateChecker;
import controller.state.StalemateGameStateChecker;
import core.Color;
import core.Move;
import model.Board;
import java.util.List;

public abstract class Game {

    protected final Board board;
    protected Color currentTurn = Color.SENTE;
    protected GameState currentGameState = GameState.ONGOING;

    private final List<GameStateChecker> checkers = List.of(
            new StalemateGameStateChecker(),
            new CheckmateGameStateChecker()
    );

    public Game(Board board) {
        this.board = board;
    }

    public abstract void gameLoop();

    protected void executeMove(Move move) {
        board.makeMove(move);
        currentTurn = currentTurn.opposite();
        currentGameState = determineGameState(board, currentTurn);

        if (currentGameState != GameState.ONGOING) {
            System.out.println("Game ended: " + currentGameState);
        }
    }

    protected GameState determineGameState(Board board, Color color) {
        for (GameStateChecker checker : checkers) {
            GameState state = checker.check(board, color);
            if (state != GameState.ONGOING) return state;
        }
        return GameState.ONGOING;
    }
}