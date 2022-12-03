// Team 1 Backgammon Project
/**
 * Class containing all valid command types
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
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