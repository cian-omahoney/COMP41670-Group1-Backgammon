package backgammon;
public class Command {
	
	private CommandType _commandType;

	public Command(String userInput) {
		_commandType = CommandType.valueOf(userInput.toUpperCase());
	}
	
	public static boolean isValid(String userInput) {
		String inputFormatted = userInput.toUpperCase();
		return  inputFormatted.matches(CommandType.QUIT.getRegex()) || 
				inputFormatted.matches(CommandType.HELP.getRegex()) ||
				inputFormatted.matches(CommandType.ROLL.getRegex());
	}
	
	public boolean isQuit() {
		return _commandType == CommandType.QUIT;
	}
	
	public boolean isRoll() {
		return _commandType == CommandType.ROLL;
	}
	
	public boolean isHelp() {
		return _commandType == CommandType.HELP;
	}
}
