public enum CommandType {
	QUIT ("QUIT"),
	ROLL ("ROLL"),
	HELP ("HELP"),
    INVALID ("");
	
	private String _regex;
	
	CommandType(String regex) {
        this._regex = regex;
    }
	
    public String getRegex() {
        return _regex;
    }
}