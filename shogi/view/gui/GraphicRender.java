package view.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import core.Coordinates;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import model.board.Board;
import model.piece.Piece;
import view.gui.components.BoardPanel;
import view.gui.components.LeftPanel;
import view.gui.components.RightPanel;

public class GraphicRender {

    private SpriteBatch batch;
    private Texture spriteSheet;
    
    // UI components
    private BoardPanel boardPanel;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;

    /**
     * Initializes the display and UI components.
     * @param board The game board reference.
     */
    public void init(Board board) {
        try {
            Display.setDisplayMode(new DisplayMode(1920, 1080));
            Display.setTitle("Shogi Game");
            Display.setVSyncEnabled(true);
            Display.create();

            glDisable(GL_DEPTH_TEST); 
            glEnable(GL_BLEND);       
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); 
            glClearColor(0.2f, 0.3f, 0.2f, 1f); 

            batch = new SpriteBatch();
            
            java.net.URL textureUrl = getClass().getClassLoader().getResource("shogi.png");
            if (textureUrl == null) {
                System.err.println("ERROR: File shogi.png not found!");
                return;
            }
            spriteSheet = new Texture(textureUrl); 
            
            int screenW = Display.getWidth();
            int screenH = Display.getHeight();
            
            int tileSize = 75;
            int boardInnerSize = 9 * tileSize; // 675
            int frameSize = 75;

            // 675 + 150 = 825
            int boardPanelSize = boardInnerSize + (frameSize * 2); 

            int boardX = (screenW - boardPanelSize) / 2;
            int boardY = (screenH - boardPanelSize) / 2;
            
            int sidePanelWidth = 500;
            int sidePanelHeight = 950;
            int panelY = 65;
            
            // Instantiate components using the clean variables
            boardPanel = new BoardPanel(boardX, boardY, boardPanelSize, boardPanelSize, board);
            leftPanel = new LeftPanel(30, panelY, sidePanelWidth, sidePanelHeight);
            rightPanel = new RightPanel(screenW - sidePanelWidth - 30, panelY, sidePanelWidth, sidePanelHeight);
            
        } catch (LWJGLException e) {
            e.printStackTrace();
        } catch (Exception e) {
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
        if (spriteSheet == null || batch == null) return;

        glClear(GL_COLOR_BUFFER_BIT);
        batch.begin();

        drawBackground();

        // 1. Draw side panels
        if (leftPanel != null) leftPanel.render(batch, spriteSheet);
        if (rightPanel != null) rightPanel.render(batch, spriteSheet);

        // 2. Draw central board
        if (boardPanel != null) {
            boardPanel.setPieceToMove(pieceToMove);
            boardPanel.render(batch, spriteSheet);
        }

        batch.end();
        Display.update(); 
    }

    /**
     * Draws the background sprite from the spritesheet scaled to the display size.
     */
    private void drawBackground() {
        // The column and row of your background tile in the spritesheet
        int backgroundCol = 3;
        int backgroundRow = 2;

        int screenWidth = Display.getWidth();
        int screenHeight = Display.getHeight();
        
        // Define the size we want to draw each background tile (256x256)
        int tileDrawSize = 256; 

        // Loop through the screen width and height to tile the image
        for (int x = 0; x < screenWidth; x += tileDrawSize) {
            for (int y = 0; y < screenHeight; y += tileDrawSize) {
                
                // Draw the tile at the current position
                SpriteUtil.drawSprite(batch, spriteSheet, 
                                      backgroundCol, backgroundRow, 
                                      x, y, 
                                      tileDrawSize, tileDrawSize, 
                                      0f);
            }
        }
    }
}