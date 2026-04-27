package view.gui.components;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import view.gui.SpriteUtil;

public abstract class UIComponent {
    protected int x, y, width, height, frameThickness;
    
    // New fields for sprite coordinates
    protected int cornerCol, cornerRow;
    protected int straightCol, straightRow;

    public UIComponent(int x, int y, int width, int height, int thickness, 
                       int cCol, int cRow, int sCol, int sRow) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.frameThickness = thickness;
        this.cornerCol = cCol;
        this.cornerRow = cRow;
        this.straightCol = sCol;
        this.straightRow = sRow;
    }

    public abstract void render(SpriteBatch batch, Texture spriteSheet);
    public abstract void handleMouseClick(int mouseX, int mouseY);

    protected void drawFrame(SpriteBatch batch, Texture sheet) {
        int T = frameThickness;

        // 1. Corners (Using the coordinates passed to constructor)
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x, y, T, T, 0f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x + width - T, y, T, T, 90f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x + width - T, y + height - T, T, T, 180f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x, y + height - T, T, T, 270f);

        // 2. Edges (Your perfect rollback loops)
        for (int i = x + T; i < x + width - T; i += T) {
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, i, y, T, T, 0f);
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, i, y + height - T, T, T, 180f);
        }
        for (int j = y + T; j < y + height - T; j += T) {
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x, j, T, T, 270f);
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x + width - T, j, T, T, 90f);
        }
    }
}