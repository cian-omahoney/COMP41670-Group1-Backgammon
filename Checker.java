// Team 1 Backgammon Project
/**
 * Class modelling individual Checkers
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
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
