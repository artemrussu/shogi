package view.gui.components;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import view.gui.SpriteUtil;

public abstract class UIComponent {
    protected int x, y, width, height, frameThickness;

    public UIComponent(int x, int y, int width, int height, int frameThickness) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.frameThickness = frameThickness;
    }

    public abstract void render(SpriteBatch batch, Texture spriteSheet);
    public abstract void handleMouseClick(int mouseX, int mouseY);

    protected void drawFrame(SpriteBatch batch, Texture sheet) {
        int cornerCol = 1; int cornerRow = 2;
        int straightCol = 2; int straightRow = 2;
        int T = frameThickness;

        // 1. Corners
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x, y, T, T, 0f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x + width - T, y, T, T, 90f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x + width - T, y + height - T, T, T, 180f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x, y + height - T, T, T, 270f);

        // 2. Horizontal Edges
        for (int i = x + T; i < x + width - T; i += T) {
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, i, y, T, T, 0f);
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, i, y + height - T, T, T, 180f);
        }

        // 3. Vertical Edges
        for (int j = y + T; j < y + height - T; j += T) {
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x, j, T, T, 270f);
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x + width - T, j, T, T, 90f);
        }
    }
}