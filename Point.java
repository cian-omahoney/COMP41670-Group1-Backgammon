// Team 1 Backgammon Project
// By 
/***@author Cian O'Mahoney Github:cian-omahoney 
 *  @author Ciarán Cullen  Github:TangentSplash
*/

import java.util.*;

// Class that represents an individual point that will make up the board
public class Point {
	public static final int MAXIMUM_PIP_NUMBER = 24;
	public static final int MAXIMUM_BEAROFF_PIP_NUMBER = 6;
	public static final int BACKGAMMONED_POINT_CUTOFF = 19;
	private int _whitePointNumber;
	private int _redPointNumber;
	private Checker _residentColour;
	private Stack<Checker> _checkers;
	
	public Point(int whitePointIndex) {
		this._whitePointNumber = whitePointIndex + 1;
		this._redPointNumber = (MAXIMUM_PIP_NUMBER - whitePointIndex);
		_residentColour = Checker.EMPTY;
		_checkers = new Stack<>();
	}


	public void addCheckers(Checker checkerColour) {
		addCheckers(checkerColour, 1);
	}
	
	/**
	 * Add checkers to this point if valid
	 * @param checkerColour	the colour of checker to add
	 * @param quantity	the number of these checkers to add
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

	public void removeChecker() {
		if(!_checkers.isEmpty()) {
			_checkers.pop();
		}
		if(_checkers.isEmpty()){
			_residentColour = Checker.EMPTY;
		}
	}
	
	public int getPointNumber(Player activePlayer) {
		return switch(activePlayer.getColour()) {
			case RED -> _redPointNumber;
			case WHITE -> _whitePointNumber;
			case EMPTY -> 0;
		};
	}

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
	
	public Checker getResidentColour() {
		return _residentColour;
	}
	
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

	public boolean isEmpty() {
		return _checkers.isEmpty();
	}

}
