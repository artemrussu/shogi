package pack;

import board.Board;
import board.BoardConsoleRenderer;
import board.BoardFEN;

public class Main {
	public static void main(String[] args) {

		Board board = (new BoardFEN()).fromFEN("LNSGKGSNL/1R5B1/PPPPPPPPP/9/9/9/ppppppppp/1b5r1/lnsgkgsnl");
		// BoardConsoleRenderer renderer = new BoardConsoleRenderer();
		// renderer.render(board);

		Game game = new Game(board);
		game.gameLoop();
	}
}
