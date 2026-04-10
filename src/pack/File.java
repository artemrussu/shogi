package pack;

/**
 * Vertical columns of the board from A to I.
 */
public enum File {
    A, B, C, D, E, F, G, H, I;

    /**
     * Returns the File matching the character, or null if not found.
     */
    public static File fromChar(char c) {
        try {
            return File.valueOf(String.valueOf(c).toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}