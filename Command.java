import java.util.ArrayList;
import java.util.List;

public class Command {
	private CommandType _commandType;
	private List<Integer> _forcedDiceValues;

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

	public List<Integer> getForcedDiceValues() {
		return _forcedDiceValues;
	}
	
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
	
	public boolean isQuit() {
		return _commandType == CommandType.QUIT;
	}
	
	public boolean isRoll() {
		return _commandType == CommandType.ROLL;
	}
	
	public boolean isHint() {
		return _commandType == CommandType.HINT;
	}

	public boolean isPip() {
		return _commandType == CommandType.PIP;
	}

	public boolean isFirst() {
		return _commandType == CommandType.FIRST;
	}

	public boolean isDouble() {
		return _commandType == CommandType.DOUBLE;
	}

	public boolean isInvalid() {return _commandType == CommandType.INVALID;}

	public boolean isDice() {return _commandType == CommandType.DICE;}
}
