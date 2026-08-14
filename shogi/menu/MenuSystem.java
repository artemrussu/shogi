package menu;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

import mdesl.graphics.SpriteBatch;
import view.gui.AssetManager;
import view.gui.ScaleManager;

public class MenuSystem {

    public static GameConfig run() {
        try {
            // 1. display first
            Display.setDisplayMode(Display.getDesktopDisplayMode());
            Display.setFullscreen(true);
            Display.setTitle("Shogi");
            Display.setVSyncEnabled(true);
            Display.create();

            // 2. opengl setup
            glDisable(GL_DEPTH_TEST);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glClearColor(0.2f, 0.3f, 0.2f, 1f);

            // 3. resources — only after Display.create()
            SpriteBatch  batch  = new SpriteBatch();
            AssetManager assets = new AssetManager();
            ScaleManager scale  = new ScaleManager();

            if (!assets.loadAssets()) {
                System.err.println("Menu: failed to load assets");
                Display.destroy();
                return null;
            }

            // 4. screen loop
            Screen current = new MainMenuScreen(batch, assets, scale);

            while (current != null && !Display.isCloseRequested()) {
                Screen next = current.run();

                if (next instanceof GameStartScreen) {
                    GameConfig config = ((GameStartScreen) next).getConfig();
                    return config; // keep display open — GraphicGame will reuse it
                }

                current = next;
            }

        } catch (LWJGLException e) {
            System.err.println("Menu display error: " + e.getMessage());
        }

        Display.destroy();
        return null;
    }
}