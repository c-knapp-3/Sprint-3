package sprint3.test;
import sprint3.product.SimpleGame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleGameTest {
	
    private SimpleGame game;

    @BeforeEach
    void setUp() {       // Setup a new 3x3 game board before each test        
        game = new SimpleGame(3);
    }

    @Test
    void testBoardInitialization() {
        assertEquals(3, game.getBoardSize());  // BoardSize should be initialized to 3
        for (int row = 0; row < game.getBoardSize(); row++) {
            for (int col = 0; col < game.getBoardSize(); col++) {
                assertEquals(SimpleGame.Cell.EMPTY, game.getCell(row, col)); // All cells should be initialized to EMPTY
            }
        }
        assertEquals(SimpleGame.GameState.PLAYING, game.getCurrentGameState());  // Game status should be PLAYING
    }

    @Test
    void testCustomBoardSizeInitialization() {
        SimpleGame customGame = new SimpleGame(5);
        assertEquals(5, customGame.getBoardSize());   // BoardSize should be the same as the custom size
    }

    @Test
    void testMakeMoveAndSwitchPlayers() {
        assertTrue(game.makeMove(0, 0, SimpleGame.Cell.S));  // Return true when valid move made
        assertEquals(SimpleGame.Cell.S, game.getCell(0, 0)); // Cell has the symbol placed by the move
        assertEquals('R', game.getCurrentPlayer());  // Player changes from 'B' to 'R'
    }

    @Test
    void testScoreUpdating() {
    	SimpleGame game = new SimpleGame(3);
        game.setCurrentPlayer('B');
        game.countSOS(); 
        assertEquals(1, game.getBlueScore());  // Blue score should be incremented
    }

    @Test
    void testEmptyBoard() {
    	SimpleGame game = new SimpleGame(3);  // New game is initialized with all empty cells
        assertFalse(game.isBoardFull());
    }

    @Test
    void testPartiallyFilledBoard() {
    	SimpleGame game = new SimpleGame(3);
        game.makeMove(0, 0, SimpleGame.Cell.S); // Fill one cell
        assertFalse(game.isBoardFull());
    }

    @Test
    void testCompletelyFilledBoard() {
    	SimpleGame game = new SimpleGame(3);
    	game.makeMove(0, 0, SimpleGame.Cell.S);
    	game.makeMove(0, 1, SimpleGame.Cell.S);
    	game.makeMove(0, 2, SimpleGame.Cell.S);
    	game.makeMove(1, 0, SimpleGame.Cell.S);
    	game.makeMove(1, 1, SimpleGame.Cell.S);
    	game.makeMove(1, 2, SimpleGame.Cell.S);
    	game.makeMove(2, 0, SimpleGame.Cell.S);
    	game.makeMove(2, 1, SimpleGame.Cell.S);
    	game.makeMove(2, 2, SimpleGame.Cell.S); 
        assertTrue(game.isBoardFull());  // The board is full
    }

    @Test
    void testValidMove() {
    	SimpleGame game = new SimpleGame(3);
        assertTrue(game.makeMove(0, 0, SimpleGame.Cell.S)); // Valid move
        assertEquals(SimpleGame.Cell.S, game.getSymbol(0, 0)); // Make sure cell is updated
        assertEquals('R', game.getCurrentPlayer());  // Make sure player switched from 'B' to 'R'
    }
    
    @Test
    void testInvalidMoveOutOfBoard() {
        assertFalse(game.makeMove(3, 3, SimpleGame.Cell.O)); // Invalid move out of bounds
    }

    @Test
    void testInvalidMoveOccupiedCell() {
        game.makeMove(1, 1, SimpleGame.Cell.S); // Place symbol in cell
        assertFalse(game.makeMove(1, 1, SimpleGame.Cell.O));  // Invalid move symbol already in cell
    }

    @Test
    void testBlueWinsAfterSequenceOfMoves() {        
        game.makeMove(1, 0, SimpleGame.Cell.S);
        game.makeMove(1, 1, SimpleGame.Cell.O);
        game.makeMove(1, 2, SimpleGame.Cell.S);   // Simulate a winning condition for Blue
        game.updateGameState('B');     // Manually update game status to reflect Blue's win
        assertEquals(SimpleGame.GameState.BLUE_WINS, game.getCurrentGameState()); // Blue should be win
    }

    @Test
    void testRedWinsAfterSequenceOfMoves() {        
        game.setCurrentPlayer('R');       // Set up Red to win
        game.makeMove(2, 0, SimpleGame.Cell.S);
        game.makeMove(2, 1, SimpleGame.Cell.O);
        game.makeMove(2, 2, SimpleGame.Cell.S);        
        game.updateGameState('R');      // Manually update game status to reflect Red's win
        assertEquals(SimpleGame.GameState.RED_WINS, game.getCurrentGameState()); // Red should win
    }

    @Test
    void testSOSInRow() {       // Set up an SOS in a row        
        game.makeMove(0, 0, SimpleGame.Cell.S);
        game.makeMove(0, 1, SimpleGame.Cell.O);
        game.makeMove(0, 2, SimpleGame.Cell.S);
        assertTrue(game.hasSOS()); // SOS event found in row
    }

    @Test
    void testSOSInColumn() {    // Set up an SOS in a column
        game.makeMove(0, 1, SimpleGame.Cell.S);
        game.makeMove(1, 1, SimpleGame.Cell.O);
        game.makeMove(2, 1, SimpleGame.Cell.S);
        assertTrue(game.hasSOS()); // SOS event found in column
    }

    @Test
    void testSOSInDiagonal() {    // Set up an SOS diagonally        
        game.makeMove(0, 0, SimpleGame.Cell.S);
        game.makeMove(1, 1, SimpleGame.Cell.O);
        game.makeMove(2, 2, SimpleGame.Cell.S);
        assertTrue(game.hasSOS());  // SOS event found in diagonal
    }
}
