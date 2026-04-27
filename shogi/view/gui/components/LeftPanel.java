package view.gui.components;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;

public class LeftPanel extends UIComponent {

    public LeftPanel(int x, int y, int width, int height) {
        // Example: thickness = 50, Corner = (4, 2), Straight = (5, 2)
        // (Replace these with your actual sprite indices from the sheet)
        super(x, y, width, height, 50, 4, 2, 5, 2); 
    }

    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) { }
}