package controller.state;

import core.Color;
import model.Board;

public abstract class GameStateChecker {
    public abstract GameState check(Board board, Color color);
}
