package core;

import java.util.Scanner;

import controller.ConsoleGame;
import controller.GraphicGame;
import core.factory.BoardFEN;
import menu.GameConfig;
import menu.MenuSystem;
import model.Board;

public class Main {
    public static void main(String[] args) {
        GameConfig config = MenuSystem.run();
        if (config == null) return;

        String startFen = "LNSGKGSNL/1R5B1/PPPPPPPPP/9/9/9/ppppppppp/1b5r1/lnsgkgsnl";
        Board board = new BoardFEN().fromFEN(startFen);

        GraphicGame game = new GraphicGame(board, config);
        game.gameLoop();
    }
}