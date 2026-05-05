package view.gui;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

import java.util.Set;

import mdesl.graphics.SpriteBatch;
import core.Color;
import core.Coordinates;
import core.PieceType;
import model.Board;
import model.piece.Piece;
import view.gui.components.BoardPanel;
import view.gui.components.LeftPanel;
import view.gui.components.PromotionDialog;
import view.gui.components.RightPanel;

public class GraphicRender {

    // --- layout constants ---
    private static final int TILE_SIZE         = 75;
    private static final int FRAME_SIZE        = 75;
    private static final int BOARD_PANEL_SIZE  = 9 * TILE_SIZE + FRAME_SIZE * 2;
    private static final int SIDE_PANEL_W      = 500;
    private static final int SIDE_PANEL_H      = 950;
    private static final int SIDE_PANEL_MARGIN = 30;

    // --- background ---
    private static final int BG_COL       = 3;
    private static final int BG_ROW       = 2;
    private static final int BG_TILE_SIZE = 256;

    // --- state ---
    private SpriteBatch batch;
    private AssetManager assets;

    // --- components ---
    private BoardPanel      boardPanel;
    private LeftPanel       leftPanel;
    private RightPanel      rightPanel;
    private PromotionDialog promotionDialog = new PromotionDialog();

    // -------------------------------------------------------------------------

    public void init(Board board, AssetManager assets) {
        this.assets = assets;

        try {
            initDisplay();
            initOpenGL();
            initComponents(board);
        } catch (LWJGLException e) {
            System.err.println("Critical error during initialization:");
            e.printStackTrace();
        }
    }

    private void initDisplay() throws LWJGLException {
        Display.setDisplayMode(Display.getDesktopDisplayMode());
        Display.setFullscreen(true);
        Display.setTitle("Shogi Game");
        Display.setVSyncEnabled(true);
        Display.create();
    }

    private void initOpenGL() throws LWJGLException {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.2f, 0.3f, 0.2f, 1f);
        batch = new SpriteBatch();
    }

    private void initComponents(Board board) {
        int screenW = Display.getWidth();
        int screenH = Display.getHeight();

        int boardX = (screenW - BOARD_PANEL_SIZE) / 2;
        int boardY = (screenH - BOARD_PANEL_SIZE) / 2;
        int panelY = (screenH - SIDE_PANEL_H)     / 2;
        int rightX = screenW - SIDE_PANEL_W - SIDE_PANEL_MARGIN;

        boardPanel = new BoardPanel(boardX, boardY, BOARD_PANEL_SIZE, BOARD_PANEL_SIZE, board);
        leftPanel  = new LeftPanel(SIDE_PANEL_MARGIN, panelY, SIDE_PANEL_W, SIDE_PANEL_H,
                                   board, assets.gameFont);  // передаём board и font
        rightPanel = new RightPanel(rightX, panelY, SIDE_PANEL_W, SIDE_PANEL_H);
    }

    // -------------------------------------------------------------------------

    public void render(Board board, Piece selectedPiece, Set<Coordinates> availableMoves) {
        if (assets == null || assets.spriteSheet == null || batch == null) return;

        glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();

        drawBackground();
        drawPanels(selectedPiece, availableMoves);
        drawTitle();
        promotionDialog.render(batch, assets.spriteSheet, assets.gameFont);

        batch.end();
        Display.update();
    }

    private void drawBackground() {
        int screenW = Display.getWidth();
        int screenH = Display.getHeight();

        for (int x = 0; x < screenW; x += BG_TILE_SIZE) {
            for (int y = 0; y < screenH; y += BG_TILE_SIZE) {
                SpriteUtil.drawSprite(batch, assets.spriteSheet,
                        BG_COL, BG_ROW, x, y, BG_TILE_SIZE, BG_TILE_SIZE, 0f);
            }
        }
    }

    private void drawPanels(Piece selectedPiece, Set<Coordinates> availableMoves) {
        if (leftPanel  != null) leftPanel.render(batch, assets.spriteSheet);
        if (rightPanel != null) rightPanel.render(batch, assets.spriteSheet);

        if (boardPanel != null) {
            boardPanel.setPieceToMove(selectedPiece);
            boardPanel.setAvailableMoves(availableMoves);
            boardPanel.render(batch, assets.spriteSheet);
        }
    }

    private void drawTitle() {
        if (assets.gameFont == null) return;

        String message  = "SHOGI";
        int textWidth   = assets.gameFont.getWidth(message);

        batch.setColor(1f, 1f, 0f, 1f);
        assets.gameFont.drawText(batch, message, (Display.getWidth() - textWidth) / 2, 50);
        batch.setColor(1f, 1f, 1f, 1f);
    }

    // -------------------------------------------------------------------------

    public void showPromotionDialog()        { promotionDialog.show(); }
    public void hidePromotionDialog()        { promotionDialog.hide(); }
    public Boolean getPromotionDialogChoice(int mouseX, int mouseY) {
        return promotionDialog.pollChoice(mouseX, mouseY);
    }

    // -------------------------------------------------------------------------

    public Coordinates getCoordinatesFromMouse() {
        if (boardPanel == null) return null;
        int mouseX = Mouse.getX();
        int mouseY = Display.getHeight() - Mouse.getY();
        return boardPanel.getCoordinatesFromMouse(mouseX, mouseY);
    }

    public PieceType getHandPieceFromMouse(Color color) {
        if (leftPanel == null) return null;
        int mouseX = Mouse.getX();
        int mouseY = Display.getHeight() - Mouse.getY();
        return leftPanel.getHandPieceFromMouse(mouseX, mouseY, color);
    }

    public void processEvents() {
        Display.processMessages();
    }
}