package menu;

import mdesl.graphics.SpriteBatch;
import view.gui.AssetManager;
import view.gui.ScaleManager;

// sentinel screen — signals Main to launch the game
public class GameStartScreen extends Screen {

    private final GameConfig config;

    public GameStartScreen(SpriteBatch batch, AssetManager assets, GameConfig config, ScaleManager scale) {
        super(batch, assets, scale);
        this.config = config;
    }

    public GameConfig getConfig() {
        return config;
    }

    @Override
    public Screen run() {
        return null; // never runs directly
    }
}