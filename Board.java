import java.util.*;

public class Board {
    // TODO: We may need to split this class into smaller classes, its getting quite big :)  - Agreed
    public static final int BEAR_OFF_PIP_NUMBER = 0;
    public static final int BAR_PIP_NUMBER = 25;

    private Point[] _points;
    private Table[] _tables;
    private HashMap<Checker, Bar> _barMap;

    public Board() {
        this._points = new Point[Point.MAXIMUM_PIP_NUMBER];
        this._tables = new Table[4];
        this._barMap = new HashMap<>();

        for(int i=0; i<Point.MAXIMUM_PIP_NUMBER; i++) {
            _points[i] = new Point(i);
        }

        for(int i=0; i<4; i++) {
            _tables[i] = new Table(i, this._points);
        }

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

    //BAR UI TESTING
    //_barMap.get(Checker.WHITE).addCheckers(Checker.WHITE, 1);
    //_barMap.get(Checker.RED).addCheckers(Checker.RED, 1);


        // THIS SET UP IS ONLY FOR TESTING:
        // Endgame test.
    //    _points[11].addCheckers(Checker.WHITE, 1);
    //    _points[10].addCheckers(Checker.WHITE, 1);
    //    _points[6].addCheckers(Checker.RED, 2);

    //    _points[4].addCheckers(Checker.RED, 2);
    //    _points[3].addCheckers(Checker.RED, 2);
    //    _points[2].addCheckers(Checker.RED, 2);
    //    _points[1].addCheckers(Checker.RED, 2);

        // TEST 1:  FROM SPEC, FINAL EXAMPLE
//        _points[5].addCheckers(Checker.WHITE, 1);
//        _points[4].addCheckers(Checker.WHITE, 1);
//        _points[0].addCheckers(Checker.RED, 2);

//        // TEST 2: What if I can only play one number? example from website
//        _points[23].addCheckers(Checker.WHITE, 1);
//        _points[20].addCheckers(Checker.RED, 2);
//        _points[19].addCheckers(Checker.RED, 1);
//        _points[18].addCheckers(Checker.RED, 2);
//        _points[14].addCheckers(Checker.WHITE, 4);
//        _points[13].addCheckers(Checker.RED, 2);
//        _points[12].addCheckers(Checker.WHITE, 5);
//        _points[10].addCheckers(Checker.RED, 2);
//        _points[8].addCheckers(Checker.RED, 2);
//        _points[6].addCheckers(Checker.RED, 2);
//        _points[5].addCheckers(Checker.WHITE, 5);
//        _points[1].addCheckers(Checker.RED, 2);

        // TEST 3: Can I play one number in such a way as to avoid playing the other? example 1 from website
//        _points[23].addCheckers(Checker.WHITE, 2);
//        _points[22].addCheckers(Checker.RED, 2);
//        _points[21].addCheckers(Checker.RED, 2);
//        _points[19].addCheckers(Checker.RED, 2);
//        _points[18].addCheckers(Checker.RED, 3);
//        _points[12].addCheckers(Checker.WHITE, 5);
//        _points[8].addCheckers(Checker.RED, 2);
//        _points[6].addCheckers(Checker.WHITE, 3);
//        _points[5].addCheckers(Checker.WHITE, 5);
//        _points[2].addCheckers(Checker.RED, 2);
//        _points[1].addCheckers(Checker.RED, 2);

        // TEST 4: Can I play one number in such a way as to avoid playing the other? example 2 from website
//        _points[5].addCheckers(Checker.WHITE, 1);
//        _points[4].addCheckers(Checker.WHITE, 2);
//        _points[2].addCheckers(Checker.WHITE, 2);
//        _points[0].addCheckers(Checker.RED, 2);

        // TEST 5: Test winner
        // _points[0].addCheckers(Checker.WHITE, 1);
        // _points[23].addCheckers(Checker.RED, 1);
    }

    public boolean isGameOver(Player playerA, Player playerB) {
        return (getPipCount(playerA) == 0 || getPipCount(playerB) == 0);
    }
    public boolean isBarEmpty(Player activePlayer) {
        return _barMap.get(activePlayer.getColour()).isEmpty();
    }

    public boolean isBearOff(Player activePlayer) {
        boolean isBearOff = true;
        if(!_barMap.get(activePlayer.getColour()).isEmpty()) {
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
        return pipCount;
    }

    private int isMoveValid(int sourcePoint, int rollValue, Checker playerColour) {
        int destinationPoint = sourcePoint - rollValue;

        int destinationIndex = destinationPoint - 1;
        if(playerColour == Checker.RED) {
            destinationIndex = Point.MAXIMUM_PIP_NUMBER - destinationPoint;
        }

        if(destinationPoint > 0) {
            if (   _points[destinationIndex].getResidentColour() != playerColour
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
                destinationPoint = isMoveValid(sourcePoint, nextDiceValue, activePlayer.getColour());

                if (destinationPoint > 0) {
                    validMovePoints.add(destinationPoint);
                    validMoveList.add(new ArrayList<>(validMovePoints));
                }
                previousDiceValue = nextDiceValue;
            }
        }
        return validMoveList;
    }

//    public List<ArrayList<Integer>> getValidPointMoves(Player activePlayer) {
//        int sourcePoint;
//        int destinationPoint;
//
//        int validMovesUsingLargerDice = 0;
//        int validMovesUsingSmallerDice = 0;
//        int indexOfOnlyValidMoveUsingLargerDice = 0;
//        int indexOfOnlyValidMoveUsingSmallerDice = 0;
//
//        List<ArrayList<Integer>> validMoveList_SmallerDiceFirst = new ArrayList<>();
//        List<ArrayList<Integer>> validMoveList_LargerDiceFirst = new ArrayList<>();
//        List<ArrayList<Integer>> validMoveList = new ArrayList<>();
//        List<Integer> validMovePoints;
//
//        for(Point currentPoint : _points) {
//            // If the current point has at least one checker belonging to the active player:
//            if(currentPoint.getResidentColour() == activePlayer.getColour()) {
//                sourcePoint = currentPoint.getPointNumber(activePlayer);
//                destinationPoint = sourcePoint;
//
//                // Larger Dice First:
//                validMovePoints = new ArrayList<>();
//                validMovePoints.add(sourcePoint);
//                for(int nextDiceValue : activePlayer.getAvailableMoves()) {
//                    destinationPoint = isMoveValid(destinationPoint, nextDiceValue, activePlayer.getColour());
//
//                    if(destinationPoint > 0) {
//                        validMovePoints.add(destinationPoint);
//                        if(validMovePoints.size() >= 2) {
//                            validMovesUsingLargerDice++;
//                        }
//                        if(validMovePoints.size() >= 3) {
//                            validMovesUsingSmallerDice++;
//                            indexOfOnlyValidMoveUsingSmallerDice = validMoveList_LargerDiceFirst.size();
//                        }
//                        validMoveList_LargerDiceFirst.add(new ArrayList<>(validMovePoints));
//                    }
//                }
//
//                // Smaller Dice First:
//                validMovePoints = new ArrayList<>();
//                validMovePoints.add(sourcePoint);
//                if(activePlayer.getAvailableMoves().size() == 2 && !activePlayer.getAvailableMoves().get(0).equals(activePlayer.getAvailableMoves().get(1))) {
//                    destinationPoint = sourcePoint;
//                    for(int i = 1; i>=0; i--) {
//                        destinationPoint = isMoveValid(destinationPoint, activePlayer.getAvailableMoves().get(i), activePlayer.getColour());
//
//                        if(destinationPoint > 0) {
//                            validMovePoints.add(destinationPoint);
//                            if(validMovePoints.size() >= 2) {
//                                validMovesUsingSmallerDice++;
//                            }
//                            if(validMovePoints.size() >= 3) {
//                                validMovesUsingLargerDice++;
//                                indexOfOnlyValidMoveUsingLargerDice = validMoveList_SmallerDiceFirst.size();
//                            }
//                            validMoveList_SmallerDiceFirst.add(new ArrayList<>(validMovePoints));
//                        }
//                    }
//                }
//            }
//        }
//
//        if(validMovesUsingLargerDice == 1 && validMovesUsingSmallerDice > 1) {
//            if(validMoveList_LargerDiceFirst.size() == 1) {
//                validMoveList.addAll(validMoveList_LargerDiceFirst);
//            }
//            else {
//                validMoveList.add(validMoveList_SmallerDiceFirst.get(indexOfOnlyValidMoveUsingLargerDice));
//            }
//        }
//        else if(validMovesUsingSmallerDice == 1 && validMovesUsingLargerDice > 1) {
//            if(validMoveList_SmallerDiceFirst.size() == 1) {
//                validMoveList.addAll(validMoveList_SmallerDiceFirst);
//            }
//            else {
//                validMoveList.add(validMoveList_LargerDiceFirst.get(indexOfOnlyValidMoveUsingSmallerDice));
//            }
//        }
//        else if (validMovesUsingSmallerDice == 1 && validMovesUsingLargerDice == 1) {
//            if(validMoveList_LargerDiceFirst.size() == 1) {
//                validMoveList.addAll(validMoveList_LargerDiceFirst);
//            }
//            else {
//                validMoveList.addAll(validMoveList_SmallerDiceFirst);
//            }
//        }
//        else {
//            validMoveList.addAll(validMoveList_LargerDiceFirst);
//            validMoveList.addAll(validMoveList_SmallerDiceFirst);
//        }
//
//
//        System.out.printf("Smaller dice use: %d\n", validMovesUsingSmallerDice);
//        System.out.printf("Larger dice use: %d\n", validMovesUsingLargerDice);
//
//        return validMoveList;
//    }
//
//
//    public List<ArrayList<Integer>> getValidBearOffMoves(Player activePlayer) {
//        List<ArrayList<Integer>> validMoveList = new ArrayList<ArrayList<Integer>>();
//        List<Integer> validMovePoints;
//        int previousDiceValue = 0;
//
//        int maximumCheckerLocation = 0;
//        for (Point currentPoint : _points) {
//            if (currentPoint.getPointNumber(activePlayer) <= Point.MAXIMUM_BEAROFF_PIP_NUMBER) {
//                // Find maximum value checker point:
//                if(currentPoint.getResidentColour() == activePlayer.getColour())
//                {
//                    maximumCheckerLocation = Math.max(currentPoint.getPointNumber(activePlayer), maximumCheckerLocation);
//                }
//            }
//        }
//
//        if(maximumCheckerLocation < Collections.max(activePlayer.getAvailableMoves())) {
//            if(maximumCheckerLocation > 0) {
//                validMovePoints = new ArrayList<>();
//                validMovePoints.add(maximumCheckerLocation);
//                validMovePoints.add(BEAR_OFF_PIP_NUMBER);
//                validMoveList.add(new ArrayList<>(validMovePoints));
//            }
//        }
//
//        for(int nextDiceValue : activePlayer.getAvailableMoves()) {
//            if(nextDiceValue != previousDiceValue) {
//                if(nextDiceValue != maximumCheckerLocation) {
//                    // If there is a checker belonging to activePlayer at the dice value point:
//                    if(_points[nextDiceValue-1].getResidentColour() == activePlayer.getColour()) {
//                        validMovePoints = new ArrayList<>();
//                        validMovePoints.add(nextDiceValue);
//                        validMovePoints.add(BEAR_OFF_PIP_NUMBER);
//                        validMoveList.add(new ArrayList<>(validMovePoints));
//                    }
//                    previousDiceValue = nextDiceValue;
//                }
//            }
//        }
//
//        validMoveList.addAll(getValidPointMoves(activePlayer));
//        return validMoveList;
//    }

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

        // ALL NONE BEAR OFF VALID MOVES ===============================================

        for(Point currentPoint : _points) {
            // If the current point has at least one checker belonging to the active player:
            if(currentPoint.getResidentColour() == activePlayer.getColour()) {
                sourcePoint = currentPoint.getPointNumber(activePlayer);
                destinationPoint = sourcePoint;

                // Larger Dice First:
                validMovePoints = new ArrayList<>();
                validMovePoints.add(sourcePoint);
                for(int nextDiceValue : activePlayer.getAvailableMoves()) {
                    destinationPoint = isMoveValid(destinationPoint, nextDiceValue, activePlayer.getColour());

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
                if(activePlayer.getAvailableMoves().size() == 2 && !activePlayer.getAvailableMoves().get(0).equals(activePlayer.getAvailableMoves().get(1))) {
                    destinationPoint = sourcePoint;
                    for(int i = 1; i>=0; i--) {
                        destinationPoint = isMoveValid(destinationPoint, activePlayer.getAvailableMoves().get(i), activePlayer.getColour());

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
        // END OF ALL NONE BEAR OFF VALID MOVES ===============================================

        // BEAR OFF BEGINS =================================================
        if (isBarEmpty(activePlayer)) {
            for (Point currentPoint : _points) {
                if (currentPoint.getPointNumber(activePlayer) <= Point.MAXIMUM_BEAROFF_PIP_NUMBER) {
                    // Find maximum value checker point:
                    if (currentPoint.getResidentColour() == activePlayer.getColour()) {
                        maximumCheckerLocation = Math.max(currentPoint.getPointNumber(activePlayer), maximumCheckerLocation);
                    }
                }
            }

            if (maximumCheckerLocation < Collections.max(activePlayer.getAvailableMoves())) {
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
                        // If there is a checker belonging to activePlayer at the dice value point:
                        if (_points[nextDiceValue - 1].getResidentColour() == activePlayer.getColour()) {
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

        if(validMovesUsingLargerDice == 1 && validMovesUsingSmallerDice > 1) {
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
        else if(validMovesUsingSmallerDice == 1 && validMovesUsingLargerDice > 1) {
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
        else if (validMovesUsingSmallerDice == 1 && validMovesUsingLargerDice == 1) {
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
            for(Integer movePosition : moveSequence) {
                if(activePlayer.getColour() == Checker.RED) {
                    moveIndex.add(Point.MAXIMUM_PIP_NUMBER - movePosition);
                }
                else {
                    moveIndex.add(movePosition - 1);
                }
            }
            if(moveSequence.get(0) == BAR_PIP_NUMBER) {
                _barMap.get(activePlayer.getColour()).removeChecker();
            }
            else {
                _points[moveIndex.get(0)].removeChecker();
            }

            if(moveSequence.get(moveSequence.size()-1) == BEAR_OFF_PIP_NUMBER) {
                // ADD TO BEAR OFF PILE HERE!!
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


    public String toString(int player){
        BoardString boardToString= new BoardString(_tables,_barMap);
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
