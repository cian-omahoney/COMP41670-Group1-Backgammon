// Team 1 Backgammon Project
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Junit test for Command class.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
class CommandTest {
	private Command command;

	@Test
	void testIsValid_CheckValidAndInvalidCommandStrings() {
		assertFalse(Command.isValid("NotValid"), 			"Fail message: Command.isValid() not false for 'NotValid' string.");
		assertFalse(Command.isValid("234hfe  wte0 0q3r"), 	"Fail message: Command.isValid() not false for '234hfe  wet0 0q3r' string.");
		assertTrue(Command.isValid("Quit"), 				"Fail message: Command.isValid() not true for 'Quit' string.");
		assertTrue(Command.isValid("hint"), 				"Fail message: Command.isValid() not true for 'hint' string.");
		assertTrue(Command.isValid("roll"), 				"Fail message: Command.isValid() not true for 'roll' string.");
		assertTrue(Command.isValid("ROLL"), 				"Fail message: Command.isValid() not true for 'ROLL' string.");
		assertTrue(Command.isValid("pip"), 					"Fail message: Command.isValid() not true for 'pip' string.");
		assertTrue(Command.isValid("first"), 				"Fail message: Command.isValid() not true for 'first' string.");
		assertTrue(Command.isValid("double"), 				"Fail message: Command.isValid() not true for 'double' string.");
	}

	@Test
	void testIsQuit_ValidQuitCommand() {
		command = new Command("Quit");
		assertTrue(command.isQuit());
	}

	@Test
	void testIsRoll_ValidRollCommand() {
		command = new Command("Roll");
		assertTrue(command.isRoll());
	}

	@Test
	void testIsHint_ValidHintCommand() {
		command = new Command("Hint");
		assertTrue(command.isHint());
	}

	@Test
	void testIsPip_ValidPipCommand() {
		command = new Command("pip");
		assertTrue(command.isPip());
	}

	@Test
	void testIsFirst_ValidFirstCommand() {
		command = new Command("first");
		assertTrue(command.isFirst());
	}

	@Test
	void testIsDouble_ValidDoubleCommand() {
		command = new Command("Double");
		assertTrue(command.isDouble());
	}

	@Test
	void testIsInvalid_InvalidCommand() {
		command = new Command("this is not valid");
		assertTrue(command.isInvalid());
	}

	@Test
	void getForcedDiceValues(){
		command = new Command("Dice 5 6");
		List<Integer> Dice= Arrays.asList(5,6);
		assertEquals(Dice,command.getForcedDiceValues());
	}
}
