public enum Checker {
	WHITE	(UI.WHITE_CHECKER_COLOUR + "O" + UI.CLEAR_COLOURS),
	RED		(UI.RED_CHECKER_COLOUR +   "X" + UI.CLEAR_COLOURS),
	EMPTY	("-");
	
	private String _symbol;
	
	private Checker(String symbol) {
		this._symbol = symbol;
	}
	
	public String getSymbol() {
		return _symbol;
	}
}
