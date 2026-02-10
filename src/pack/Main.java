package pack;

import board.Board;

public class Main {
	public static void main(String[] args) {
		Board board = new Board();
		board.setupInitialPosition();
		Game game = new Game(board);
		game.gameLoop();
	}
}
