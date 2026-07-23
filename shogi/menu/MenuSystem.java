package menu;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

import mdesl.graphics.SpriteBatch;
import view.gui.AssetManager;

public class MenuSystem {

    // returns GameConfig when player starts a game, null if quit
    public static GameConfig run() {
        try {
            initDisplay();
            SpriteBatch  batch  = new SpriteBatch();
            AssetManager assets = new AssetManager();

            if (!assets.loadAssets()) {
                System.err.println("Menu: failed to load assets");
                Display.destroy();
                return null;
            }

            Screen current = new MainMenuScreen(batch, assets);

            while (current != null && !Display.isCloseRequested()) {
                Screen next = current.run();

                if (next instanceof GameStartScreen) {
                    GameConfig config = ((GameStartScreen) next).getConfig();
                    // keep display open — GraphicGame will reuse it
                    return config;
                }

                current = next;
            }

        } catch (LWJGLException e) {
            System.err.println("Menu display error: " + e.getMessage());
        }

        Display.destroy();
        return null;
    }

    private static void initDisplay() throws LWJGLException {
        Display.setDisplayMode(Display.getDesktopDisplayMode());
        Display.setFullscreen(true);
        Display.setTitle("Shogi");
        Display.setVSyncEnabled(true);
        Display.create();

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.2f, 0.3f, 0.2f, 1f);
    }
}