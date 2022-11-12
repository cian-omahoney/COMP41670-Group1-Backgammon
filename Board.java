import java.util.*;

public class Board {
    // TODO: We may need to split this class into smaller classes, its getting quit big :)

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
        _points[5].addCheckers(Checker.WHITE, 5);
    	_points[7].addCheckers(Checker.WHITE, 3);
    	_points[12].addCheckers(Checker.WHITE, 5);
    	_points[23].addCheckers(Checker.WHITE, 2);

        _points[18].addCheckers(Checker.RED, 5);
    	_points[16].addCheckers(Checker.RED, 3);
    	_points[11].addCheckers(Checker.RED, 5);
    	_points[0].addCheckers(Checker.RED, 2);
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

        sourcePoint = Bar.BAR_POINT_NUMBER;

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

    // TODO: WHAT If no valid moves possible??
    public List<ArrayList<Integer>> getValidPointMoves(Player activePlayer) {
        int sourcePoint;
        int destinationPoint;

        List<ArrayList<Integer>> validMoveList = new ArrayList<ArrayList<Integer>>();
        List<Integer> validMovePoints;

        for(Point currentPoint : _points) {
            // If the current point has at least one checker belonging to the active player:
            if(currentPoint.getResidentColour() == activePlayer.getColour()) {
                sourcePoint = currentPoint.getPointNumber(activePlayer.getColour());

                validMovePoints = new ArrayList<>();
                validMovePoints.add(sourcePoint);
                destinationPoint = sourcePoint;

                for(int nextDiceValue : activePlayer.getAvailableMoves()) {
                    destinationPoint = isMoveValid(destinationPoint, nextDiceValue, activePlayer.getColour());

                    if(destinationPoint > 0) {
                        validMovePoints.add(destinationPoint);
                        validMoveList.add(new ArrayList<>(validMovePoints));
                    }
                }

                validMovePoints = new ArrayList<>();
                validMovePoints.add(sourcePoint);
                if(activePlayer.getAvailableMoves().size() == 2 && !activePlayer.getAvailableMoves().get(0).equals(activePlayer.getAvailableMoves().get(1))) {
                    destinationPoint = sourcePoint;
                    for(int i = 1; i>=0; i--) {
                        destinationPoint = isMoveValid(destinationPoint, activePlayer.getAvailableMoves().get(i), activePlayer.getColour());

                        if(destinationPoint > 0) {
                            validMovePoints.add(destinationPoint);
                            validMoveList.add(new ArrayList<>(validMovePoints));
                        }
                    }
                }

            }
        }
        return validMoveList;
    }



    // TODO: SHould make source index a constant.
    public void moveChecker(List<Integer> moveSequence, Player activePlayer) {
        List <Integer> moveIndex = new ArrayList<>();

        for(Integer movePosition : moveSequence) {
            if(activePlayer.getColour() == Checker.RED) {
                moveIndex.add(Point.MAXIMUM_PIP_NUMBER - movePosition);
            }
            else {
                moveIndex.add(movePosition - 1);
            }
        }
        if(moveIndex.get(0) < 0 || moveIndex.get(0) > Point.MAXIMUM_PIP_NUMBER-1) {
            _barMap.get(activePlayer.getColour()).removeChecker();
        }
        else {
            _points[moveIndex.get(0)].removeChecker();
        }

        for(int i=1; i<moveIndex.size(); i++) {
            if(_points[moveIndex.get(i)].getCheckerCount() == 1 && !_points[moveIndex.get(i)].getResidentColour().equals(activePlayer.getColour())) {
                _barMap.get(_points[moveIndex.get(i)].getResidentColour()).addCheckers();
                _points[moveIndex.get(i)].removeChecker();
            }
        }
        _points[moveIndex.get(moveIndex.size()-1)].addCheckers(activePlayer.getColour());
    }

    public int getPipCount(Player player) {
        int pipCount = 0;
        for(Point currentPoint : _points) {
            if(currentPoint.getResidentColour() == player.getColour()) {
                pipCount += currentPoint.getPointNumber(player.getColour()) * currentPoint.getCheckerCount();
            }
        }
        return pipCount;
    }

    public String toString(int player){
        String board="";
        board+=getPointNumbers(true,player);
        board+=getBorder();

        //Get Top Table
        int length=getPointMaxLength(2);
        for (int i=0; i<length;i++){
            board+=getPoints(i,2,3);
        }

        for (int i=0; i<2;i++){
            board+=getArrows(i,true);
        }

        for (int i=0;i<3;i++){  //FIXME - Bar can be of variable length depending on # checkers in it
            board+=" ".repeat(4*Constants.LANES_PER_TABLE)+"|"+getBarRow(i,3,Checker.RED)+"|\n"; //FIXME Correct Paramaters need to be given to this function
        }

        //Print Bottom Table
        length=getPointMaxLength(0);
        for(int i=1;i>=0;i--){
            board+=getArrows(i,false);
        }

        for (int i=length-1; i>=0;i--){
            board+=getPoints(i,1,0);
        }
        board+=getBorder();
        board+=getPointNumbers(false,player);

        return board;
    }

    private int getPointMaxLength(int table1){
        int table1Length=_tables[table1].getPointMaxLength();
        int table2Length=_tables[table1+1].getPointMaxLength();
        int max=Math.max(table1Length,table2Length);
        return max;
    }

    private String getPoints(int row,int leftTable,int rightTable){
        String points="";
        String[]checkersOnTableRow=_tables[leftTable].getPointsRow(row);
        points+=getTableRow(checkersOnTableRow,Constants.LANES_PER_TABLE);

        points+="|"+getBarRow(row,5,Checker.RED);//FIXME Correct Paramaters need to be given to this function

        checkersOnTableRow=_tables[rightTable].getPointsRow(row);
        points+=getTableRow(checkersOnTableRow,Constants.LANES_PER_TABLE);
        points+="|\n";
        return points;
    }

    private String getArrows(int i,boolean pointDown){
        String arrows="";
        arrows+=getArrow(i,1,pointDown);
        arrows+=getBarRow(i,2,Checker.RED);   //FIXME Correct Paramaters need to be given to this function
        arrows+=getArrow(i,0,pointDown);
        arrows+="\n";
        return arrows;
    }

    private String getBorder(){
        return"=".repeat(5*((Constants.LANES_PER_TABLE*2)-1))+"\n";
    }

    private String getBarRow(int row,int numRows,Checker playerColour){
        String bar=" ".repeat(2);
        if(numRows-row<=_barMap.get(playerColour).getCheckerCount()){
            bar+=_barMap.get(playerColour).getResidentColour();
        }
        else{
            bar+=" ";
        }
        bar+=" ".repeat(2);
        return bar;
    }

    private String getPointNumbers(boolean top, int player){
        String numbers="  ";
        int tableLeft=1;
        int tableRight=0;
        if (top){
            tableLeft=2;
            tableRight=3;
        }
        for (int point=0;point<Constants.LANES_PER_TABLE;point++){
            numbers+=Integer.toString(_tables[tableLeft].getPointNumber(point,player))+"  ";    //FIXME pad single digits
        }
        numbers+="Bar   ";
        for (int point=0;point<Constants.LANES_PER_TABLE;point++){
            numbers+=Integer.toString(_tables[tableRight].getPointNumber(point,player))+"  ";
        }
        return numbers+"\n";
    }

    private String getTableRow(String[] checkers, int size){
        String rowString="";
        for(int i=size-1;i>=0;i--){
            rowString+="| "+checkers[i]+" ";
        }
        return rowString;
    }

    private String getArrow(int layer,int leftSide,boolean pointDown){
        String left="\\";
        String right="/";
        String arrow="";
        int layers=2;

        if (!pointDown)
        {
            left="/";
            right="\\";
        }

        arrow=" ".repeat(layer)+left+" ".repeat((layers)-(layer*2))+right+" ".repeat(layer);
        return "|".repeat(1-leftSide)+arrow.repeat(Constants.LANES_PER_TABLE)+"|".repeat(leftSide);
    }

    public boolean barEmpty(Player activePlayer) {
        return _barMap.get(activePlayer.getColour()).isEmpty();
    }
}
