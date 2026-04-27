package view.gui.components;

import java.util.Set;
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
    private final int TILE_SIZE = 75;

    public BoardPanel(int x, int y, int width, int height, Board board) {
        // We pass 75 here to lock the board's frame thickness
        super(x, y, width, height, 75);
        this.board = board;
    }

    public void setPieceToMove(Piece piece) { this.pieceToMove = piece; }

    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);

        Set<Coordinates> moves = (pieceToMove != null) ? pieceToMove.getAvailableMoveSquares(board) : Set.of();

        for (int rank = 9; rank >= 1; rank--) {
            for (File file : File.values()) {
                Coordinates coords = new Coordinates(file, rank);
                int sx = this.x + frameThickness + (file.ordinal() * TILE_SIZE);
                int sy = this.y + frameThickness + ((9 - rank) * TILE_SIZE);

                // Draw Tile, Highlight, and Piece
                SpriteUtil.drawSprite(batch, spriteSheet, 0, 0, sx, sy, TILE_SIZE, TILE_SIZE, 0f); 
                if (moves.contains(coords)) SpriteUtil.drawSprite(batch, spriteSheet, 1, 0, sx, sy, TILE_SIZE, TILE_SIZE, 0f); 
                if (!board.isSquareEmpty(coords)) SpriteUtil.drawPiece(batch, spriteSheet, board.getPiece(coords), sx, sy, TILE_SIZE);
            }
        }
    }

    public Coordinates getCoordinatesFromMouse(int mouseX, int mouseY) {
        int f = (mouseX - this.x - frameThickness) / TILE_SIZE;
        int r = (mouseY - this.y - frameThickness) / TILE_SIZE;
        return (f >= 0 && f < 9 && r >= 0 && r < 9) ? new Coordinates(File.values()[f], 9 - r) : null;
    }

    @Override public void handleMouseClick(int mouseX, int mouseY) { }
}