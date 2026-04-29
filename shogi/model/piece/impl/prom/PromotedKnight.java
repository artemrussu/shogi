package model.piece.impl.prom;

import core.Color;
import core.Coordinates;
import core.PieceType;
import model.piece.GoldLikePiece;

public class PromotedKnight extends GoldLikePiece {

	public PromotedKnight(Color color, Coordinates coordinates) {
		super(color, coordinates);
	}

    @Override
	public PieceType getPieceType() {
		return PieceType.PROMOTED_KNIGHT;
	}
}
