package menu;

import mdesl.graphics.SpriteBatch;
import view.gui.AssetManager;
import view.gui.SpriteUtil;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public abstract class Screen {

    private static final int BG_COL       = 3;
    private static final int BG_ROW       = 2;
    private static final int BG_TILE_SIZE = 256;

    protected final SpriteBatch  batch;
    protected final AssetManager assets;

    public Screen(SpriteBatch batch, AssetManager assets) {
        this.batch  = batch;
        this.assets = assets;
    }

    // returns next Screen, null = quit
    public abstract Screen run();

    protected void beginFrame() {
        glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();
        drawBackground();
    }

    protected void endFrame() {
        batch.end();
        Display.update();
        Display.sync(60);
    }

    protected void drawBackground() {
        int screenW = Display.getWidth();
        int screenH = Display.getHeight();
        for (int x = 0; x < screenW; x += BG_TILE_SIZE) {
            for (int y = 0; y < screenH; y += BG_TILE_SIZE) {
                SpriteUtil.drawSprite(batch, assets.spriteSheet,
                        BG_COL, BG_ROW, x, y, BG_TILE_SIZE, BG_TILE_SIZE, 0f);
            }
        }
    }

    protected void drawCenteredText(String text, int y) {
        if (assets.gameFont == null) return;
        int textWidth = assets.gameFont.getWidth(text);
        batch.setColor(0.424f, 0.161f, 0.251f, 1f);
        assets.gameFont.drawText(batch, text, (Display.getWidth() - textWidth) / 2, y);
        batch.setColor(1f, 1f, 1f, 1f);
    }
}