package view.gui;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;

/**
 * Utility class to handle extracting and drawing sprites from a spritesheet.
 */
public class SpriteUtil {
    
    // The size of a single sprite in the actual image file (e.g., shogi.png)
    public static final int TEXTURE_TILE_SIZE = 256;

    /**
     * Draws a sprite from the spritesheet using grid coordinates (col, row).
     * * @param batch The SpriteBatch used for drawing.
     * @param spriteSheet The source texture.
     * @param col The column index of the sprite in the spritesheet (starts at 0).
     * @param row The row index of the sprite in the spritesheet (starts at 0).
     * @param x The target X coordinate on the screen.
     * @param y The target Y coordinate on the screen.
     * @param width The target width to draw on the screen.
     * @param height The target height to draw on the screen.
     * @param rotationDegrees The rotation angle in degrees.
     */
    public static void drawSprite(SpriteBatch batch, Texture spriteSheet, int col, int row, 
                                  float x, float y, float width, float height, float rotationDegrees) {
        
        // Calculate the pixel position in the spritesheet
        float srcX = col * TEXTURE_TILE_SIZE;
        float srcY = row * TEXTURE_TILE_SIZE;
        
        // Calculate UV coordinates (0.0 to 1.0) for OpenGL
        float u = srcX / spriteSheet.getWidth();
        float v = srcY / spriteSheet.getHeight();
        float u2 = (srcX + TEXTURE_TILE_SIZE) / spriteSheet.getWidth();
        float v2 = (srcY + TEXTURE_TILE_SIZE) / spriteSheet.getHeight();

        float rotationRadians = (float) Math.toRadians(rotationDegrees);

        // Center origin for rotation
        float originX = width / 2f;
        float originY = height / 2f;

        // Draw the mapped texture region
        batch.draw(spriteSheet, 
                   x, y, 
                   width, height, 
                   originX, originY, 
                   rotationRadians, 
                   u, v, u2, v2);
    }
}