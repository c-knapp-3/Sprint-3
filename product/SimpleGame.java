package sprint3.product;

public class SimpleGame extends Board {
	
    private Cell[][] board;   // 2D array to represent the game board
    private GameState currentGameState;   // Tracks current state of game
    private int boardSize;        // Dimension of the square board
    private int blueScore;        // Blue player's score
    private int redScore;         // Red player's score
    private char currentPlayer;    // 'B' for Blue player, 'R' for Red player
    
    // Default constructor initializing a 3x3 board
    public SimpleGame() {
        this.board = new Cell[3][3];
        this.boardSize = 3;
        initializeBoard();
    }

    // Constructor with custom board size
    public SimpleGame(int size) {
    	this.board = new Cell[size][size];
    	this.boardSize = size;         
        initializeBoard();
    }
    
    // Initializes or resets the board to start a new game
    public void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = Cell.EMPTY;     // Set all cells to EMPTY
            }
        }
        currentGameState = GameState.PLAYING; // Set status to PLAYING
        currentPlayer = 'B';	    // Blue starts the game
        blueScore = redScore = 0;              // Reset both player's scores
    }    
       
    public Cell[][] getBoard() {
        return board;
    }
    
    public void setBoardSize(int size) {
    	this.boardSize = size;
    	this.board = new Cell[size][size];
        initializeBoard();
    }
    
    public int getBoardSize() {
    	return boardSize;
    }
       
    public void setCurrentPlayer(char player) {
        this.currentPlayer = player;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    public GameState getCurrentGameState() {
		return currentGameState;
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < boardSize && column >= 0 && column < boardSize)
            return board[row][column];
        else
            return Cell.EMPTY;
    }

    public Cell getSymbol(int row, int column) {
        return board[row][column];
    }
    
    public int getBlueScore() {
    	return blueScore;
    }
    
    public int getRedScore() {
    	return redScore;
    }
    
    // Resets board to start a new game
    @Override
    public void newGame() {
    	initializeBoard();
    }
    
    // Handles a player's move on the boards
    @Override
    public boolean makeMove(int row, int column, Cell cell) {
        
        if (currentGameState != GameState.PLAYING) {
            System.out.println("Game over");
            return false;
        }
        if (row >= 0 && row < boardSize && column >= 0 && column < boardSize && getCell(row, column) == Cell.EMPTY) {
            board[row][column] = cell;      // Place the cell      
            updateGameState(currentPlayer);   // Check for win/tie status        
            if (currentGameState == GameState.PLAYING) {
                switchPlayers();
            }
            return true; // The move was successfully made.
        } 
        else {
            // The move is invalid (either out of bounds or the cell is not empty).
            return false;
        }
    }
   
    // Switches the turn between players
    @Override
    public void switchPlayers() {
        currentPlayer = (currentPlayer == 'B') ? 'R' : 'B';
        System.out.println("Switching... the current player is " + currentPlayer);
    }
   
    public void countSOS() {
    	if (currentPlayer == 'B') {
    		blueScore++;
    	}
    	else {
    		redScore++;
    	}
    }    

    public void updateGameState(char turn) {
    	if (hasSOS()) {        // If SOS Event detected, update game status to indicate winner
            currentGameState = (turn == 'B') ? GameState.BLUE_WINS : GameState.RED_WINS;
            System.out.println(currentGameState + "!");
        } 
    	else if (isBoardFull()) {  // If board is full and no SOS Event detected, game is a draw
            currentGameState = GameState.DRAW;
            System.out.println("Tie game");
        }
    }
    
    public boolean isBoardFull() {
    	for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (board[row][column] == Cell.EMPTY) {   
                    return false;  // Board is not full because an empty cell was found	
                }
            }
    	}
    	return true;  // No empty cells are found, board is full
    }
    
    public boolean hasSOS() { // Checks board for any SOS events in all directions
        
    	Cell[] symbols = {Cell.S, Cell.O, Cell.S};  // Pattern to check for
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};  // Horizontal, vertical and diagonal directions
        
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                for (int[] dir : directions) {
                    if (checkDirection(row, column, dir[0], dir[1], symbols)) {  // Check each direction from current cell
                        countSOS();          // Increase score and indicate SOS Event is found
                        return true;
                    }
                }
            }
        }
        return false;   // No SOS Event found
    }

    // Checks if there's an SOS pattern starting from a chosen cell, moving in a set direction
    private boolean checkDirection(int rowStart, int columnStart, int rowDirection, int columnDirection, Cell[] symbols) {
        for (int i = 0; i < symbols.length; i++) {
            int row = rowStart + i * rowDirection;
            int column = columnStart + i * columnDirection;
            // Checks if a cell is within the board and contains the correct symbol
            if (row < 0 || row >= boardSize || column < 0 || column >= boardSize || board[row][column] != symbols[i]) {
                return false;
            }
        }
        return true;  // Found SOS Event along chosen path
    }
}
