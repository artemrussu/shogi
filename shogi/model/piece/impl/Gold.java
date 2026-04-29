package model.piece.impl;

import core.Color;
import core.Coordinates;
import core.PieceType;
import model.piece.GoldLikePiece;

/**
 *
 */
public class Gold extends GoldLikePiece {

	public Gold(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}
	
    @Override
	public PieceType getPieceType() {
		return PieceType.GOLD;
	}
}