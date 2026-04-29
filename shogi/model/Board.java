package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import core.Color;
import core.Coordinates;
import core.DropMove;
import core.Move;
import core.PieceType;
import core.factory.PieceFactory;
import model.piece.Piece;

/**
 * Represents the game board. Manages piece placement, movement, and board state.
 */
public class Board {
    /** Map containing all pieces currently on the board, keyed by their coordinates. */
    HashMap<Coordinates, Piece> pieces = new HashMap<>();
    
    public Hand hand = new Hand();
    public List<Move> moves = new ArrayList<>();
    public final String startingFen;
    
    public Board(String startingFen) {
        this.startingFen = startingFen;
    }
    
    /***************************/
    public void makeMove(Move move) {

        // --- drop from hand ---
        if (move instanceof DropMove) {
            DropMove drop = (DropMove) move;
            hand.remove(drop.color, drop.pieceType);
            Piece piece = PieceFactory.create(drop.pieceType, drop.color, drop.to);
            setPiece(drop.to, piece);
            moves.add(move);
            return;
        }

        // --- normal move ---
        Piece piece = getPiece(move.from);

        // capture: put the base version of the piece into the attacker's hand
        if (!isSquareEmpty(move.to)) {
            Piece captured = getPiece(move.to);
            hand.add(piece.getColor(), captured.getBaseType());
        }

        removePiece(move.from);

        // promotion: create a new piece via factory
        if (move.promote) {
            PieceType promotedType = piece.getPieceType().getPromotedType();
            Piece promoted = PieceFactory.create(promotedType, piece.getColor(), move.to);
            setPiece(move.to, promoted);
        } else {
            setPiece(move.to, piece);
        }

        moves.add(move);
    }

    public void removePiece(Coordinates coordinates) {
        pieces.remove(coordinates);
    }
    /***************************/

    public Piece getPiece(Coordinates coordinates) {
        return pieces.get(coordinates);
    }
    
    public void setPiece(Coordinates coordinates, Piece piece) {
        piece.setCoordinates(coordinates);
        pieces.put(coordinates, piece);
    }
    
    public static boolean isSquareDark(Coordinates coordinates) {
        return (((coordinates.file.ordinal() + 1) + coordinates.rank) % 2) == 0;
    }

    public boolean isSquareEmpty(Coordinates coordinates) {
        return !pieces.containsKey(coordinates);
    }

	public boolean isSquareAttackedByColor(Coordinates coordinates, Color color) {
		List<Piece> pieces = getPiecesByColor(color);
		
		for (Piece piece : pieces) {
			Set<Coordinates> attackedSquares = piece.getAttackedSquares(this);
			
			if (attackedSquares.contains(coordinates)) {
				return true;
			}
		}
		
		return false;
	}

	public List<Piece> getPiecesByColor(Color color) {
		List<Piece> result = new ArrayList<>();
		
		for (Piece piece : pieces.values()) {
			if (piece.getColor() == color) {
				result.add(piece);
			}
		}
		
		return result;
	}
	
	/**
     * Sets up the board with the standard starting configuration of pieces.
     * Configurated from BoardFEN. This version for debug only.
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