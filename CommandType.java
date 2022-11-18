public enum CommandType {
	QUIT ("QUIT"),
	ROLL ("ROLL"),
	HINT ("HINT"),
    PIP ("PIP"),
    FIRST ("FIRST"),
    DOUBLE("DOUBLE"),
    DICE ("DICE[ \t]+[1-6][ \t]+[1-6]"),
    INVALID ("");
	
	private final String _regex;
	
	CommandType(String regex) {
        this._regex = regex;
    }
	
    public String getRegex() {
        return _regex;
    }
}