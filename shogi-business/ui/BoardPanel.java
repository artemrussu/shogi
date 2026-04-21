package ui;

import java.util.Set;
import board.Board;
import pack.Color;
import pack.Coordinates;
import pack.File;
import piece.Piece;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;

public class BoardPanel extends UIComponent {
    private Board board;
    private Piece pieceToMove;
    
    private final int TEXTURE_TILE_SIZE = 256; 
    private final int DISPLAY_TILE_SIZE = 75;

    public BoardPanel(int x, int y, int width, int height, Board board) {
        super(x, y, width, height);
        this.board = board;
    }

    public void setPieceToMove(Piece piece) {
        this.pieceToMove = piece;
    }

    /**
     * Converts raw mouse coordinates to board logic coordinates (file, rank).
     * * @param mouseX Absolute X coordinate on the screen.
     * @param mouseY Absolute Y coordinate on the screen.
     * @return Coordinates on the board, or null if clicked outside.
     */
    public Coordinates getCoordinatesFromMouse(int mouseX, int mouseY) {
        int fileIdx = (mouseX - this.x) / DISPLAY_TILE_SIZE;
        int rankIdx = (mouseY - this.y) / DISPLAY_TILE_SIZE;

        if (fileIdx >= 0 && fileIdx < 9 && rankIdx >= 0 && rankIdx < 9) {
            File file = File.values()[fileIdx];
            int rank = 9 - rankIdx; 
            return new Coordinates(file, rank);
        }
        return null;
    }

    /**
     * Renders the game board, highlights, and pieces.
     */
    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);

        Set<Coordinates> availableMoveSquares = (pieceToMove != null) 
            ? pieceToMove.getAvailableMoveSquares(board) 
            : Set.of();

        for (int rank = 9; rank >= 1; rank--) {
            for (File file : File.values()) {
                Coordinates coordinates = new Coordinates(file, rank);
                boolean isHighlight = availableMoveSquares.contains(coordinates);

                // Use panel-relative coordinates instead of global offsets
                int screenX = this.x + (file.ordinal() * DISPLAY_TILE_SIZE);
                int screenY = this.y + ((9 - rank) * DISPLAY_TILE_SIZE);

                // Draw background cell
                drawSprite(batch, spriteSheet, 0, 0, screenX, screenY, 0f); 

                // Draw move highlight if applicable
                if (isHighlight) {
                    drawSprite(batch, spriteSheet, 1, 0, screenX, screenY, 0f); 
                }

                // Draw piece
                if (!board.isSquareEmpty(coordinates)) {
                    drawPiece(batch, spriteSheet, board.getPiece(coordinates), screenX, screenY);
                }
            }
        }
    }

    /**
     * Maps piece type to sprite sheet coordinates and handles orientation.
     */
    private void drawPiece(SpriteBatch batch, Texture spriteSheet, Piece piece, int screenX, int screenY) {
        int col = 0; 
        int row = 0; 

        switch (piece.getClass().getSimpleName()) {
            case "Lance": col = 2; break;
            case "Knight": col = 3; break;
            case "Silver": col = 4; break;
            case "Gold": col = 5; break;
            case "King": col = 6; break; 
            case "Rook": col = 0; row = 1; break;
            case "Bishop": col = 1; row = 1; break;
            case "Pawn": col = 7; break;
        }

        float rotation = (piece.getColor() == Color.SENTE) ? 180f : 0f;
        drawSprite(batch, spriteSheet, col, row, screenX, screenY, rotation);
    }

    /**
     * Draws a specific sprite from the sheet using provided transformations.
     */
    private void drawSprite(SpriteBatch batch, Texture spriteSheet, int col, int row, int screenX, int screenY, float rotationDegrees) {
        float srcX = col * TEXTURE_TILE_SIZE;
        float srcY = row * TEXTURE_TILE_SIZE;
        float srcWidth = TEXTURE_TILE_SIZE;
        float srcHeight = TEXTURE_TILE_SIZE;

        float u = srcX / spriteSheet.getWidth();
        float v = srcY / spriteSheet.getHeight();
        float u2 = (srcX + srcWidth) / spriteSheet.getWidth();
        float v2 = (srcY + srcHeight) / spriteSheet.getHeight();

        float rotationRadians = (float) Math.toRadians(rotationDegrees);

        float originX = DISPLAY_TILE_SIZE / 2f;
        float originY = DISPLAY_TILE_SIZE / 2f;

        batch.draw(spriteSheet, 
                   (float)screenX, (float)screenY, 
                   (float)DISPLAY_TILE_SIZE, (float)DISPLAY_TILE_SIZE, 
                   originX, originY, 
                   rotationRadians, 
                   u, v, u2, v2);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) { }
}