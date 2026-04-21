package view.gui.components;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;

public abstract class UIComponent {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public UIComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Each component must be able to render itself.
     */
    public abstract void render(SpriteBatch batch, Texture spriteSheet);

    /**
     * Each component handles its own mouse interactions.
     */
    public abstract void handleMouseClick(int mouseX, int mouseY);

    /**
     * Draws the panel border. To be implemented by subclasses.
     */
    protected void drawFrame(SpriteBatch batch, Texture sheet) {
        // Logic for drawing panel borders will be implemented here
    }
}