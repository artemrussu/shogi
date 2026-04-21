package pack;

import java.util.Set;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import board.Board;
import piece.Piece;

public class GraphicGame {
	private final Board board;
	private GraphicRender renderer = new GraphicRender();

	private Coordinates selectedSquare = null;
	private Color currentTurn = Color.SENTE;

	public GraphicGame(Board board) {
		this.board = board;
	}

	/**
	 * Main game loop handling input and rendering.
	 */
	public void gameLoop() {
		// Pass the board for UI component initialization
		renderer.init(board);

		while (!Display.isCloseRequested()) {
			handleInput();

			Piece selectedPiece = (selectedSquare != null) ? board.getPiece(selectedSquare) : null;

			// Render the current game state
			renderer.render(board, selectedPiece);

			renderer.processEvents();
			Display.sync(60);
		}

		Display.destroy();
	}

	/**
	 * Handles mouse input events and game logic interactions.
	 */
	private void handleInput() {
		while (Mouse.next()) {
			// Check for Left Mouse Button (button 0) press
			if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
				// The renderer knows which panel the click occurred on
				Coordinates clickedCoords = renderer.getCoordinatesFromMouse();

				// If clicked outside the board (e.g., on side panels or background)
				if (clickedCoords == null) {
					selectedSquare = null;
					continue;
				}

				if (selectedSquare == null) {
					// Selection logic: select a piece of the current turn's color
					if (!board.isSquareEmpty(clickedCoords)) {
						Piece piece = board.getPiece(clickedCoords);

						if (piece.getColor() == currentTurn) {
							selectedSquare = clickedCoords;
							System.out.println(
									"Selected: " + piece.getClass().getSimpleName() + " (" + currentTurn + ")");
						} else {
							System.out.println("It is currently " + currentTurn + "'s turn!");
						}
					}
				} else {
					// Attempt to move the previously selected piece
					Piece pieceToMove = board.getPiece(selectedSquare);
					Set<Coordinates> availableMoves = pieceToMove.getAvailableMoveSquares(board);

					if (availableMoves.contains(clickedCoords)) {
						board.movePiece(selectedSquare, clickedCoords);

						// Switch turns after a successful move
						currentTurn = (currentTurn == Color.SENTE) ? Color.GOTE : Color.SENTE;

						System.out.println("Turn completed. Next: " + currentTurn);
						selectedSquare = null;
					} else {
						// Re-select if another friendly piece is clicked
						if (!board.isSquareEmpty(clickedCoords)
								&& board.getPiece(clickedCoords).getColor() == currentTurn) {
							selectedSquare = clickedCoords;
						} else {
							// Deselect if clicking empty space or invalid move
							selectedSquare = null;
						}
					}
				}
			}
		}
	}
}