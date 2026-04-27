package view.gui;

import mdesl.graphics.Texture;
import mdesl.graphics.text.BitmapFont;
import java.io.IOException;
import java.net.URL;

public class AssetManager {
    public Texture spriteSheet;
    public BitmapFont gameFont;

    /**
     * Loads assets and returns true if successful.
     */
    public boolean loadAssets() {
        try {
            // Load Spritesheet
            URL texUrl = getClass().getClassLoader().getResource("shogi.png");
            if (texUrl == null) throw new IOException("shogi.png not found");
            spriteSheet = new Texture(texUrl);

            // Load Font
            URL fontDef = getClass().getClassLoader().getResource("wide-latin.fnt");
            URL fontTex = getClass().getClassLoader().getResource("wide-latin_0.png");
            if (fontDef != null && fontTex != null) {
                gameFont = new BitmapFont(fontDef, fontTex);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Failed to load assets: " + e.getMessage());
            return false;
        }
    }

    public void dispose() {
        if (spriteSheet != null) spriteSheet.dispose();
    }
}