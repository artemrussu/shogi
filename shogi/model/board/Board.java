package model.board;

import java.util.HashMap;

import core.Coordinates;
import model.piece.Piece;

/**
 * Represents the game board. Manages piece placement, movement, and board state.
 */
public class Board {
    /** Map containing all pieces currently on the board, keyed by their coordinates. */
    HashMap<Coordinates, Piece> pieces = new HashMap<>();

    /**
     * Places a piece on the specified coordinates and updates the piece's internal position.
     */
    public void setPiece(Coordinates coordinates, Piece piece) {
        piece.setCoordinates(coordinates);
        pieces.put(coordinates, piece);
    }

    /**
     * Moves a piece from one coordinate to another, removing it from the original spot.
     */
    public void movePiece(Coordinates from, Coordinates to) {
        Piece piece = getPiece(from);

        removePiece(from);
        setPiece(to, piece);
    }

    /**
     * Removes the piece located at the given coordinates.
     */
    public void removePiece(Coordinates coordinates) {
        pieces.remove(coordinates);
    }

    /**
     * Determines if a square should be colored as "dark" based on its coordinates.
     */
    public static boolean isSquareDark(Coordinates coordinates) {
        return (((coordinates.file.ordinal() + 1) + coordinates.rank) % 2) == 0;
    }

    /**
     * Checks if there is no piece at the given coordinates.
     */
    public boolean isSquareEmpty(Coordinates coordinates) {
        return !pieces.containsKey(coordinates);
    }

    /**
     * Returns the piece at the given coordinates, or null if the square is empty.
     */
    public Piece getPiece(Coordinates coordinates) {
        return pieces.get(coordinates);
    }

    /**
     * Sets up the board with the standard starting configuration of pieces.
     */
	public void setupInitialPosition() {

//		for (File file : File.values()) {
//			// pawns
//			setPiece(new Coordinates(file, 3), new Pawn(Color.SENTE, new Coordinates(file, 3)));
//			setPiece(new Coordinates(file, 7), new Pawn(Color.GOTE, new Coordinates(file, 7)));
//		}
//
//		// bishops
//		setPiece(new Coordinates(File.B, 2), new Bishop(Color.SENTE, new Coordinates(File.B, 2)));
//		setPiece(new Coordinates(File.H, 8), new Bishop(Color.GOTE, new Coordinates(File.H, 8)));
//
//		// rooks
//		setPiece(new Coordinates(File.H, 2), new Rook(Color.SENTE, new Coordinates(File.H, 2)));
//		setPiece(new Coordinates(File.B, 8), new Rook(Color.GOTE, new Coordinates(File.B, 8)));
//
//		// lances
//		setPiece(new Coordinates(File.A, 1), new Lance(Color.SENTE, new Coordinates(File.A, 1)));
//		setPiece(new Coordinates(File.I, 1), new Lance(Color.SENTE, new Coordinates(File.I, 1)));
//		setPiece(new Coordinates(File.A, 9), new Lance(Color.GOTE, new Coordinates(File.A, 9)));
//		setPiece(new Coordinates(File.I, 9), new Lance(Color.GOTE, new Coordinates(File.I, 9)));
//
//		// knights
//		setPiece(new Coordinates(File.B, 1), new Knight(Color.SENTE, new Coordinates(File.B, 1)));
//		setPiece(new Coordinates(File.H, 1), new Knight(Color.SENTE, new Coordinates(File.H, 1)));
//		setPiece(new Coordinates(File.B, 9), new Knight(Color.GOTE, new Coordinates(File.A, 9)));
//		setPiece(new Coordinates(File.H, 9), new Knight(Color.GOTE, new Coordinates(File.H, 9)));
//
//		// silvers
//		setPiece(new Coordinates(File.C, 1), new Silver(Color.SENTE, new Coordinates(File.C, 1)));
//		setPiece(new Coordinates(File.G, 1), new Silver(Color.SENTE, new Coordinates(File.G, 1)));
//		setPiece(new Coordinates(File.C, 9), new Silver(Color.GOTE, new Coordinates(File.C, 9)));
//		setPiece(new Coordinates(File.G, 9), new Silver(Color.GOTE, new Coordinates(File.G, 9)));
//
//		// golds
//		setPiece(new Coordinates(File.D, 1), new Gold(Color.SENTE, new Coordinates(File.D, 1)));
//		setPiece(new Coordinates(File.F, 1), new Gold(Color.SENTE, new Coordinates(File.F, 1)));
//		setPiece(new Coordinates(File.D, 9), new Gold(Color.GOTE, new Coordinates(File.D, 9)));
//		setPiece(new Coordinates(File.F, 9), new Gold(Color.GOTE, new Coordinates(File.F, 9)));
//
//		// kings
//		setPiece(new Coordinates(File.E, 1), new King(Color.SENTE, new Coordinates(File.E, 1)));
//		setPiece(new Coordinates(File.E, 9), new King(Color.GOTE, new Coordinates(File.E, 9)));
	}

}