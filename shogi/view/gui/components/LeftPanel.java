package view.gui.components;

import java.util.ArrayList;
import java.util.List;

import core.Color;
import core.PieceType;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import mdesl.graphics.text.BitmapFont;
import model.Board;
import view.gui.SpriteUtil;

public class LeftPanel extends UIComponent {

    private static final int PIECE_SIZE    = 75;
    private static final int PIECE_MARGIN  = 10;
    private static final int SECTION_PAD   = 40;

    private static final PieceType[] HAND_ORDER = {
        PieceType.ROOK, PieceType.BISHOP,
        PieceType.GOLD, PieceType.SILVER,
        PieceType.KNIGHT, PieceType.LANCE, PieceType.PAWN
    };

    private final Board board;
    private BitmapFont font;

    public LeftPanel(int x, int y, int width, int height, Board board, BitmapFont font) {
        super(x, y, width, height, 50, 4, 2, 5, 2);
        this.board = board;
        this.font  = font;
    }

    @Override
    public void render(SpriteBatch batch, Texture spriteSheet) {
        drawFrame(batch, spriteSheet);
        drawHand(batch, spriteSheet, Color.SENTE, getSenteStartY());
        drawHand(batch, spriteSheet, Color.GOTE,  getGoteStartY());
    }

    private void drawHand(SpriteBatch batch, Texture sheet, Color color, int startY) {
        List<PieceType> pieces = getPiecesInHand(color);
        int drawX = x + frameThickness + SECTION_PAD;
        int drawY = startY;

        for (PieceType type : pieces) {
            int count = board.hand.getHand(color).getOrDefault(type, 0);
            if (count == 0) continue;

            float rotation = (color == Color.SENTE) ? 180f : 0f;
            SpriteUtil.drawSprite(batch, sheet,
                    getSpriteCol(type), getSpriteRow(type),
                    drawX, drawY, PIECE_SIZE, PIECE_SIZE, rotation);

            if (font != null && count > 1) {
                font.drawText(batch, "x" + count, drawX + PIECE_SIZE - 20, drawY + PIECE_SIZE - 20);
            }

            drawX += PIECE_SIZE + PIECE_MARGIN;

            if (drawX + PIECE_SIZE > x + width - frameThickness - SECTION_PAD) {
                drawX = x + frameThickness + SECTION_PAD;
                drawY += PIECE_SIZE + PIECE_MARGIN;
            }
        }
    }

    public PieceType getHandPieceFromMouse(int mouseX, int mouseY) {
        PieceType result = getClickedPiece(mouseX, mouseY, Color.SENTE, getSenteStartY());
        if (result != null) return result;
        return getClickedPiece(mouseX, mouseY, Color.GOTE, getGoteStartY());
    }

    private PieceType getClickedPiece(int mouseX, int mouseY, Color color, int startY) {
        List<PieceType> pieces = getPiecesInHand(color);
        int drawX = x + frameThickness + SECTION_PAD;
        int drawY = startY;

        for (PieceType type : pieces) {
            int count = board.hand.getHand(color).getOrDefault(type, 0);
            if (count == 0) continue;

            if (mouseX >= drawX && mouseX <= drawX + PIECE_SIZE
             && mouseY >= drawY && mouseY <= drawY + PIECE_SIZE) {
                return type;
            }

            drawX += PIECE_SIZE + PIECE_MARGIN;

            if (drawX + PIECE_SIZE > x + width - frameThickness - SECTION_PAD) {
                drawX = x + frameThickness + SECTION_PAD;
                drawY += PIECE_SIZE + PIECE_MARGIN;
            }
        }

        return null;
    }

    private List<PieceType> getPiecesInHand(Color color) {
        List<PieceType> result = new ArrayList<>();
        for (PieceType type : HAND_ORDER) {
            if (board.hand.getHand(color).getOrDefault(type, 0) > 0) {
                result.add(type);
            }
        }
        return result;
    }

    private int getSenteStartY() {
        return y + frameThickness + SECTION_PAD;
    }

    private int getGoteStartY() {
        return y + height / 2 + SECTION_PAD;
    }

    // from SpriteUtil
    private int getSpriteCol(PieceType type) {
        switch (type) {
            case PAWN:   return 7;
            case LANCE:  return 2;
            case KNIGHT: return 3;
            case SILVER: return 4;
            case GOLD:   return 5;
            case BISHOP: return 1;
            case ROOK:   return 0;
            default:     return 0;
        }
    }

    private int getSpriteRow(PieceType type) {
        switch (type) {
            case ROOK:   return 1;
            case BISHOP: return 1;
            default:     return 0;
        }
    }
    
    public PieceType getHandPieceFromMouse(int mouseX, int mouseY, Color currentTurn) {
        return getClickedPiece(mouseX, mouseY, currentTurn,
               currentTurn == Color.SENTE ? getSenteStartY() : getGoteStartY());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) { }
}