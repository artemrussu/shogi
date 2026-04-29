package view.gui;

import core.Color;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import model.piece.Piece;

public class SpriteUtil {
    
    public static final int TEXTURE_TILE_SIZE = 256;

    public static void drawSprite(SpriteBatch batch, Texture spriteSheet, int col, int row, 
                                  float x, float y, float width, float height, float rotationDegrees) {
        float srcX = col * TEXTURE_TILE_SIZE;
        float srcY = row * TEXTURE_TILE_SIZE;
        float u = srcX / spriteSheet.getWidth();
        float v = srcY / spriteSheet.getHeight();
        float u2 = (srcX + TEXTURE_TILE_SIZE) / spriteSheet.getWidth();
        float v2 = (srcY + TEXTURE_TILE_SIZE) / spriteSheet.getHeight();
        float rotationRadians = (float) Math.toRadians(rotationDegrees);

        batch.draw(spriteSheet, x, y, width, height, width / 2f, height / 2f, rotationRadians, u, v, u2, v2);
    }

    /**
     * Maps piece type to sprite sheet coordinates and draws it.
     */
    public static void drawPiece(SpriteBatch batch, Texture sheet, Piece piece, float x, float y, float size) {
        int col = 0; int row = 0; 

        switch (piece.getPieceType()) {
        case PAWN:   col = 0; row = 0; break; 		case TOKIN:				col = 0; row = 2; break;
        case LANCE:  col = 2; row = 0; break;		case PROMOTED_LANCE:  	col = 3; row = 2; break;
        case KNIGHT: col = 3; row = 0; break;		case PROMOTED_KNIGHT: 	col = 4; row = 2; break;
        case SILVER: col = 4; row = 0; break;		case PROMOTED_SILVER: 	col = 5; row = 2; break;
        case GOLD:   col = 5; row = 0; break;
        case KING:   col = 6; row = 0; break;
        case ROOK:   col = 0; row = 1; break;		case DRAGON: 			col = 1; row = 2; break;
        case BISHOP: col = 1; row = 1; break;		case HORSE: 				col = 2; row = 2; break;
    }

        float rotation = (piece.getColor() == Color.SENTE) ? 180f : 0f;
        drawSprite(batch, sheet, col, row, x, y, size, size, rotation);
    }
}