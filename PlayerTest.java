import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

class PlayerTest {
	private Player player;
	
	@BeforeEach
	void setUp() {
		player = new Player(Checker.WHITE,1);
		player.setName("Testname");
	}

	@Test
	void testGetName() {
		assertEquals("Testname", player.getName());
	}

	@Test
	void testGetNumber() {
		assertEquals(player.getNumber(), 1);
	}

    @Test
    void testRollBothDice() {
        player.rollBothDice();
        assertTrue(player.getAvailableMoves().size() >= 2 && player.getAvailableMoves().size() <= 4);
        if(player.getAvailableMoves().size() == 2) {
            assertTrue(player.getAvailableMoves().get(0) > player.getAvailableMoves().get(1));
        }
        else if(player.getAvailableMoves().size() == 4) {
            assertTrue(player.getAvailableMoves().get(0) == player.getAvailableMoves().get(1));
            assertTrue(player.getAvailableMoves().get(1) == player.getAvailableMoves().get(2));
            assertTrue(player.getAvailableMoves().get(2) == player.getAvailableMoves().get(3));
        }
    }

    @Test
    void testAddAvailableMove() {
        player.addAvailableMove(1);
        player.addAvailableMove(2);
        player.addAvailableMove(3);
        assertTrue(player.getAvailableMoves().size() == 3);
        assertTrue(player.getAvailableMoves().get(0) == 3);
        assertTrue(player.getAvailableMoves().get(2) == 1);
    }

    @Test
    void getAvailableMoves() {
        player.addAvailableMove(1);
        player.addAvailableMove(2);
        player.addAvailableMove(3);
        assertTrue(player.getAvailableMoves().size() == 3);
        assertTrue(player.getAvailableMoves().get(0) == 3);
        assertTrue(player.getAvailableMoves().get(2) == 1);
    }

    @Test
    void availableMovesRemaining() {
        player.addAvailableMove(1);
        player.addAvailableMove(2);
        player.addAvailableMove(3);
        assertTrue(player.availableMovesRemaining());
    }

    @Test
    void testUpdateAvailableMoves() {
        ArrayList<Integer> testMoves = new ArrayList<Integer>(3);
        player.updateAvailableMoves(testMoves);
        assertEquals(player.getAvailableMoves().size(), 0);

        testMoves.add(20);
        testMoves.add(15);
        testMoves.add(12);
        testMoves.add(1);

        player.addAvailableMove(3);
        player.addAvailableMove(5);
        player.addAvailableMove(1);
        player.addAvailableMove(6);

        player.updateAvailableMoves(testMoves);

        assertEquals(player.getAvailableMoves().get(0), 1);
        assertTrue(player.availableMovesRemaining());
    }

    @Test
    void getColour() {
        assertEquals(player.getColour(), Checker.WHITE);
    }

    @Test
    void setName() {
        player.setName("Testname");
        assertEquals(player.getName(), "Testname");
    }
}