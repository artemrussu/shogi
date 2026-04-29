package view.gui.components;

import core.PieceType;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;

public class LeftPanel extends UIComponent {

    public LeftPanel(int x, int y, int width, int height) {
        // thickness = 50, Corner = (4, 2), Straight = (5, 2)
        super(x, y, width, height, 50, 4, 2, 5, 2); 
    }

    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) { }

	public PieceType getHandPieceFromMouse(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return null;
	}
}