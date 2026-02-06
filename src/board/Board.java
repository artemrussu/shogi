package board;

import java.util.HashMap;

import pack.Coordinates;
import piece.Piece;
import pack.Color;
import pack.File;
import piece.Pawn;
import piece.King;

public class Board {
	HashMap<Coordinates, Piece> pieces = new HashMap<>();

	public void setPiece(Coordinates coordinates, Piece piece) {
		piece.coordinates = coordinates;
		pieces.put(coordinates, piece);
	}
	
	public void movePiece(Coordinates from, Coordinates to) {
		Piece piece = getPiece(from);
		
		removePiece(from);
		setPiece(to, piece);
	}
	
	public void removePiece(Coordinates coordinates) {
		pieces.remove(coordinates);
	}

	public static boolean isSquareDark(Coordinates coordinates) {
		return (((coordinates.file.ordinal() + 1) + coordinates.rank) % 2) == 0;
	}

	public boolean isSquareEmpty(Coordinates coordinates) {
		return !pieces.containsKey(coordinates);
	}

	public Piece getPiece(Coordinates coordinates) {
		return pieces.get(coordinates);
	}
	
	public void setupInitialPosition() {
		
		// pawns
		setPiece(new Coordinates(File.A, 4), new Pawn(Color.BLACK, new Coordinates(File.A, 4)));
		setPiece(new Coordinates(File.A, 5), new Pawn(Color.BLACK, new Coordinates(File.A, 5)));
		setPiece(new Coordinates(File.A, 6), new Pawn(Color.BLACK, new Coordinates(File.A, 6)));
		setPiece(new Coordinates(File.B, 5), new Pawn(Color.BLACK, new Coordinates(File.B, 5)));
		
		setPiece(new Coordinates(File.I, 4), new Pawn(Color.BLACK, new Coordinates(File.I, 4)));
		setPiece(new Coordinates(File.I, 5), new Pawn(Color.BLACK, new Coordinates(File.I, 5)));
		setPiece(new Coordinates(File.I, 6), new Pawn(Color.BLACK, new Coordinates(File.I, 6)));
		setPiece(new Coordinates(File.H, 5), new Pawn(Color.BLACK, new Coordinates(File.H, 5)));
		
		setPiece(new Coordinates(File.D, 1), new Pawn(Color.BLACK, new Coordinates(File.D, 1)));
		setPiece(new Coordinates(File.E, 1), new Pawn(Color.BLACK, new Coordinates(File.E, 1)));
		setPiece(new Coordinates(File.F, 1), new Pawn(Color.BLACK, new Coordinates(File.F, 1)));
		setPiece(new Coordinates(File.D, 9), new Pawn(Color.BLACK, new Coordinates(File.D, 9)));
		setPiece(new Coordinates(File.E, 9), new Pawn(Color.BLACK, new Coordinates(File.E, 9)));
		setPiece(new Coordinates(File.F, 9), new Pawn(Color.BLACK, new Coordinates(File.F, 9)));
		setPiece(new Coordinates(File.E, 2), new Pawn(Color.BLACK, new Coordinates(File.E, 2)));
		setPiece(new Coordinates(File.E, 8), new Pawn(Color.BLACK, new Coordinates(File.E, 8)));
		
		setPiece(new Coordinates(File.E, 3), new King(Color.WHITE, new Coordinates(File.E, 3)));
		setPiece(new Coordinates(File.E, 4), new King(Color.WHITE, new Coordinates(File.E, 4)));
		setPiece(new Coordinates(File.E, 6), new King(Color.WHITE, new Coordinates(File.E, 6)));
		setPiece(new Coordinates(File.E, 7), new King(Color.WHITE, new Coordinates(File.E, 7)));
		
		setPiece(new Coordinates(File.C, 5), new King(Color.WHITE, new Coordinates(File.C, 5)));
		setPiece(new Coordinates(File.D, 5), new King(Color.WHITE, new Coordinates(File.D, 5)));
		setPiece(new Coordinates(File.F, 5), new King(Color.WHITE, new Coordinates(File.F, 5)));
		setPiece(new Coordinates(File.G, 5), new King(Color.WHITE, new Coordinates(File.G, 5)));
		
		
		// kings
		setPiece(new Coordinates(File.E, 5), new King(Color.WHITE, new Coordinates(File.E, 5)));
	}
	
}