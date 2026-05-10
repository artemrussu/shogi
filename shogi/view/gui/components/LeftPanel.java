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
    private static final int SECTION_PAD   = 35;
    
    private static final int BACKGROUND_COL = 1;
    private static final int BACKGROUND_ROW = 0;
    private static final int EMPTY_SLOT_COL = 7;
    private static final int EMPTY_SLOT_ROW = 2;

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
        drawBackground(batch, spriteSheet);
        drawFrame(batch, spriteSheet);
        drawHand(batch, spriteSheet, Color.SENTE, getSenteStartY());
        drawHand(batch, spriteSheet, Color.GOTE,  getGoteStartY());
    }
    
    private void drawBackground(SpriteBatch batch, Texture sheet) {
        int tileSize = frameThickness;
        int innerX = x + frameThickness;
        int innerY = y + frameThickness;
        int innerW = width  - frameThickness * 2;
        int innerH = height - frameThickness * 2;

        for (int px = innerX; px < innerX + innerW; px += tileSize) {
            for (int py = innerY; py < innerY + innerH; py += tileSize) {
                SpriteUtil.drawSprite(batch, sheet,
                        BACKGROUND_COL, BACKGROUND_ROW,
                        px, py, tileSize, tileSize, 0f);
            }
        }
    }

    private void drawHand(SpriteBatch batch, Texture sheet, Color color, int startY) {
        int drawX = x + frameThickness + SECTION_PAD;
        int drawY = startY;

        for (PieceType type : HAND_ORDER) {
            int count = board.hand.getHand(color).getOrDefault(type, 0);

            SpriteUtil.drawSprite(batch, sheet, EMPTY_SLOT_COL, EMPTY_SLOT_ROW,
                                  drawX, drawY, PIECE_SIZE, PIECE_SIZE, 0f);

            if (count > 0) {
                float rotation = (color == Color.SENTE) ? 180f : 0f;
                SpriteUtil.drawSprite(batch, sheet,
                        SpriteUtil.getPieceCol(type), SpriteUtil.getPieceRow(type),
                        drawX, drawY, PIECE_SIZE, PIECE_SIZE, rotation);
            }

            if (font != null) {
                String countText = String.valueOf(count);
                int textX = drawX + PIECE_SIZE - font.getWidth(countText) - 4;
                int textY = drawY + PIECE_SIZE - 20;

                batch.setColor(0.424f, 0.161f, 0.251f, 1f);
                font.drawText(batch, countText, textX, textY);
                batch.setColor(1f, 1f, 1f, 1f);
            }

            drawX += PIECE_SIZE + PIECE_MARGIN;

            if (drawX + PIECE_SIZE > x + width - frameThickness - SECTION_PAD) {
                drawX  = x + frameThickness + SECTION_PAD;
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
        int drawX = x + frameThickness + SECTION_PAD;
        int drawY = startY;

        for (PieceType type : HAND_ORDER) {
            int count = board.hand.getHand(color).getOrDefault(type, 0);

            if (count > 0
             && mouseX >= drawX && mouseX <= drawX + PIECE_SIZE
             && mouseY >= drawY && mouseY <= drawY + PIECE_SIZE) {
                return type;
            }

            drawX += PIECE_SIZE + PIECE_MARGIN;

            if (drawX + PIECE_SIZE > x + width - frameThickness - SECTION_PAD) {
                drawX  = x + frameThickness + SECTION_PAD;
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
//    private int getSpriteCol(PieceType type) {
//        switch (type) {
//            case PAWN:   return 7;
//            case LANCE:  return 2;
//            case KNIGHT: return 3;
//            case SILVER: return 4;
//            case GOLD:   return 5;
//            case BISHOP: return 1;
//            case ROOK:   return 0;
//            default:     return 0;
//        }
//    }
//
//    private int getSpriteRow(PieceType type) {
//        switch (type) {
//            case ROOK:   return 1;
//            case BISHOP: return 1;
//            default:     return 0;
//        }
//    }
    
    public PieceType getHandPieceFromMouse(int mouseX, int mouseY, Color currentTurn) {
        return getClickedPiece(mouseX, mouseY, currentTurn,
               currentTurn == Color.SENTE ? getSenteStartY() : getGoteStartY());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY) { }
}