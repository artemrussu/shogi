package view.gui;

import core.Color;
import core.PieceType;
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
    
    public static void drawPiece(SpriteBatch batch, Texture sheet, Piece piece,
            float x, float y, float size, Color bottomPlayer) {
    		int col = getPieceCol(piece.getPieceType());
    		int row = getPieceRow(piece.getPieceType());
    		float rotation = (piece.getColor() == bottomPlayer) ? 0f : 180f;
    		drawSprite(batch, sheet, col, row, x, y, size, size, rotation);
    }

    /**
     * Maps piece type to sprite sheet coordinates and draws it.
     */
    public static int getPieceCol(PieceType type) {
        switch (type) {
            case PAWN:            return 7;
            case LANCE:           return 2;
            case KNIGHT:          return 3;
            case SILVER:          return 4;
            case GOLD:            return 5;
            case KING:            return 6;
            case ROOK:            return 0;
            case BISHOP:          return 1;
            case TOKIN:           return 0;
            case PROMOTED_LANCE:  return 3;
            case PROMOTED_KNIGHT: return 4;
            case PROMOTED_SILVER: return 7;
            case DRAGON:          return 6;
            case HORSE:           return 5;
            default:              return 0;
        }
    }

    public static int getPieceRow(PieceType type) {
        switch (type) {
            case ROOK:            return 1;
            case BISHOP:          return 1;
            case DRAGON:          return 1;
            case HORSE:           return 1;
            case PROMOTED_LANCE:  return 1;
            case PROMOTED_KNIGHT: return 1;
            case PROMOTED_SILVER: return 1;
            case TOKIN:           return 2;
            default:              return 0;
        }
    }
}