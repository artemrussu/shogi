package view.gui.components;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import view.gui.SpriteUtil;

public class RightPanel extends UIComponent {
    private final int FRAME_THICKNESS = 50; 

    public RightPanel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    protected void drawFrame(SpriteBatch batch, Texture sheet) {
        int cornerCol = 1; int cornerRow = 2;
        int straightCol = 2; int straightRow = 2;
        int T = FRAME_THICKNESS;

        // 1. Draw 4 Corners (Always at the exact four edges)
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x, y, T, T, 0f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x + width - T, y, T, T, 90f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x + width - T, y + height - T, T, T, 180f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x, y + height - T, T, T, 270f);

        // 2. Horizontal Edges
        for (int i = T; i < width - T; i += T) {
            // If the next tile would overlap the corner, we "squish" this last tile to fit perfectly
            int drawW = (i + T > width - T) ? (width - T - i) : T;
            
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x + i, y, drawW, T, 0f);
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x + i, y + height - T, drawW, T, 180f);
        }

        // 3. Vertical Edges
        for (int j = T; j < height - T; j += T) {
            // Same logic: if this tile is the last one, squish its height to fill the gap
            int drawH = (j + T > height - T) ? (height - T - j) : T;
            
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x, y + j, T, drawH, 270f);
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x + width - T, y + j, T, drawH, 90f);
        }
    }

    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) { }
}