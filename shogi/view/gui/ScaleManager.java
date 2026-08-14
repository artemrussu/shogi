package view.gui;

import org.lwjgl.opengl.Display;

public class ScaleManager {

    private static final int REF_WIDTH  = 1920;
    private static final int REF_HEIGHT = 1080;

    private final float scale;

    public ScaleManager() {
        float scaleW = (float) Display.getWidth()  / REF_WIDTH;
        float scaleH = (float) Display.getHeight() / REF_HEIGHT;
        this.scale = Math.min(scaleW, scaleH); // fit without stretching
    }

    public int s(int baseValue) {
        return Math.round(baseValue * scale);
    }

    public float getScale() { return scale; }
}