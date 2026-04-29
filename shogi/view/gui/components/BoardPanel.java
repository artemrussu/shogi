package view.gui.components;

import java.util.Set;
import core.Coordinates;
import core.File;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import model.Board;
import model.piece.Piece;
import view.gui.SpriteUtil;

public class BoardPanel extends UIComponent {

    private Board board;
    private Piece pieceToMove;
    private Set<Coordinates> availableMoves = Set.of();
    private final int TILE_SIZE = 75;

    public BoardPanel(int x, int y, int width, int height, Board board) {
    	// thickness = 75, Corner = (1, 2), Straight = (2, 2)
        super(x, y, width, height, 75, 1, 2, 2, 2);
        this.board = board;
    }

    public void setPieceToMove(Piece piece) {
        this.pieceToMove = piece;
    }

    // GraphicRender call this before render()
    public void setAvailableMoves(Set<Coordinates> moves) {
        this.availableMoves = moves;
    }

    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);

        for (int rank = 9; rank >= 1; rank--) {
            for (File file : File.values()) {
                Coordinates coords = new Coordinates(file, rank);
                int sx = this.x + frameThickness + (file.ordinal() * TILE_SIZE);
                int sy = this.y + frameThickness + ((9 - rank) * TILE_SIZE);

                // Draw Tile, Highlight, and Piece
                SpriteUtil.drawSprite(batch, spriteSheet, 0, 0, sx, sy, TILE_SIZE, TILE_SIZE, 0f);
                if (availableMoves.contains(coords))
                    SpriteUtil.drawSprite(batch, spriteSheet, 1, 0, sx, sy, TILE_SIZE, TILE_SIZE, 0f);
                if (!board.isSquareEmpty(coords))
                    SpriteUtil.drawPiece(batch, spriteSheet, board.getPiece(coords), sx, sy, TILE_SIZE);
            }
        }
    }

    public Coordinates getCoordinatesFromMouse(int mouseX, int mouseY) {
        int f = (mouseX - this.x - frameThickness) / TILE_SIZE;
        int r = (mouseY - this.y - frameThickness) / TILE_SIZE;
        return (f >= 0 && f < 9 && r >= 0 && r < 9) ? new Coordinates(File.values()[f], 9 - r) : null;
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) { }
}