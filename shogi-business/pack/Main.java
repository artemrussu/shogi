package pack;

import board.Board;
import board.BoardFEN;

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

        Game game = new Game(board);
        game.gameLoop();
    }
}