package menu;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import core.Color;
import mdesl.graphics.SpriteBatch;
import view.gui.AssetManager;

public class NewGameScreen extends Screen {

    private static final int BTN_W   = 300;
    private static final int BTN_H   = 70;
    private static final int BTN_GAP = 20;

    private final GameConfig.Mode mode;
    private Color selectedColor = Color.SENTE; // default

    private final MenuButton btnSente;
    private final MenuButton btnGote;
    private final MenuButton btnStart;
    private final MenuButton btnBack;

    public NewGameScreen(SpriteBatch batch, AssetManager assets, GameConfig.Mode mode) {
        super(batch, assets);
        this.mode = mode;

        int cx     = (Display.getWidth()  - BTN_W) / 2;
        int startY =  Display.getHeight() / 2 - (BTN_H + BTN_GAP);

        btnSente = new MenuButton(cx - BTN_W / 2 - BTN_GAP, startY, BTN_W, BTN_H, "PLAY AS SENTE");
        btnGote  = new MenuButton(cx + BTN_W / 2 + BTN_GAP, startY, BTN_W, BTN_H, "PLAY AS GOTE");
        btnStart = new MenuButton(cx, startY + BTN_H + BTN_GAP * 2, BTN_W, BTN_H, "START");
        btnBack  = new MenuButton(cx, startY + 2 * (BTN_H + BTN_GAP) + BTN_GAP, BTN_W, BTN_H, "BACK");
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

                    if (btnSente.isHovered(ex, ey)) selectedColor = Color.SENTE;
                    if (btnGote.isHovered(ex, ey))  selectedColor = Color.GOTE;

                    if (btnStart.isHovered(ex, ey))
                        return new GameStartScreen(batch, assets, new GameConfig(mode, selectedColor));
                    if (btnBack.isHovered(ex, ey))
                        return new MainMenuScreen(batch, assets);
                }
            }

            beginFrame();

            String modeText = (mode == GameConfig.Mode.VS_AI) ? "VS AI" : "VS HUMAN";
            drawCenteredText(modeText, 80);

            String colorText = "SELECTED: " + (selectedColor == Color.SENTE ? "SENTE" : "GOTE");
            drawCenteredText(colorText, 140);

            btnSente.render(batch, assets, btnSente.isHovered(mouseX, mouseY));
            btnGote.render(batch,  assets, btnGote.isHovered(mouseX, mouseY));
            btnStart.render(batch, assets, btnStart.isHovered(mouseX, mouseY));
            btnBack.render(batch,  assets, btnBack.isHovered(mouseX, mouseY));

            endFrame();
        }

        return null;
    }
}