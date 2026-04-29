package model.piece.impl.prom;

import core.Color;
import core.Coordinates;
import core.PieceType;
import model.piece.GoldLikePiece;

public class PromotedLance extends GoldLikePiece {

	public PromotedLance(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

    @Override
	public PieceType getPieceType() {
		return PieceType.PROMOTED_LANCE;
	}
}
