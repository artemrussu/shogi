package menu;

import core.Color;

public class GameConfig {

    public enum Mode { VS_HUMAN, VS_AI }

    public final Mode  mode;
    public final Color playerColor;

    public GameConfig(Mode mode, Color playerColor) {
        this.mode        = mode;
        this.playerColor = playerColor;
    }
}