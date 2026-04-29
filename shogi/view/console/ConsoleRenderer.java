package view.console;

import java.util.Set;

import core.Color;
import core.Coordinates;
import core.File;
import model.Board;
import model.piece.Piece;

/**
 * Responsible for rendering the game board state to the system console.
 * Uses ANSI escape codes for colored output and square highlighting.
 */
public class ConsoleRenderer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE_PIECE_COLOR = "\u001B[97m";
    public static final String ANSI_BLACK_PIECE_COLOR = "\u001B[30m";
    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_BLACK_SQUARE_BACKGROUND = "\u001B[0;100m";
    public static final String ANSI_HIGHLIGHTED_SQUARE_BACKGROUND = "\u001B[45m";

    /**
     * Renders the board and highlights available moves for a specific piece.
     */
    public void render(Board board, Piece pieceToMove) {
        Set<Coordinates> availableMoveSquares = Set.of();
        if (pieceToMove != null) {
            availableMoveSquares = pieceToMove.getAvailableMoveSquares(board);
        }

        for (int rank = 9; rank >= 1; rank--) {
            String line = " ";
            for (File file : File.values()) {
                Coordinates coordinates = new Coordinates(file, rank);
                boolean isHighlight = availableMoveSquares.contains(coordinates);

                if (board.isSquareEmpty(coordinates)) {
                    line += getSpriteForEmptySquare(coordinates, isHighlight);
                } else {
                    line += getPieceSprite(board.getPiece(coordinates), isHighlight);
                }
            }

            line += ANSI_RESET;
            System.out.println(line);
        }
    }

    /**
     * Renders the current state of the board without any highlighting.
     */
    public void render(Board board) {
        render(board, null);
    }

    /**
     * Returns a string representing a piece with background and font colors.
     */
    private String getPieceSprite(Piece piece, boolean isHighlight) {
        return colorizeSprite(" " + selectSpriteForPiece(piece) + " ", 
                piece.getColor(),
                Board.isSquareDark(piece.getCoordinates()), 
                isHighlight);
    }

    /**
     * Maps a piece class to its corresponding single-character representation.
     */
    private String selectSpriteForPiece(Piece piece) {
        switch (piece.getClass().getSimpleName()) {
            case "King": return "K";
            case "Pawn": return "P";
            case "Lance": return "L";
            case "Knight": return "N";
            case "Silver": return "S";
            case "Gold": return "G";
            case "Bishop": return "B";
            case "Rook": return "R";
            case "Horse": return "H";
            default: return piece.getClass().getSimpleName();
        }
    }

    /**
     * Wraps the sprite string with ANSI codes for background and foreground colors.
     */
    private String colorizeSprite(String sprite, Color pieceColor, boolean isSquareDark, boolean isHighlight) {
        String result = sprite;

        if (pieceColor == Color.SENTE) {
            result = ANSI_WHITE_PIECE_COLOR + result;
        } else {
            result = ANSI_BLACK_PIECE_COLOR + result;
        }

        if (isHighlight) {
            result = ANSI_HIGHLIGHTED_SQUARE_BACKGROUND + result;
        } else if (isSquareDark) {
            result = ANSI_BLACK_SQUARE_BACKGROUND + result;
        } else {
            result = ANSI_WHITE_SQUARE_BACKGROUND + result;
        }

        return result;
    }

    /**
     * Returns a colored empty space representing a board square.
     */
    private String getSpriteForEmptySquare(Coordinates coordinates, boolean isHighlight) {
        return colorizeSprite("   ", Color.SENTE, Board.isSquareDark(coordinates), isHighlight);
    }
}