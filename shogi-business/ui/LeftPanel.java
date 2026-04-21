package ui;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;

public class LeftPanel extends UIComponent {
    public LeftPanel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);
        // Player information will be rendered here
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) {
    }
}