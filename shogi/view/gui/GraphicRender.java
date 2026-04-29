package view.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import core.Coordinates;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.*;
import mdesl.graphics.SpriteBatch;
import model.Board;
import model.piece.Piece;
import view.gui.components.BoardPanel;
import view.gui.components.LeftPanel;
import view.gui.components.RightPanel;

public class GraphicRender {

    private SpriteBatch batch;
    
    // NEW: The renderer now holds a reference to the loaded assets
    private AssetManager assets; 
    
    // UI components
    private BoardPanel boardPanel;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;

    /**
     * Initializes the display and UI components.
     * @param board The game board reference.
     * @param assets The loaded assets manager.
     */
    public void init(Board board, AssetManager assets) {
        // Store the assets so the render method can use them
        this.assets = assets; 

        try {
            // 1. Setup Display
            DisplayMode desktopMode = Display.getDesktopDisplayMode();
            Display.setDisplayMode(desktopMode);
            Display.setFullscreen(true);
            Display.setTitle("Shogi Game");
            Display.setVSyncEnabled(true);
            Display.create();
            
            // 2. OpenGL Setup
            glDisable(GL_DEPTH_TEST); 
            glEnable(GL_BLEND);       
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); 
            glClearColor(0.2f, 0.3f, 0.2f, 1f); 

            batch = new SpriteBatch();

            // 3. Initialize UI Components
            int screenW = Display.getWidth();
            int screenH = Display.getHeight();
            int tileSize = 75;
            int boardInnerSize = 9 * tileSize;
            int frameSize = 75;
            int boardPanelSize = boardInnerSize + (frameSize * 2); 

            int boardX = (screenW - boardPanelSize) / 2;
            int boardY = (screenH - boardPanelSize) / 2;
            
            int sidePanelWidth = 500;
            int sidePanelHeight = 950;
            int panelY = (screenH - sidePanelHeight) / 2;
            
            boardPanel = new BoardPanel(boardX, boardY, boardPanelSize, boardPanelSize, board);
            leftPanel = new LeftPanel(30, panelY, sidePanelWidth, sidePanelHeight);
            rightPanel = new RightPanel(screenW - sidePanelWidth - 30, panelY, sidePanelWidth, sidePanelHeight);

        // Notice we removed the IOException catch, because file loading is no longer done here!
        } catch (LWJGLException e) {
            System.err.println("Critical error during initialization:");
            e.printStackTrace();
        }
    }
    
    /**
     * Translates mouse position to board coordinates.
     * @return The board coordinates or null if clicked outside the board panel.
     */
    public Coordinates getCoordinatesFromMouse() {
        if (boardPanel == null) return null;

        int mouseX = Mouse.getX();
        int mouseY = Display.getHeight() - Mouse.getY(); 

        // Delegate calculation to the board panel
        return boardPanel.getCoordinatesFromMouse(mouseX, mouseY);
    }    
    
    /**
     * Processes system events.
     */
    public void processEvents() {
        Display.processMessages();
    }    

    /**
     * Renders the game interface components.
     * @param board The game board reference.
     * @param pieceToMove The piece currently being selected/moved.
     */
    public void render(Board board, Piece pieceToMove) {
        // We now check assets.spriteSheet instead of a local spriteSheet variable
        if (assets == null || assets.spriteSheet == null || batch == null) return;

        glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();

        drawBackground();

        // 1. Draw side panels (Notice we pass assets.spriteSheet)
        if (leftPanel != null) leftPanel.render(batch, assets.spriteSheet);
        if (rightPanel != null) rightPanel.render(batch, assets.spriteSheet);

        // 2. Draw central board
        if (boardPanel != null) {
            boardPanel.setPieceToMove(pieceToMove);
            boardPanel.render(batch, assets.spriteSheet);
        }

        // 3. Draw Custom Font using the AssetManager
        if (assets.gameFont != null) {
            String message = "SHOGI";
            int textWidth = assets.gameFont.getWidth(message);
         
            batch.setColor(1f, 1f, 0f, 1f); 
            assets.gameFont.drawText(batch, message, (Display.getWidth() - textWidth) / 2, 50);
            batch.setColor(1f, 1f, 1f, 1f);
        }

        batch.end();
        Display.update(); 
    }

    /**
     * Draws the background sprite from the spritesheet scaled to the display size.
     */
    private void drawBackground() {
        int backgroundCol = 3;
        int backgroundRow = 2;

        int screenWidth = Display.getWidth();
        int screenHeight = Display.getHeight();
        int tileDrawSize = 256; 

        for (int x = 0; x < screenWidth; x += tileDrawSize) {
            for (int y = 0; y < screenHeight; y += tileDrawSize) {
                // Use assets.spriteSheet here!
                SpriteUtil.drawSprite(batch, assets.spriteSheet, 
                                      backgroundCol, backgroundRow, 
                                      x, y, 
                                      tileDrawSize, tileDrawSize, 
                                      0f);
            }
        }
    }
}