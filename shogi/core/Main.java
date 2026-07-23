package core;

import java.util.Scanner;

import controller.ConsoleGame;
import controller.GraphicGame;
import core.factory.BoardFEN;
import menu.GameConfig;
import menu.MenuSystem;
import model.Board;

/**
 * The main entry point of the application.
 * Initializes the board from a FEN string and starts the game loop.
 */
public class Main {
    /**
     * Sets up the initial board configuration and launches the game.
     */
    public static void main(String[] args) {

    	GameConfig config = MenuSystem.run();

        if (config == null) return;

        String startFen = "LNSGKGSNL/1R5B1/PPPPPPPPP/9/9/9/ppppppppp/1b5r1/lnsgkgsnl";
        Board board = new BoardFEN().fromFEN(startFen);

        // TODO: config.mode → VS_HUMAN or VS_AI
        // TODO: config.playerColor

        GraphicGame game = new GraphicGame(board);
        game.gameLoop();
    }
}