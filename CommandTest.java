import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandTest {
	private Command command;
	
	@BeforeEach
	void setUp() {
	}

	@Test
	void testIsValid_CheckValidAndInvalidCommandStrings() {
		assertFalse(Command.isValid("NotValid"), 			"Fail message: Command.isValid() not false for 'NotValid' string.");
		assertFalse(Command.isValid("234hfe  wte0 0q3r"), 	"Fail message: Command.isValid() not false for '234hfe  wet0 0q3r' string.");
		assertTrue(Command.isValid("Quit"), 				"Fail message: Command.isValid() not false for 'Quit' string.");
		assertTrue(Command.isValid("help"), 				"Fail message: Command.isValid() not false for 'help' string.");
		assertTrue(Command.isValid("roll"), 				"Fail message: Command.isValid() not false for 'roll' string.");
		assertTrue(Command.isValid("ROLL"), 				"Fail message: Command.isValid() not false for 'ROLL' string.");
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

	/*@Test
	void testIsHelp_ValidHelpCommand() {
		command = new Command("Help");
		assertTrue(command.isHelp());
	}*/

}
