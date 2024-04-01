package sprint3.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprint3.product.Board;
import sprint3.product.GeneralGame;

class GeneralGameTest {

    private GeneralGame game;

    @BeforeEach
    void setUp() {     // Initialized before each test
        game = new GeneralGame(3);
    }

    @Test
    void testMakeValidMove() {
        assertTrue(game.makeMove(0, 0, Board.Cell.S));  // Return true when valid move made
        assertEquals(Board.Cell.S, game.getSymbol(0, 0));   // Cell has the symbol placed by the move
        assertEquals('R', game.getCurrentPlayer()); // Player changes from 'B' to 'R'
    }

    @Test
    void testMakeInvalidMoveInOccupiedCell() {
        game.makeMove(0, 0, Board.Cell.S); // First player makes a valid move
        assertFalse(game.makeMove(0, 0, Board.Cell.O));   // Invalid move on full cell
    }

    @Test
    void testBoardIsFullWithoutSOS() {
    	fillBoardWithNoSOSEvents();   // Fill the board without forming an SOS
        assertTrue(game.isBoardFull());  // Board should be full        
    }

    @Test
    void testGameEndsWithDraw() {
    	fillBoardWithNoSOSEvents();        
        assertEquals(Board.GameState.DRAW, game.getCurrentGameState()); // Game should end in tie
    }

    @Test
    void testBlueWinsBySOSFormation() {
        simulateBlueWinningSOS();
        assertEquals(Board.GameState.BLUE_WINS, game.getCurrentGameState());
    }

    // Helper methods for scenarios
    private void fillBoardWithNoSOSEvents() {
        game = new GeneralGame(3);     // Reset to a 3x3 board for this specific test
        game.makeMove(0, 0, Board.Cell.S);
        game.makeMove(0, 1, Board.Cell.S);
        game.makeMove(0, 2, Board.Cell.S);
        game.makeMove(1, 0, Board.Cell.S);
        game.makeMove(1, 1, Board.Cell.S);
        game.makeMove(1, 2, Board.Cell.S);
        game.makeMove(2, 0, Board.Cell.S);
        game.makeMove(2, 1, Board.Cell.S);
        game.makeMove(2, 2, Board.Cell.S);
    }

    private void simulateBlueWinningSOS() {
        game.makeMove(0, 0, Board.Cell.S); // B
        game.makeMove(1, 0, Board.Cell.O); // R
        game.makeMove(2, 0, Board.Cell.S); // B SOS
        game.makeMove(0, 1, Board.Cell.O); // B 
        game.makeMove(0, 2, Board.Cell.S); // R SOS
        game.makeMove(1, 1, Board.Cell.O); // R SOS
        game.makeMove(1, 2, Board.Cell.O); // R
        game.makeMove(2, 2, Board.Cell.S); // B SOS x 2
        game.makeMove(2, 1, Board.Cell.S); // B SOS
    }
}
