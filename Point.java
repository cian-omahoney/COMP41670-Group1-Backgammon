// Team 1 Backgammon Project
import java.util.*;
/**
 * Class that represents an individual point that will make up the board
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
*/
public class Point {
	/**
	 * The maximum pip number on the board.
	 */
	public static final int MAXIMUM_PIP_NUMBER = 24;
	/**
	 * The maximum pip number of a checker if the player is to be allowed to being
	 * bearing off checkers.
	 */
	public static final int MAXIMUM_BEAROFF_PIP_NUMBER = 6;
	/**
	 * If at least one checkers has a pip number greater than this when the
	 * other player wins, the game end in backgammon.
	 */
	public static final int BACKGAMMONED_POINT_CUTOFF = 19;
	private int _whitePointNumber;
	private int _redPointNumber;
	private Checker _residentColour;
	private Stack<Checker> _checkers;

	/**
	 * Point constructor.
	 * @param whitePointIndex Index of point from white player perspective.
	 */
	public Point(int whitePointIndex) {
		this._whitePointNumber = whitePointIndex + 1;
		this._redPointNumber = (MAXIMUM_PIP_NUMBER - whitePointIndex);
		_residentColour = Checker.EMPTY;
		_checkers = new Stack<>();
	}

	/**
	 * Add checkers to point.
	 * @param checkerColour Checker colour to add.
	 */
	public void addCheckers(Checker checkerColour) {
		addCheckers(checkerColour, 1);
	}
	
	/**
	 * Add checkers to this point if valid
	 * @param checkerColour	The colour of checker to add
	 * @param quantity	The number of these checkers to add
	 */
	public void addCheckers(Checker checkerColour, int quantity) {
		if(_checkers.isEmpty() && _residentColour == Checker.EMPTY) {
			for(int i=0; i<quantity; i++) {
				_checkers.push(checkerColour);
			}
			_residentColour = checkerColour;
		}
		else if (_residentColour == checkerColour) {
			for(int i=0; i<quantity; i++) {
				_checkers.push(checkerColour);
			}
		}
	}

	/**
	 * Remove a single checker from the point.
	 */
	public void removeChecker() {
		if(!_checkers.isEmpty()) {
			_checkers.pop();
		}
		if(_checkers.isEmpty()){
			_residentColour = Checker.EMPTY;
		}
	}

	/**
	 * Get the point number of the checker.
	 * @param activePlayer The player whose point number to get.
	 * @return Return point number.
	 */
	public int getPointNumber(Player activePlayer) {
		return switch(activePlayer.getColour()) {
			case RED -> _redPointNumber;
			case WHITE -> _whitePointNumber;
			case EMPTY -> 0;
		};
	}

	/**
	 * Get the point number of the checker.
	 * @param player The player whose point number to get.
	 * @return Return point number.
	 */
	public int getPointNumber(int player){
		if (player==0){
			return _whitePointNumber;
		}
		else if(player==1){
			return _redPointNumber;
		}
		else{
			return 0;
		}
	}

	/**
	 * Get colour of checker on point.
	 * @return Colour of checker on point.
	 */
	public Checker getResidentColour() {
		return _residentColour;
	}

	/**
	 * Get number of checkers on point.
	 * @return Number of checkers on this point.
	 */
	public int getCheckerCount() {
		int checkerCount;
		if(!_checkers.isEmpty()) {
			checkerCount = _checkers.size();
		}
		else {
			checkerCount = 0;
		}
		return checkerCount;
	}

	/**
	 * Whether or not a point is empty.
	 * @return True if point has no checkers. False otherwise.
	 */
	public boolean isEmpty() {
		return _checkers.isEmpty();
	}
}
