package view.gui.components;

import java.util.Set;
import core.Color;
import core.Coordinates;
import core.File;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import model.board.Board;
import model.piece.Piece;
import view.gui.SpriteUtil;

public class BoardPanel extends UIComponent {
    private Board board;
    private Piece pieceToMove;
    
    private final int DISPLAY_TILE_SIZE = 75;
    private final int FRAME_THICKNESS = 70;

    public BoardPanel(int x, int y, int width, int height, Board board) {
        super(x, y, width, height);
        this.board = board;
    }

    public void setPieceToMove(Piece piece) {
        this.pieceToMove = piece;
    }

    @Override
    protected void drawFrame(SpriteBatch batch, Texture sheet) {
        // Explicitly define the frame tile coordinates from your sheet
        int cornerCol = 1; int cornerRow = 2;
        int straightCol = 2; int straightRow = 2;
        int T = FRAME_THICKNESS;

        // 1. Draw 4 Corners
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x, y, T, T, 0f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x + width - T, y, T, T, 90f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x + width - T, y + height - T, T, T, 180f);
        SpriteUtil.drawSprite(batch, sheet, cornerCol, cornerRow, x, y + height - T, T, T, 270f);

        // 2. Horizontal Edges - Explicitly use the frame sheet coordinates
        for (int i = T; i < width - T; i += T) {
            int drawW = Math.min(T, width - T - i);
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x + i, y, drawW, T, 0f);
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x + i, y + height - T, drawW, T, 180f);
        }

        // 3. Vertical Edges - Explicitly use the frame sheet coordinates
        for (int j = T; j < height - T; j += T) {
            int drawH = Math.min(T, height - T - j);
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x, y + j, T, drawH, 270f);
            SpriteUtil.drawSprite(batch, sheet, straightCol, straightRow, x + width - T, y + j, T, drawH, 90f);
        }
    }
    public Coordinates getCoordinatesFromMouse(int mouseX, int mouseY) {
        // Offset by FRAME_THICKNESS to skip the border
        int fileIdx = (mouseX - this.x - FRAME_THICKNESS) / DISPLAY_TILE_SIZE;
        int rankIdx = (mouseY - this.y - FRAME_THICKNESS) / DISPLAY_TILE_SIZE;

        if (fileIdx >= 0 && fileIdx < 9 && rankIdx >= 0 && rankIdx < 9) {
            return new Coordinates(File.values()[fileIdx], 9 - rankIdx);
        }
        return null;
    }

    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);

        Set<Coordinates> availableMoveSquares = (pieceToMove != null) 
            ? pieceToMove.getAvailableMoveSquares(board) : Set.of();

        for (int rank = 9; rank >= 1; rank--) {
            for (File file : File.values()) {
                Coordinates coordinates = new Coordinates(file, rank);
                
                // Offset the drawing area by FRAME_THICKNESS
                int screenX = this.x + FRAME_THICKNESS + (file.ordinal() * DISPLAY_TILE_SIZE);
                int screenY = this.y + FRAME_THICKNESS + ((9 - rank) * DISPLAY_TILE_SIZE);

                SpriteUtil.drawSprite(batch, spriteSheet, 0, 0, screenX, screenY, DISPLAY_TILE_SIZE, DISPLAY_TILE_SIZE, 0f); 

                if (availableMoveSquares.contains(coordinates)) {
                    SpriteUtil.drawSprite(batch, spriteSheet, 1, 0, screenX, screenY, DISPLAY_TILE_SIZE, DISPLAY_TILE_SIZE, 0f); 
                }

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
        
        // Use the static helper class instead of the local method
        SpriteUtil.drawSprite(batch, spriteSheet, col, row, 
                                screenX, screenY, DISPLAY_TILE_SIZE, DISPLAY_TILE_SIZE, rotation);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) { }
}