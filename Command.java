public class Command {
	private CommandType _commandType;

	public Command(String userInput) {
		if(isValid(userInput)) {
			_commandType = CommandType.valueOf(userInput.toUpperCase());
		}
		else {
			_commandType = CommandType.INVALID;
		}
	}
	
	public static boolean isValid(String userInput) {
		String inputFormatted = userInput.toUpperCase();
		return  inputFormatted.matches(CommandType.QUIT.getRegex()) || 
				inputFormatted.matches(CommandType.HINT.getRegex()) ||
				inputFormatted.matches(CommandType.ROLL.getRegex()) ||
				inputFormatted.matches(CommandType.PIP.getRegex());
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
	public boolean isInvalid() {
		return _commandType == CommandType.INVALID;
	}
}
