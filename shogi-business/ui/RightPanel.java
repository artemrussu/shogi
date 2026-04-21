package ui;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;

public class RightPanel extends UIComponent {
    public RightPanel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);
        // Captured pieces and move history will be rendered here
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) {
    }
}