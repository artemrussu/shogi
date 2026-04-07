package board;

import java.util.HashMap;
import pack.Coordinates;
import piece.Piece;
//import piece.Rook;
//import piece.Silver;
//import pack.Color;
//import pack.File;
//import piece.Pawn;
//import piece.Bishop;
//import piece.Gold;
//import piece.King;
//import piece.Knight;
//import piece.Lance;

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