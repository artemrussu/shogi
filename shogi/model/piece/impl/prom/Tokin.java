package model.piece.impl.prom;

import core.Color;
import core.Coordinates;
import core.PieceType;
import model.piece.GoldLikePiece;
import model.piece.Piece;
import model.piece.impl.Pawn;

public class Tokin extends GoldLikePiece {

	public Tokin(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}
	
    @Override
	public PieceType getPieceType() {
		return PieceType.TOKIN;
	}
}
