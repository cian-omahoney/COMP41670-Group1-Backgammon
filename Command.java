// Team 1 Backgammon Project
import java.util.ArrayList;
import java.util.List;

/**
 * Class to interpret input user commands.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
public class Command {
	private CommandType _commandType;
	private List<Integer> _forcedDiceValues;

	/**
	 * Parse user input string into command.
	 * @param userInput String input by user.
	 */
	public Command(String userInput) {
		_forcedDiceValues = new ArrayList<>(2);
		if(isValid(userInput)) {
			if(userInput.toUpperCase().matches(CommandType.DICE.getRegex())) {
				_commandType = CommandType.DICE;
				_forcedDiceValues.add(Integer.parseInt(userInput.split("[ |\t]+")[1]));
				_forcedDiceValues.add(Integer.parseInt(userInput.split("[ |\t]+")[2]));
			}
			else {
				_commandType = CommandType.valueOf(userInput.toUpperCase());
			}
		}
		else {
			_commandType = CommandType.INVALID;
		}
	}

	/**
	 * Get values of forced dice values when "dice [] []" command used.
	 * @return Array list containing dice values.
	 */
	public List<Integer> getForcedDiceValues() {
		return _forcedDiceValues;
	}

	/**
	 * Check if user input string is a valid command.
	 * @param userInput String input by user.
	 * @return True if command is valid, false otherwise.
	 */
	public static boolean isValid(String userInput) {
		String inputFormatted = userInput.toUpperCase();
		return  inputFormatted.matches(CommandType.QUIT.getRegex()) || 
				inputFormatted.matches(CommandType.HINT.getRegex()) ||
				inputFormatted.matches(CommandType.ROLL.getRegex()) ||
				inputFormatted.matches(CommandType.PIP.getRegex())  ||
				inputFormatted.matches(CommandType.DICE.getRegex())  ||
				inputFormatted.matches(CommandType.DOUBLE.getRegex())  ||
				inputFormatted.matches(CommandType.FIRST.getRegex());
	}

	/**
	 * Check is current command a valid quit command.
	 * @return True if command is quit. False otherwise.
	 */
	public boolean isQuit() {
		return _commandType == CommandType.QUIT;
	}

	/**
	 * Check is current command a valid roll command.
	 * @return True if command is roll. False otherwise.
	 */
	public boolean isRoll() {
		return _commandType == CommandType.ROLL;
	}

	/**
	 * Check is current command a valid hint command.
	 * @return True if command is hint. False otherwise.
	 */
	public boolean isHint() {
		return _commandType == CommandType.HINT;
	}

	/**
	 * Check is current command a valid pip command.
	 * @return True if command is pip. False otherwise.
	 */
	public boolean isPip() {
		return _commandType == CommandType.PIP;
	}

	/**
	 * Check is current command a valid first command.
	 * @return True if command is first. False otherwise.
	 */
	public boolean isFirst() {
		return _commandType == CommandType.FIRST;
	}

	/**
	 * Check is current command a valid double command.
	 * @return True if command is double. False otherwise.
	 */
	public boolean isDouble() {
		return _commandType == CommandType.DOUBLE;
	}

	/**
	 * Check is current command an invalid command.
	 * @return True if command is invalid. False otherwise.
	 */
	public boolean isInvalid() {return _commandType == CommandType.INVALID;}

	/**
	 * Check is current command a valid dice command.
	 * @return True if command is dice. False otherwise.
	 */
	public boolean isDice() {return _commandType == CommandType.DICE;}
}
