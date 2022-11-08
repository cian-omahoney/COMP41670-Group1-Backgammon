import java.util.*;

public class Point {
	public static final int MAXIMUM_PIP_NUMBER = 24;
	private int _whitePointNumber;
	private int _redPointNumber;
	private Checker _residentColour;
	private Stack<Checker> _checkers;
	
	public Point(int whitePointIndex) {
		this._whitePointNumber = whitePointIndex + 1;
		this._redPointNumber = (MAXIMUM_PIP_NUMBER - whitePointIndex);
		_residentColour = Checker.EMPTY;
		_checkers = new Stack<Checker>();
	}
	
	public void addCheckers(Checker checkerColour) {
		addCheckers(checkerColour, 1);
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
	
	public int getPointNumber(Checker activeColour) {
		return switch(activeColour) {
			case RED -> _redPointNumber;
			case WHITE -> _whitePointNumber;
			case EMPTY -> 0;
		};
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

	// TODO: Note sure about this method.
	//       Shoulint the board object using this
	//		 only need the number of checkers in the point to print it?
	public Stack<Checker> getCheckers(){
		return _checkers;
	}
}
