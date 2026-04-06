package pack;

import board.Board;
import board.BoardConsoleRenderer;
import board.BoardFEN;

public class Main {
	public static void main(String[] args) {
		
		Board board = (new BoardFEN()).fromFEN("3ppp3/4p4/4P4/p3P3p/ppPPKPPpp/p3P3p/4P4/4p4/3ppp3");
		BoardConsoleRenderer renderer = new BoardConsoleRenderer();
		// renderer.render(board);
		
		Game game = new Game(board);
		game.gameLoop();
	}
}
