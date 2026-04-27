package view.gui.components;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;

public class LeftPanel extends UIComponent {

    public LeftPanel(int x, int y, int width, int height) {
        // Pass 50 as the frame thickness to the parent
        super(x, y, width, height, 50);
    }

    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);
        // Add specific left panel text or captured pieces rendering here
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) { }
}