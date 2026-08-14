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
	private static final int BASE_TILE_SIZE        = 75;
	private static final int BASE_FRAME_SIZE       = 75;
	private static final int BASE_SIDE_PANEL_W     = 500;
	private static final int BASE_SIDE_PANEL_H     = 950;
	private static final int BASE_SIDE_PANEL_MARGIN = 30;
	private static final int BASE_BG_TILE_SIZE     = 256;

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
    
    // --- color (red) ---
    private static final float TEXT_R = 0.424f;
    private static final float TEXT_G = 0.161f;
    private static final float TEXT_B = 0.251f;
    
    private final ScreenMessage screenMessage = new ScreenMessage();
    
    private ScaleManager scale;

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
        if (Display.isCreated()) return;
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
        scale = new ScaleManager();

        int tileSize        = scale.s(BASE_TILE_SIZE);
        int frameSize       = scale.s(BASE_FRAME_SIZE);
        int boardPanelSize  = 9 * tileSize + frameSize * 2;
        int sidePanelW      = scale.s(BASE_SIDE_PANEL_W);
        int sidePanelH      = scale.s(BASE_SIDE_PANEL_H);
        int sidePanelMargin = scale.s(BASE_SIDE_PANEL_MARGIN);

        int screenW = Display.getWidth();
        int screenH = Display.getHeight();

        int boardX = (screenW - boardPanelSize) / 2;
        int boardY = (screenH - boardPanelSize) / 2;
        int panelY = (screenH - sidePanelH)     / 2;
        int rightX =  screenW - sidePanelW - sidePanelMargin;

        boardPanel = new BoardPanel(boardX, boardY, boardPanelSize, boardPanelSize, board, tileSize, frameSize);
        leftPanel  = new LeftPanel(sidePanelMargin, panelY, sidePanelW, sidePanelH, board, assets, scale);
        rightPanel = new RightPanel(rightX, panelY, sidePanelW, sidePanelH);
    }

    // -------------------------------------------------------------------------

    public void render(Board board, Piece selectedPiece, 
    						Set<Coordinates> availableMoves, Color currentTurn) {
        if (assets == null || assets.spriteSheet == null || batch == null) return;

        glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();

        drawBackground();
        drawPanels(selectedPiece, availableMoves);
        drawTitle(currentTurn);
        promotionDialog.render(batch, assets.spriteSheet, assets.gameFont);
        drawMessage();

        batch.end();
        Display.update();
    }

    private void drawBackground() {
        int tileSize = scale.s(BASE_BG_TILE_SIZE);
        for (int x = 0; x < Display.getWidth();  x += tileSize)
            for (int y = 0; y < Display.getHeight(); y += tileSize)
                SpriteUtil.drawSprite(batch, assets.spriteSheet,
                        BG_COL, BG_ROW, x, y, tileSize, tileSize, 0f);
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

    private void drawTitle(Color currentTurn) {
        if (assets.gameFont == null) return;

        String message = (currentTurn == Color.SENTE) ? "SENTE" : "GOTE";
        int textWidth  = assets.gameFont.getWidth(message);

        batch.setColor(TEXT_R, TEXT_G, TEXT_B, 1f);
        assets.gameFont.drawText(batch, message, (Display.getWidth() - textWidth) / 2, 50);
        batch.setColor(1f, 1f, 1f, 1f);
    }
    
    private void drawMessage() {
        if (!screenMessage.isVisible() || assets.gameFont == null) return;

        String text   = screenMessage.getText();
        int textWidth = assets.gameFont.getWidth(text);
        int textX     = (Display.getWidth()  - textWidth) / 2;
        int textY     = (Display.getHeight() - 40)        / 2;

        batch.setColor(TEXT_R, TEXT_G, TEXT_B, 1f);
        assets.gameFont.drawText(batch, text, textX, textY);
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
    
    public void setPerspective(BoardPerspective perspective) {
        if (boardPanel != null) boardPanel.setPerspective(perspective);
        if (leftPanel  != null) leftPanel.setPerspective(perspective);
    }


    // -------------------------------------------------------------------------
    
	public void showMessage(String text)          { screenMessage.show(text); }
	public void showPermanentMessage(String text) { screenMessage.showPermanent(text); }
	public void clearMessage()                    { screenMessage.clear(); }

}
