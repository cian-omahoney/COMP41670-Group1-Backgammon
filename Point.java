import java.util.*;

public class Point {
	public static final int MAXIMUM_PIP_NUMBER = 24;
	private int _whitePointNumber;
	private int _redPointNumber;
	private Checker _residentColour;
	private Stack<Checker> _checkers;
	
	public Point(int whitePointNumber) {
		this._whitePointNumber = whitePointNumber;
		this._redPointNumber = (MAXIMUM_PIP_NUMBER - whitePointNumber + 1);
		_residentColour = Checker.EMPTY;
		_checkers = new Stack<Checker>();
	}
	
	public void addCheckers(Checker checkerColour) {
		if(_checkers.isEmpty() && _residentColour == Checker.EMPTY) {
			_checkers.add(checkerColour);
			_residentColour = checkerColour;
		}
		else if (_residentColour == checkerColour) {
			_checkers.add(checkerColour);
		}
		else {
			// UNFINISHED
			System.out.println("Unable to place checker here!");
		}
	}
	
	public void addCheckers(Checker checkerColour, int quantity) {
		if(_checkers.isEmpty() && _residentColour == Checker.EMPTY) {
			for(int i=0; i<quantity; i++) {
				_checkers.add(checkerColour);
			}
			_residentColour = checkerColour;
		}
		else if (_residentColour == checkerColour) {
			for(int i=0; i<quantity; i++) {
				_checkers.add(checkerColour);
			}
		}
		else {
			// UNFINISHED
			System.out.println("Unable to place checker here!");
		}
	}
	
	public int getWhitePointNumber() {
		return _whitePointNumber;
	}
	
	public int getRedPointNumber() {
		return _redPointNumber;
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
}
