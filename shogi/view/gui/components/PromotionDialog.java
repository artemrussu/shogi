package view.gui.components;

import org.lwjgl.opengl.Display;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.text.BitmapFont;
import mdesl.graphics.Texture;
import view.gui.SpriteUtil;

public class PromotionDialog {

    private static final int DIALOG_W     = 380;
    private static final int DIALOG_H     = 200;
    private static final int BUTTON_W     = 150;
    private static final int BUTTON_H     = 100;

    private boolean visible = false;

    public void show() { visible = true; }
    public void hide() { visible = false; }
    public boolean isVisible() { return visible; }

    
    public Boolean pollChoice(int mouseX, int mouseY) {
        if (!visible) return null;
        if (isInside(mouseX, mouseY, getYesX(), getYesY())) return true;
        if (isInside(mouseX, mouseY, getNoX(),  getNoY()))  return false;
        return null;
    }

    public void render(SpriteBatch batch, Texture sheet, BitmapFont font) {
        if (!visible) return;

        int dialogX = (Display.getWidth()  - DIALOG_W) / 2;
        int dialogY = (Display.getHeight() - DIALOG_H) / 2;

        SpriteUtil.drawSprite(batch, sheet, 0, 3, dialogX, 	dialogY,   DIALOG_W, DIALOG_H, 0f);
        SpriteUtil.drawSprite(batch, sheet, 6, 2, getYesX(), getYesY(), BUTTON_W, BUTTON_H, 0f);
        SpriteUtil.drawSprite(batch, sheet, 6, 2, getNoX(),  getNoY(),  BUTTON_W, BUTTON_H, 0f);

        if (font != null) {
            font.drawText(batch, "Promote?", dialogX + 80, dialogY + 20);
        } else {
        		System.err.println("Critical error: Font could not be loaded.");
        }
    }

    private int getYesX() { return Display.getWidth()  / 2 - 170; }
    private int getYesY() { return Display.getHeight() / 2 - 50;  }
    private int getNoX()  { return Display.getWidth()  / 2 + 20;  }
    private int getNoY()  { return Display.getHeight() / 2 - 50;  }

    private boolean isInside(int mouseX, int mouseY, int bx, int by) {
        return mouseX >= bx && mouseX <= bx + BUTTON_W
            && mouseY >= by && mouseY <= by + BUTTON_H;
    }
}