package core;

import java.util.Scanner;

import controller.ConsoleGame;
import controller.GraphicGame;
import core.factory.BoardFEN;
import model.board.Board;

/**
 * The main entry point of the application.
 * Initializes the board from a FEN string and starts the game loop.
 */
public class Main {
    /**
     * Sets up the initial board configuration and launches the game.
     */
    public static void main(String[] args) {

        Board board = (new BoardFEN()).fromFEN("LNSGKGSNL/1R5B1/PPPPPPPPP/9/9/9/ppppppppp/1b5r1/lnsgkgsnl");

        Scanner sc = new Scanner(System.in);
        System.out.println("1. Console\n2. Graphic");
        String x = sc.next();
        
        if (x.equals("1")) {
        	ConsoleGame cgame = new ConsoleGame(board);
            cgame.gameLoop();
        } else {
            GraphicGame game = new GraphicGame(board);
            game.gameLoop();
        }
    }
}