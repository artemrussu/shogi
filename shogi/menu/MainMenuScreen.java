package menu;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import mdesl.graphics.SpriteBatch;
import view.gui.AssetManager;

public class MainMenuScreen extends Screen {

    private static final int BTN_W   = 400;
    private static final int BTN_H   = 70;
    private static final int BTN_GAP = 20;

    private final MenuButton btnVsHuman;
    private final MenuButton btnVsAI;
    private final MenuButton btnSettings;
    private final MenuButton btnQuit;

    public MainMenuScreen(SpriteBatch batch, AssetManager assets) {
        super(batch, assets);

        int cx     = (Display.getWidth()  - BTN_W) / 2;
        int startY =  Display.getHeight() / 2 - 2 * (BTN_H + BTN_GAP);

        btnVsHuman  = new MenuButton(cx, startY,                          BTN_W, BTN_H, "PLAY VS HUMAN");
        btnVsAI     = new MenuButton(cx, startY +     BTN_H + BTN_GAP,   BTN_W, BTN_H, "PLAY VS AI");
        btnSettings = new MenuButton(cx, startY + 2 * (BTN_H + BTN_GAP), BTN_W, BTN_H, "SETTINGS");
        btnQuit     = new MenuButton(cx, startY + 3 * (BTN_H + BTN_GAP), BTN_W, BTN_H, "QUIT");
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

                    if (btnVsHuman.isHovered(ex, ey))
                        return new NewGameScreen(batch, assets, GameConfig.Mode.VS_HUMAN);
                    if (btnVsAI.isHovered(ex, ey))
                        return new NewGameScreen(batch, assets, GameConfig.Mode.VS_AI);
                    if (btnSettings.isHovered(ex, ey))
                        return new SettingsScreen(batch, assets);
                    if (btnQuit.isHovered(ex, ey))
                        return null;
                }
            }

            beginFrame();
            drawCenteredText("SHOGI", 80);
            btnVsHuman.render(batch,  assets, btnVsHuman.isHovered(mouseX, mouseY));
            btnVsAI.render(batch,     assets, btnVsAI.isHovered(mouseX, mouseY));
            btnSettings.render(batch, assets, btnSettings.isHovered(mouseX, mouseY));
            btnQuit.render(batch,     assets, btnQuit.isHovered(mouseX, mouseY));
            endFrame();
        }

        return null;
    }
}