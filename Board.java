import java.util.*;

public class Board {
    public static final int BEAR_OFF_PIP_NUMBER = 0;
    public static final int BAR_PIP_NUMBER = 25;

    private Point[] _points;
    private Table[] _tables;
    private HashMap<Checker, Bar> _barMap;
    private int _doublingCube;
    private Checker _doublingCubeOwner;
    private int[] _bearOff;

    public Board() {
        this._points = new Point[Point.MAXIMUM_PIP_NUMBER];
        this._tables = new Table[4];
        this._barMap = new HashMap<>();
        this._doublingCube = 1;
        this._doublingCubeOwner = Checker.EMPTY;
        this._bearOff = new int[2];

        for(int i=0; i<Point.MAXIMUM_PIP_NUMBER; i++) {
            _points[i] = new Point(i);
        }

        for(int i=0; i<4; i++) {
            _tables[i] = new Table(i, this._points);
        }

        _bearOff[0]=0;
        _bearOff[1]=0;

        _barMap.put(Checker.RED, new Bar(Checker.RED));
        _barMap.put(Checker.WHITE, new Bar(Checker.WHITE));
        setupCheckersInitial();
    }


    private void setupCheckersInitial() {
        // THIS IS THE CORRECT INITIAL SET UP
        _points[5].addCheckers(Checker.WHITE, 5);
    	_points[7].addCheckers(Checker.WHITE, 3);
    	_points[12].addCheckers(Checker.WHITE, 5);
    	_points[23].addCheckers(Checker.WHITE, 2);

        _points[18].addCheckers(Checker.RED, 5);
    	_points[16].addCheckers(Checker.RED, 3);
    	_points[11].addCheckers(Checker.RED, 5);
    	_points[0].addCheckers(Checker.RED, 2);

        //_points[0].addCheckers(Checker.WHITE, 1);
        //_points[23].addCheckers(Checker.RED, 1);
    }

    public int getDoublingCube() {
        return _doublingCube;
    }

    public boolean isDoublingCubeOwner(Player activePlayer) {
        boolean isDoublingCubeOwner = false;
        if(_doublingCubeOwner == activePlayer.getColour()){
            isDoublingCubeOwner = true;
        }
        else if(_doublingCubeOwner == Checker.EMPTY) {
            isDoublingCubeOwner = true;
            _doublingCubeOwner = activePlayer.getColour();
        }
        return isDoublingCubeOwner;
    }

    public String doublingCubeToString(Player player) {
        String doublingCubeString = "[" + _doublingCube + "]";;
        if(player.getColour() != _doublingCubeOwner) {
            doublingCubeString = "---";
        }
        return doublingCubeString;
    }

    public String doublingCubeToString() {
        String doublingCubeString = "[" + _doublingCube + "]";;
        if(_doublingCubeOwner != Checker.EMPTY) {
            doublingCubeString = "   ";
        }
        return doublingCubeString;
    }

    public boolean isMatchOver(Player playerA, Player playerB) {
        return (getPipCount(playerA) == 0 || getPipCount(playerB) == 0);
    }
    public boolean isBarEmpty(Player activePlayer) {
        return _barMap.get(activePlayer.getColour()).isEmpty();
    }

    public boolean isBearOff(Player activePlayer) {
        boolean isBearOff = true;
        if(!isBarEmpty(activePlayer)) {
            isBearOff = false;
        }
        for (Point currentPoint : _points) {
            if(currentPoint.getResidentColour() == activePlayer.getColour()) {
                if(currentPoint.getPointNumber(activePlayer) > Point.MAXIMUM_BEAROFF_PIP_NUMBER) {
                    isBearOff = false;
                }
            }
        }
        return isBearOff;
    }

    public int getPipCount(Player player) {
        int pipCount = 0;
        for(Point currentPoint : _points) {
            if(currentPoint.getResidentColour() == player.getColour()) {
                pipCount += currentPoint.getPointNumber(player) * currentPoint.getCheckerCount();
            }
        }
        pipCount += _barMap.get(player.getColour()).getCheckerCount()*Board.BAR_PIP_NUMBER;
        return pipCount;
    }

    private int convertPointNumberToIndex(int pointNumber, Player activePlayer) {
        int pointIndex = pointNumber - 1;
        if(activePlayer.getColour() == Checker.RED) {
            pointIndex = Point.MAXIMUM_PIP_NUMBER - pointNumber;
        }
        return pointIndex;
    }

    private int isMoveValid(int sourcePoint, int rollValue, Player activePlayer) {
        int destinationPoint = sourcePoint - rollValue;
        int destinationIndex = convertPointNumberToIndex(destinationPoint, activePlayer);

        if(destinationPoint > 0) {
            if (   _points[destinationIndex].getResidentColour() != activePlayer.getColour()
                    && _points[destinationIndex].getResidentColour() != Checker.EMPTY
                    && _points[destinationIndex].getCheckerCount()   != 1) {
                destinationPoint = -1;
            }
        }
        else {
            destinationPoint = -1;
        }
        return destinationPoint;
    }

    public List<ArrayList<Integer>> getValidBarMoves(Player activePlayer) {
        int sourcePoint;
        int destinationPoint;
        int previousDiceValue = 0;
        List<ArrayList<Integer>> validMoveList = new ArrayList<ArrayList<Integer>>();
        List<Integer> validMovePoints;

        sourcePoint = BAR_PIP_NUMBER;

        for (int nextDiceValue : activePlayer.getAvailableMoves()) {
            validMovePoints = new ArrayList<>();
            validMovePoints.add(sourcePoint);
            if(nextDiceValue != previousDiceValue) {
                destinationPoint = isMoveValid(sourcePoint, nextDiceValue, activePlayer);

                if (destinationPoint > 0) {
                    validMovePoints.add(destinationPoint);
                    validMoveList.add(new ArrayList<>(validMovePoints));
                }
                previousDiceValue = nextDiceValue;
            }
        }
        return validMoveList;
    }


    public List<ArrayList<Integer>> getValidMoves(Player activePlayer) {
        int sourcePoint;
        int destinationPoint;

        int validMovesUsingLargerDice = 0;
        int validMovesUsingSmallerDice = 0;
        int indexOfOnlyValidMoveUsingLargerDice = 0;
        int indexOfOnlyValidMoveUsingSmallerDice = 0;
        int previousDiceValue = 0;
        int maximumCheckerLocation = 0;

        List<ArrayList<Integer>> validMoveList_SmallerDiceFirst = new ArrayList<>();
        List<ArrayList<Integer>> validMoveList_LargerDiceFirst = new ArrayList<>();

        List<ArrayList<Integer>> validBearOffMoveList_LargerDiceFirst = new ArrayList<>();
        List<ArrayList<Integer>> validBearOffMoveList_SmallerDiceFirst = new ArrayList<>();

        List<ArrayList<Integer>> validMoveList = new ArrayList<>();
        List<Integer> validMovePoints;

        boolean isDoubleMove = !(activePlayer.getAvailableMoves().size() == 2 && !activePlayer.getAvailableMoves().get(0).equals(activePlayer.getAvailableMoves().get(1)));

        for(Point currentPoint : _points) {
            // If the current point has at least one checker belonging to the active player:
            if(currentPoint.getResidentColour() == activePlayer.getColour()) {
                sourcePoint = currentPoint.getPointNumber(activePlayer);
                destinationPoint = sourcePoint;

                // Larger Dice First:
                validMovePoints = new ArrayList<>();
                validMovePoints.add(sourcePoint);
                for(int nextDiceValue : activePlayer.getAvailableMoves()) {
                    destinationPoint = isMoveValid(destinationPoint, nextDiceValue, activePlayer);

                    if(destinationPoint > 0) {
                        validMovePoints.add(destinationPoint);
                        if(validMovePoints.size() >= 2) {
                            validMovesUsingLargerDice++;
                        }
                        if(validMovePoints.size() >= 3) {
                            validMovesUsingSmallerDice++;
                            indexOfOnlyValidMoveUsingSmallerDice = validMoveList_LargerDiceFirst.size();
                        }
                        validMoveList_LargerDiceFirst.add(new ArrayList<>(validMovePoints));
                    }
                }

                // Smaller Dice First:
                validMovePoints = new ArrayList<>();
                validMovePoints.add(sourcePoint);
                if(!isDoubleMove) {
                    destinationPoint = sourcePoint;
                    for(int i = 1; i>=0; i--) {
                        destinationPoint = isMoveValid(destinationPoint, activePlayer.getAvailableMoves().get(i), activePlayer);

                        if(destinationPoint > 0) {
                            validMovePoints.add(destinationPoint);
                            if(validMovePoints.size() >= 2) {
                                validMovesUsingSmallerDice++;
                            }
                            if(validMovePoints.size() >= 3) {
                                validMovesUsingLargerDice++;
                                indexOfOnlyValidMoveUsingLargerDice = validMoveList_SmallerDiceFirst.size();
                            }
                            validMoveList_SmallerDiceFirst.add(new ArrayList<>(validMovePoints));
                        }
                    }
                }
            }
        }

        // BEAR OFF BEGINS =================================================
        if(isBearOff(activePlayer)) {
            maximumCheckerLocation = maximumCheckerPoint(activePlayer);

            if (maximumCheckerLocation <= Collections.max(activePlayer.getAvailableMoves())) {
                if (maximumCheckerLocation > 0) {
                    validMovePoints = new ArrayList<>();
                    validMovePoints.add(maximumCheckerLocation);
                    validMovePoints.add(BEAR_OFF_PIP_NUMBER);
                    validBearOffMoveList_LargerDiceFirst.add(new ArrayList<>(validMovePoints));

                    validMovesUsingLargerDice++;
                }
            }

            for (int nextDiceValue : activePlayer.getAvailableMoves()) {
                if (nextDiceValue != previousDiceValue) {
                    if (nextDiceValue != maximumCheckerLocation) {
                        int checkerIndex = convertPointNumberToIndex(nextDiceValue, activePlayer);

                        // If there is a checker belonging to activePlayer at the dice value point:
                        if (_points[checkerIndex].getResidentColour() == activePlayer.getColour()) {
                            validMovePoints = new ArrayList<>();
                            validMovePoints.add(nextDiceValue);
                            validMovePoints.add(BEAR_OFF_PIP_NUMBER);

                            if(nextDiceValue == Collections.max(activePlayer.getAvailableMoves())) {
                                validBearOffMoveList_LargerDiceFirst.add(new ArrayList<>(validMovePoints));
                                validMovesUsingLargerDice++;
                            }
                            else {
                                validBearOffMoveList_SmallerDiceFirst.add(new ArrayList<>(validMovePoints));
                                validMovesUsingSmallerDice++;
                            }
                        }
                        previousDiceValue = nextDiceValue;
                    }
                }
            }
        }
        // BEAR OFF END =================================================


        // Decide which of all possible moves is valid.
        // This depends on their order as we must use both dices if it is possible:

        if(validMovesUsingLargerDice == 1 && validMovesUsingSmallerDice > 1 && !isDoubleMove) {
            if(validMoveList_LargerDiceFirst.size() == 1) {
                validMoveList.addAll(validMoveList_LargerDiceFirst);
            }
            else if(validBearOffMoveList_LargerDiceFirst.size() == 1) {
                validMoveList.addAll(validBearOffMoveList_LargerDiceFirst);
            }
            else {
                validMoveList.add(validMoveList_SmallerDiceFirst.get(indexOfOnlyValidMoveUsingLargerDice));
            }
        }
        else if(validMovesUsingSmallerDice == 1 && validMovesUsingLargerDice > 1 && !isDoubleMove) {
            if(validMoveList_SmallerDiceFirst.size() == 1) {
                validMoveList.addAll(validMoveList_SmallerDiceFirst);
            }
            else if(validBearOffMoveList_SmallerDiceFirst.size() == 1) {
                validMoveList.addAll(validBearOffMoveList_SmallerDiceFirst);
            }
            else {
                validMoveList.add(validMoveList_LargerDiceFirst.get(indexOfOnlyValidMoveUsingSmallerDice));
            }
        }
        else if (validMovesUsingSmallerDice == 1 && validMovesUsingLargerDice == 1 && !isDoubleMove) {
            if(validMoveList_LargerDiceFirst.size() == 1) {
                validMoveList.addAll(validMoveList_LargerDiceFirst);
            }
            else if(validBearOffMoveList_LargerDiceFirst.size() == 1) {
                validMoveList.addAll(validBearOffMoveList_LargerDiceFirst);
            }
            else {
                validMoveList.addAll(validMoveList_SmallerDiceFirst);
            }
        }
        else {
            validMoveList.addAll(validMoveList_LargerDiceFirst);
            validMoveList.addAll(validMoveList_SmallerDiceFirst);
            validMoveList.addAll(validBearOffMoveList_LargerDiceFirst);
            validMoveList.addAll(validBearOffMoveList_SmallerDiceFirst);
        }

        return validMoveList;
    }

    // TODO: SHould make source index a constant.
    public void moveChecker(List<Integer> moveSequence, Player activePlayer) {
        List <Integer> moveIndex = new ArrayList<>();

        if(moveSequence.size() > 0) {
            for(Integer movePoint : moveSequence) {
                moveIndex.add(convertPointNumberToIndex(movePoint, activePlayer));
            }
            if(moveSequence.get(0) == BAR_PIP_NUMBER) {
                _barMap.get(activePlayer.getColour()).removeChecker();
            }
            else {
                _points[moveIndex.get(0)].removeChecker();
            }

            if(moveSequence.get(moveSequence.size()-1) == BEAR_OFF_PIP_NUMBER) {
                _bearOff[activePlayer.getNumber()]++;
            }
            else {
                for (int i = 1; i < moveIndex.size(); i++) {
                    if (_points[moveIndex.get(i)].getCheckerCount() == 1 && !_points[moveIndex.get(i)].getResidentColour().equals(activePlayer.getColour())) {
                        _barMap.get(_points[moveIndex.get(i)].getResidentColour()).addCheckers();
                        _points[moveIndex.get(i)].removeChecker();
                    }
                }
                _points[moveIndex.get(moveIndex.size() - 1)].addCheckers(activePlayer.getColour());
            }

        }
    }

    private int maximumCheckerPoint(Player activePlayer) {
        int maximumCheckerLocation = 0;
        for (Point currentPoint : _points) {
            if (currentPoint.getPointNumber(activePlayer) <= Point.MAXIMUM_BEAROFF_PIP_NUMBER) {
                if (currentPoint.getResidentColour() == activePlayer.getColour()) {
                    maximumCheckerLocation = Math.max(currentPoint.getPointNumber(activePlayer), maximumCheckerLocation);
                }
            }
        }
        return maximumCheckerLocation;
    }


    public String toString(int player){
        BoardString boardToString= new BoardString(_tables,_barMap,_bearOff);
        String board="";

        board+=boardToString.getPointNumbers(true,player);
        board+=boardToString.getBorder();
        
        int topLength=boardToString.getPointMaxLength(2);
        int bottomLength=boardToString.getPointMaxLength(0);

        //Get Top Table
        board+=boardToString.getPoints(topLength,true);
        board+=boardToString.getArrows(topLength,true);

        board+=boardToString.centreBar(topLength,bottomLength);

        //Print Bottom Table
        board+=boardToString.getArrows(bottomLength,false);            
        board+=boardToString.getPoints(bottomLength,false);

        board+=boardToString.getBorder();
        board+=boardToString.getPointNumbers(false,player);

        return board;
    }


}
