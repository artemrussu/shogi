package menu;

import mdesl.graphics.SpriteBatch;
import view.gui.AssetManager;
import view.gui.SpriteUtil;

public class MenuButton {

    private static final int BTN_COL       = 6;
    private static final int BTN_ROW       = 2;
    private static final int BTN_HOVER_COL = 5;
    private static final int BTN_HOVER_ROW = 2;

    private final int    x, y, width, height;
    private final String label;

    public MenuButton(int x, int y, int width, int height, String label) {
        this.x      = x;
        this.y      = y;
        this.width  = width;
        this.height = height;
        this.label  = label;
    }

    public void render(SpriteBatch batch, AssetManager assets, boolean hovered) {
        int col = hovered ? BTN_HOVER_COL : BTN_COL;
        int row = hovered ? BTN_HOVER_ROW : BTN_ROW;

        SpriteUtil.drawSprite(batch, assets.spriteSheet,
                col, row, x, y, width, height, 0f);

        if (assets.gameFont != null) {
            int textWidth = assets.gameFont.getWidth(label);
            int textX     = x + (width  - textWidth) / 2;
            int textY     = y + (height / 2) - 8;

            batch.setColor(0.424f, 0.161f, 0.251f, 1f);
            assets.gameFont.drawText(batch, label, textX, textY);
            batch.setColor(1f, 1f, 1f, 1f);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width
            && mouseY >= y && mouseY <= y + height;
    }
}