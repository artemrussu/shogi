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
            
            // UI LAYOUT CALCULATIONS
            int screenW = Display.getWidth();
            int screenH = Display.getHeight();
            
            int boardSize = 9 * 75; // 675 pixels (board width/height)
            
            // Center the board on the screen
            int boardX = (screenW - boardSize) / 2; 
            int boardY = (screenH - boardSize) / 2; 
            
            int sidePanelWidth = 400;
            int sidePanelHeight = screenH - 200;
            int panelY = 100;
            
            // Instantiate UI panels
            boardPanel = new BoardPanel(boardX, boardY, boardSize, boardSize, board);
            leftPanel = new LeftPanel(50, panelY, sidePanelWidth, sidePanelHeight);
            rightPanel = new RightPanel(screenW - sidePanelWidth - 50, panelY, sidePanelWidth, sidePanelHeight);
            
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
}