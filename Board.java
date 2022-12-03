// Team 1 Backgammon Project
import java.util.*;

/**
 * Class representing the board. This class deals with the valid moves that can be made given a set of dice rolls
 * It also holds each of the points and tables that make up the Board.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
*/
public class Board {
    // Pip number assigned to bear off area.
    public static final int BEAR_OFF_PIP_NUMBER = 0;
    // Pip number assigned to bar.
    public static final int BAR_PIP_NUMBER = 25;
    public static final int BACKGAMMONED_MULTIPLIER = 3;
    public static final int GAMMONED_MULTIPLIER = 2;

    // Each board is made up of points, tables, bars and bear-off areas.
    private Point[] _points;
    private Table[] _tables;
    private HashMap<Checker, Bar> _barMap;
    private int[] _bearOff;

    public Board() {
        this._points = new Point[Point.MAXIMUM_PIP_NUMBER];
        this._tables = new Table[4];
        this._barMap = new HashMap<>();
        this._bearOff = new int[2];

        _bearOff[0]=0;
        _bearOff[1]=0;

        _barMap.put(Checker.RED, new Bar(Checker.RED));
        _barMap.put(Checker.WHITE, new Bar(Checker.WHITE));

        for(int i=0; i<Point.MAXIMUM_PIP_NUMBER; i++) {
            _points[i] = new Point(i);
        }

        for(int i=0; i<4; i++) {
            _tables[i] = new Table(i, this._points);
        }

        setupCheckersInitial();
    }

    /**
     * Setup checkers on board in their initial configuration.
     */
    private void setupCheckersInitial() {
        _points[5].addCheckers(Checker.WHITE, 5);
    	_points[7].addCheckers(Checker.WHITE, 3);
    	_points[12].addCheckers(Checker.WHITE, 5);
    	_points[23].addCheckers(Checker.WHITE, 2);

        _points[18].addCheckers(Checker.RED, 5);
    	_points[16].addCheckers(Checker.RED, 3);
    	_points[11].addCheckers(Checker.RED, 5);
        _points[0].addCheckers(Checker.RED, 2);
    }

    // Calculate match score, implementing doubling, gammon and backgammon rules.
    public int getMatchScore(Player playerA, Player playerB, DoublingCube doublingCube) {
        int matchScore = doublingCube.getDoublingCubeValue();
        if(isBackgammoned(playerA, playerB)) {
            matchScore *= BACKGAMMONED_MULTIPLIER;
        }
        else if(isGammoned()) {
            matchScore *= GAMMONED_MULTIPLIER;
        }
        return matchScore;
    }

    // Determine if game ends in gammon:
    public boolean isGammoned() {
        return _bearOff[0] == 0 || _bearOff[1] == 0;
    }

    // Determine if game ends in backgammon:
    public boolean isBackgammoned(Player playerA, Player playerB) {
        boolean isBackgammoned = false;
        Player losingPlayer = getPipCount(playerA) == 0 ? playerB: playerA;
        if(isGammoned()) {
            if(!isBarEmpty(losingPlayer)) {
                isBackgammoned = true;
            }
            if(maximumCheckerPoint(losingPlayer) >= Point.BACKGAMMONED_POINT_CUTOFF) {
                isBackgammoned = true;
            }
        }
        return isBackgammoned;
    }

    public boolean isMatchOver(Player playerA, Player playerB, DoublingCube doublingCube) {
        return (getPipCount(playerA) == 0 || getPipCount(playerB) == 0 || doublingCube.isDoublingRefused());
    }

    public Player getWinner(Player playerA, Player playerB, Player activePlayer) {
        Player winner = activePlayer;
        if(getPipCount(playerA) == 0) {
            winner = playerA;
        }
        else if(getPipCount(playerB) == 0) {
            winner = playerB;
        }
        return winner;
    }

    public boolean isBarEmpty(Player activePlayer) {
        return _barMap.get(activePlayer.getColour()).isEmpty();
    }

    // Determine if active player is allowed to bear off checkers.
    // This is only possible if all that players pip are in the home
    // table.
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

    // Convert point number to an index in the _points array.
    // This conversion depends on the active player colour.
    private int convertPointNumberToIndex(int pointNumber, Player activePlayer) {
        int pointIndex = pointNumber - 1;
        if(activePlayer.getColour() == Checker.RED) {
            pointIndex = Point.MAXIMUM_PIP_NUMBER - pointNumber;
        }
        return pointIndex;
    }

    /**
     * Determine if there is a valid move possible for the active player from the sourcePoint given a
     * dive value of rollValue.
     * @param sourcePoint
     * @param rollValue
     * @param activePlayer
     * @return The destination point if a move is valid. -1 if a valid move is not possible.
     */
    private int isMoveValid(int sourcePoint, int rollValue, Player activePlayer) {
        int destinationPoint = sourcePoint - rollValue;
        int destinationIndex = convertPointNumberToIndex(destinationPoint, activePlayer);

        if(destinationPoint > 0) {
            // If the destination point contains active player checkers, or
            // if the destination point is empty, or
            // if the destination point contains only one of the other players checkers,
            // then a valid move is possible.  Otherwise a valid move is not possible:
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

    // Get all valid moves from the bar, if such moves exist.
    // Return a list of array lists containing the move sequences which are possible.
    public List<ArrayList<Integer>> getValidBarMoves(Player activePlayer) {
        int sourcePoint;
        int destinationPoint;
        int previousDiceValue = 0;
        List<ArrayList<Integer>> validMoveList = new ArrayList<>();
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

    // Determine all valid move sequences possible for the active player.
    // Return all possible move sequences as a list of array lists.
    public List<ArrayList<Integer>> getValidMoves(Player activePlayer) {
        int sourcePoint;
        int destinationPoint;

        int validMovesUsingLargerDice = 0;
        int validMovesUsingSmallerDice = 0;
        int indexOfOnlyValidMoveUsingLargerDice = 0;
        int indexOfOnlyValidMoveUsingSmallerDice = 0;
        int previousDiceValue = 0;
        int maximumCheckerLocation;

        List<ArrayList<Integer>> validMoveList_SmallerDiceFirst = new ArrayList<>();
        List<ArrayList<Integer>> validMoveList_LargerDiceFirst = new ArrayList<>();

        List<ArrayList<Integer>> validBearOffMoveList_LargerDiceFirst = new ArrayList<>();
        List<ArrayList<Integer>> validBearOffMoveList_SmallerDiceFirst = new ArrayList<>();

        List<ArrayList<Integer>> validMoveList = new ArrayList<>();
        List<Integer> validMovePoints;

        // Determine if a double move has been rolled:
        boolean isDoubleMove = !(activePlayer.getAvailableMoves().size() == 2 && !activePlayer.getAvailableMoves().get(0).equals(activePlayer.getAvailableMoves().get(1)));

        // Determine all possible valid move sequences, excluding those this end with the checker being beared-off:
        for(Point currentPoint : _points) {
            // If the current point has at least one checker belonging to the active player:
            if(currentPoint.getResidentColour() == activePlayer.getColour()) {
                sourcePoint = currentPoint.getPointNumber(activePlayer);
                destinationPoint = sourcePoint;

                // Check moves with larger dice or double dice value first:
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

                // Check moves smaller Dice First:
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

        // Get all valid move sequences which end with the checker being beared-off
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

    // Move a checker according to the sequence of moves in moveSequence.
    public void moveChecker(List<Integer> moveSequence, Player activePlayer) {
        List <Integer> moveIndex = new ArrayList<>();

        if(moveSequence.size() > 0) {
            // Convert the moveSequence to a sequence of indexes for _points().
            for(Integer movePoint : moveSequence) {
                moveIndex.add(convertPointNumberToIndex(movePoint, activePlayer));
            }

            // If the first step in the sequence begins at the bar:
            if(moveSequence.get(0) == BAR_PIP_NUMBER) {
                _barMap.get(activePlayer.getColour()).removeChecker();
            }
            else {
                _points[moveIndex.get(0)].removeChecker();
            }

            // If the final step in the move sequence ends in the bear off area:
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

    // Determine the checker with the maximum pip number.
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


    // Convert the board to a string so that it can be printed out by the UI
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
