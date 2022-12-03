// Team 1 Backgammon Project
// By 
/***@author Cian O'Mahoney Github:cian-omahoney 
 *  @author Ciarán Cullen  Github:TangentSplash
*/

//Class representing individual Checkers
public enum Checker {
	WHITE	(UI.WHITE_CHECKER_COLOUR + "O" + UI.CLEAR_COLOURS),
	RED		(UI.RED_CHECKER_COLOUR +   "X" + UI.CLEAR_COLOURS),
	EMPTY	("-");
	
	private final String _symbol;
	
	Checker(String symbol) {
		this._symbol = symbol;
	}
	
	public String getSymbol() {
		return _symbol;
	}
}
