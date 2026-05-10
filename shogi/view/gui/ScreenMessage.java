package view.gui;

public class ScreenMessage {

    private static final long DEFAULT_DURATION_MS = 2500;

    private String text     = null;
    private long   hideAt   = 0;

    public void show(String text) {
        show(text, DEFAULT_DURATION_MS);
    }

    public void show(String text, long durationMs) {
        this.text   = text;
        this.hideAt = System.currentTimeMillis() + durationMs;
    }

    // constant message (game over)
    public void showPermanent(String text) {
        this.text   = text;
        this.hideAt = Long.MAX_VALUE;
    }

    public void clear() {
        this.text = null;
    }

    public boolean isVisible() {
        if (text == null) return false;
        if (System.currentTimeMillis() > hideAt) {
            text = null;
            return false;
        }
        return true;
    }

    public String getText() {
        return text;
    }
}