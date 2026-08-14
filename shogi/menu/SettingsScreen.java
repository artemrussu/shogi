package menu;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import mdesl.graphics.SpriteBatch;
import view.gui.AssetManager;
import view.gui.ScaleManager;

public class SettingsScreen extends Screen {

    private static final int BTN_W = 300;
    private static final int BTN_H = 70;

    private final MenuButton btnBack;

    public SettingsScreen(SpriteBatch batch, AssetManager assets, ScaleManager scale) {
        super(batch, assets, scale);
        int cx = (Display.getWidth()  - BTN_W) / 2;
        int cy = Display.getHeight() / 2 + 100;
        btnBack = new MenuButton(cx, cy, BTN_W, BTN_H, "BACK");
    }

    @Override
    public Screen run() {
        while (!Display.isCloseRequested()) {

            int mouseX = Mouse.getX();
            int mouseY = Display.getHeight() - Mouse.getY();

            while (Mouse.next()) {
                if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                    int ex = Mouse.getEventX();
                    int ey = Display.getHeight() - Mouse.getEventY();
                    if (btnBack.isHovered(ex, ey))
                        return new MainMenuScreen(batch, assets, scale);
                }
            }

            beginFrame();
            drawCenteredText("SETTINGS", 80);
            drawCenteredText("COMING SOON", Display.getHeight() / 2 - 20);
            btnBack.render(batch, assets, btnBack.isHovered(mouseX, mouseY));
            endFrame();
        }

        return null;
    }
}