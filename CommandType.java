public enum CommandType {
	QUIT ("QUIT"),
	ROLL ("ROLL"),
	HINT ("HINT"),
    PIP ("PIP"),
    FIRST ("FIRST"),
    DOUBLE("DOUBLE"),
    INVALID ("");
	
	private String _regex;
	
	CommandType(String regex) {
        this._regex = regex;
    }
	
    public String getRegex() {
        return _regex;
    }
}