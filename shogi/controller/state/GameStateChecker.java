package controller.state;

import core.Color;
import model.board.Board;

public abstract class GameStateChecker {
    public abstract GameState check(Board board, Color color);
}
