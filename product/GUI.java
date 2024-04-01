package sprint3.product;

import sprint3.product.Board.Cell;
import sprint3.product.Board.GameState;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.Cursor;

@SuppressWarnings("serial")
public class GUI extends JFrame {

    private SimpleGame simpleGame; 
    private GeneralGame generalGame; 
    protected Board board; 
    protected int boardSize;
    
    public static final int SYMBOL_STROKE_WIDTH = 8; 
    public static final int CANVAS_SIZE = 300;
    public int cellSize;
    public int cellPadding;
    public int symbolSize;
    public char currentPlayerTurn;
    Cell currentPlayerSymbol;

    protected GameBoardCanvas gameBoardCanvas; 
    protected JTextField textFieldCurrentTurn;
	
    protected JRadioButton buttonBlueS;
    protected JRadioButton buttonBlueO;
    protected ButtonGroup blueSelectionGroup; 

    protected JRadioButton buttonRedS;
    protected JRadioButton buttonRedO;
    protected ButtonGroup redSelectionGroup;  
    protected ButtonGroup gameModeSelectionGroup; 
    protected JSpinner spinnerBoardSize; 
    private JTextField textFieldScore;
    
    public GUI() {
    	
    	// Initialize GUI components and layout
    	setTitle("SOS GAME");
    	setResizable(false);
    	setVisible(true);
    	setSize(450, 400); 
    	setBackground(Color.WHITE);		
	getContentPane().setBackground(Color.WHITE);		
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setContentPane();
	pack(); 		
		
	// Initialize game objects
	generalGame = new GeneralGame();
        simpleGame = new SimpleGame();
        board = simpleGame;	
    }
	
    public Board getBoard(){
	return board;
    }
	
    // Setup content pane with game components
    protected void setContentPane(){	
		
    	gameBoardCanvas = new GameBoardCanvas();
        gameBoardCanvas.setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
        Border borderGrid = BorderFactory.createLineBorder(Color.BLACK, 2);
        gameBoardCanvas.setBorder(borderGrid);
        
        JPanel panelNorth = new JPanel();
        JPanel panelEast = new JPanel();
        JPanel panelSouth = new JPanel();
        JPanel panelWest = new JPanel();
        
        panelNorth.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        panelNorth.setBackground(Color.WHITE);
        panelEast.setBackground(Color.WHITE);
        panelSouth.setBackground(Color.WHITE);
        panelWest.setBackground(Color.WHITE);
                          
        // Setup game mode selection
        JLabel labelGameMode = new JLabel("Mode:");
        labelGameMode.setFont(new Font("Consolas", Font.BOLD, 14));  
        labelGameMode.setHorizontalAlignment(SwingConstants.LEFT);
        JRadioButton buttonSimpleGame = new JRadioButton("Simple", true);
        buttonSimpleGame.setFont(new Font("Consolas", Font.BOLD, 14));  
        buttonSimpleGame.setBackground(Color.WHITE);
        JRadioButton buttonGeneralGame = new JRadioButton("General");
        buttonGeneralGame.setFont(new Font("Consolas", Font.BOLD, 14));  
        buttonGeneralGame.setBackground(Color.WHITE);
        
        gameModeSelectionGroup = new ButtonGroup();
        gameModeSelectionGroup.add(buttonSimpleGame);
        gameModeSelectionGroup.add(buttonGeneralGame);
        
        buttonSimpleGame.addActionListener(e -> {
            board = simpleGame;
        });
        buttonGeneralGame.addActionListener(e -> {
            board = generalGame;
        });
        
        panelNorth.add(labelGameMode);
        panelNorth.add(buttonSimpleGame);
        panelNorth.add(buttonGeneralGame);
        
        // Setup board size selection
        JLabel spaceLabel = new JLabel("                            ");
        JLabel boardSizeLabel = new JLabel("Board Size:");
        boardSizeLabel.setFont(new Font("Consolas", Font.BOLD, 14));  
        spinnerBoardSize = new JSpinner(new SpinnerNumberModel(3, 3, 8, 1));
        spinnerBoardSize.addChangeListener(e -> {
            int newSize = (int) spinnerBoardSize.getValue();
            board.setBoardSize(newSize); // change board size
            board.newGame();
            gameBoardCanvas.repaint(); 
        });             
        panelNorth.add(spaceLabel);
        panelNorth.add(boardSizeLabel);    
        panelNorth.add(spinnerBoardSize);
        
        // Setup blue player selection
        JLabel labelBluePlayer = new JLabel("<html><font color='blue'>Blue Player:</font></html>");        
        labelBluePlayer.setFont(new Font("Consolas", Font.BOLD, 14));  
        buttonBlueS = new JRadioButton("S", true);
        buttonBlueS.setActionCommand("S");
        buttonBlueS.setBackground(Color.WHITE);
        buttonBlueO = new JRadioButton("O"); 
        buttonBlueO.setActionCommand("O");
        buttonBlueO.setBackground(Color.WHITE);
        
        blueSelectionGroup = new ButtonGroup();
        blueSelectionGroup.add(buttonBlueS);
        blueSelectionGroup.add(buttonBlueO);
        
        panelWest.add(labelBluePlayer);
        panelWest.add(buttonBlueS);
        panelWest.add(buttonBlueO); 
        
        // Setup red player selection
        JLabel labelRedPlayer = new JLabel("<html><font color='red'>Red Player:</font></html>");
        labelRedPlayer.setFont(new Font("Consolas", Font.BOLD, 14));  
        buttonRedS = new JRadioButton("S", true);
        buttonRedS.setActionCommand("S");
        buttonRedS.setBackground(Color.WHITE);
        buttonRedO = new JRadioButton("O");   
        buttonRedO.setActionCommand("O");
        buttonRedO.setBackground(Color.WHITE);
        
        redSelectionGroup = new ButtonGroup();
        redSelectionGroup.add(buttonRedS);
        redSelectionGroup.add(buttonRedO);
        
        panelEast.add(labelRedPlayer);
        panelEast.add(buttonRedS);
        panelEast.add(buttonRedO);
        
        // Configure main GUI layout
        Container ContentPane = getContentPane();
        ContentPane.setLayout(new BorderLayout());
        ContentPane.add(gameBoardCanvas, BorderLayout.CENTER);
        ContentPane.add(panelNorth, BorderLayout.NORTH);
        ContentPane.add(panelEast, BorderLayout.EAST);
        ContentPane.add(panelSouth, BorderLayout.SOUTH);
        ContentPane.add(panelWest, BorderLayout.WEST);
        panelNorth.setPreferredSize(new Dimension(100, 75)); 
        panelEast.setPreferredSize(new Dimension(100, 100));
        panelSouth.setPreferredSize(new Dimension(100, 100));
        panelWest.setPreferredSize(new Dimension(100, 100));        
        
        // Setup score-keeping and current turn
        textFieldScore = new JTextField();
        textFieldScore.setFont(new Font("Calibri", Font.PLAIN, 15));  
        textFieldScore.setBorder(null);
        textFieldScore.setColumns(16);        
        textFieldCurrentTurn = new JTextField();
        textFieldCurrentTurn.setFont(new Font("Consolas", Font.BOLD, 16));
        textFieldCurrentTurn.setBorder(null);
        textFieldCurrentTurn.setBackground(Color.WHITE);
        textFieldCurrentTurn.setColumns(15);
        panelSouth.add(textFieldScore);
        panelSouth.add(textFieldCurrentTurn);           
        
        // Initialize reset button (start new game, restore defaults)
        JButton buttonNewGame = new JButton();        
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("refresh.png")); // Replace with your image path
        Image originalImage = originalIcon.getImage();
        int newWidth = 24; 
        int newHeight = 24;
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);
        buttonNewGame.setIcon(newIcon);
        buttonNewGame.setToolTipText("Start New Game");

        buttonNewGame.addActionListener(e -> {
            
            board.newGame();
            spinnerBoardSize.setValue(3); 
            buttonBlueS.setSelected(true);
            buttonRedS.setSelected(true);
            buttonSimpleGame.setSelected(true);
            textFieldCurrentTurn.setText(""); 
            textFieldScore.setText(""); 
            gameBoardCanvas.repaint(); 
        });
        panelSouth.add(buttonNewGame);         				
    }
	
    class GameBoardCanvas extends JPanel {
	    
	GameBoardCanvas() {	   
	    // Listen for mouse clicks to handle player moves
	    addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            handleMouseClick(e);
	        }
	    });
	}
	    
	// Process mouse click events on the game board
	private void handleMouseClick(MouseEvent e) {
	        
	    if (board.getCurrentGameState() == GameState.PLAYING) {
	        int rowSelected = e.getY() / cellSize;
	        int columnSelected = e.getX() / cellSize;
	            
	        if (board.getCell(rowSelected, columnSelected) == Cell.EMPTY) {
	                processMove(rowSelected, columnSelected);
	        }
	    }
	}
	    
	// Make move based on the selected cell & current player
	private void processMove(int row, int col) {
	        
	    char currentPlayer = board.getCurrentPlayer();
	    Cell playerSymbol = determinePlayerSymbol(currentPlayer);
	        
	    if (playerSymbol != null) {
	        board.makeMove(row, col, playerSymbol);
	        repaint();
	        handleGameResult();
	    }
	}
	    
	// Determines proper symbol based on current player's choice
	private Cell determinePlayerSymbol(char currentPlayer) {
	    ButtonModel selection = (currentPlayer == 'B') ? blueSelectionGroup.getSelection() : redSelectionGroup.getSelection();
	    return (selection != null && selection.getActionCommand() != null) ? Cell.valueOf(selection.getActionCommand()) : null;
	}
	    
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    setBackground(Color.WHITE);
	        
	    int canvasSize = Math.min(getWidth(), getHeight());
	    cellSize = canvasSize / board.getBoardSize();
	    cellPadding = cellSize / 6;
	    symbolSize = cellSize - cellPadding * 2;
	        
	    drawGridLines(g);
	    drawBoard(g);
	}
	    
	// Draws grid lines according to board size
	private void drawGridLines(Graphics g) {
	    g.setColor(Color.BLACK);
	    int gridSize = getSize().width / board.getBoardSize();
	        
	    for (int i = 1; i < board.getBoardSize(); i++) {
	        int pos = i * gridSize;
	        g.drawLine(pos, 0, pos, getSize().height);
	        g.drawLine(0, pos, getSize().width, pos);
	    }
	}
	    
	// Draws S and O symbols on the board
	private void drawBoard(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	        
	    for (int row = 0; row < board.getBoardSize(); row++) {
	        for (int col = 0; col < board.getBoardSize(); col++) {
	            int x1 = col * cellSize + cellPadding;
	            int y1 = row * cellSize + cellPadding;
	   	    Cell cellValue = board.getCell(row, col);
	            if (cellValue == Cell.S || cellValue == Cell.O) {
	                drawSymbol(g2d, cellValue.name().charAt(0), x1, y1);
	            }
	        }
	    }
	}
	    
	// Draw a symbol at specific cell coordinates
	private void drawSymbol(Graphics2D g2d, char symbol, int x, int y) {
	    String letter = String.valueOf(symbol);
	        
	    Font font = new Font("Lucida Console", Font.BOLD, cellSize > 30 ? symbolSize : 28);
	        
	    // Center the symbol within the cell.
	    int centerX = x + (cellSize - g2d.getFontMetrics(font).stringWidth(letter)) / 2;
	    int centerY = y + (cellSize + g2d.getFontMetrics(font).getHeight()) / 2 - g2d.getFontMetrics(font).getDescent();
	        
	    g2d.setFont(font);
	    g2d.drawString(letter, centerX, centerY);
	}
	    
	//Update GUI based on game's current state and scores
	public void handleGameResult() {
	    // Update the status message based on the game state
	    switch (board.getCurrentGameState()) {
	        case BLUE_WINS:
	            textFieldCurrentTurn.setText("Blue Wins!");
	            break;
	        case RED_WINS:
	            textFieldCurrentTurn.setText("Red Wins!");
	            break;
	        case DRAW:
	            textFieldCurrentTurn.setText("Tie Game!");
	            break;
	        default:
	            // Show which player's turn it is during an ongoing game.
	            textFieldCurrentTurn.setText("Play: " + (board.getCurrentPlayer() == 'B' ? "Blue" : "Red"));
	            break;
	        }	        
	        // Update scores for both players.
	        textFieldScore.setText(String.format("Blue Score: %d, Red Score: %d", board.getBlueScore(), board.getRedScore()));
	        repaint();
	    }
	}
	
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
            	public void run() {
                    GUI gui = new GUI();
                    gui.setVisible(true);
                    gui.board.initializeBoard();
                }
	    });
	}
}
