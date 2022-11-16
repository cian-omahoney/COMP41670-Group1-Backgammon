import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class PlayerTest {
	private Player player;
	
	@BeforeEach
	void setUp() {
		player = new Player(Checker.WHITE,1);
		player.setName("Testname");
	} 

	/*@Test
	void testRollDice_AreValidNumbersReturned() {
		player.rollBothDice();
		int[] retVal = player.getDiceRoll();
		assertTrue((retVal[0] <= 6 && retVal[0] >= 1), "Fail message: Player.getDiceRoll() isn't working");
		assertTrue((retVal[1] <= 6 && retVal[1] >= 1), "Fail message: Player.getDiceRoll() isn't working");
	}*/

	/*@Test
	void testGetDiceRoll() {
		player.rollBothDice();
		int[] retVal = player.getDiceRoll();
		assertTrue((retVal[0] <= 6 && retVal[0] >= 1), "Fail message: Player.getDiceRoll() isn't working");
		assertTrue((retVal[1] <= 6 && retVal[1] >= 1), "Fail message: Player.getDiceRoll() isn't working");
	}*/

	@Test
	void testGetName() {
		assertEquals("Testname", player.getName());
	}

	@Test
	void testGetNumber() {
		assertEquals(Checker.WHITE, player.getColour());
	}
}