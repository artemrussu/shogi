package core;

/**
 * Represents the two opposing sides in the game.
 */
public enum Color {
	SENTE, GOTE;

	public Color opposite() {
        return this == SENTE ? GOTE : SENTE;
    }
}
