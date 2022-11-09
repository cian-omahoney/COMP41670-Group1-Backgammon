import java.util.*;

public class Board {
    // TODO: We may need to split this class into smaller classes, its getting quit big :)

    private Point[] _points;
    private Table[] _tables;
    private Bar[] _bar;

    public Board() {
        this._points = new Point[Point.MAXIMUM_PIP_NUMBER];
        this._tables = new Table[4];
        this._bar = new Bar[2];

        for(int i=0; i<Point.MAXIMUM_PIP_NUMBER; i++) {
            _points[i] = new Point(i);
        }

        for(int i=0; i<4; i++) {
            _tables[i] = new Table(i, this._points);
        }

        for(int i=0;i<2;i++)
        {
            _bar[i]=new Bar(i+1);
        }
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

    public List<ArrayList<Integer>> getValidMoves(Player activePlayer) {
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


    public String toString(){
        String board="";
        board+=getPointNumbers(true,1);
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
            board+=" ".repeat(4*Constants.LANES_PER_TABLE)+"|"+getBarRow(i,3,0)+"|\n"; //FIXME Correct Paramaters need to be given to this function
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

        points+="|"+getBarRow(row,5,0);//FIXME Correct Paramaters need to be given to this function

        checkersOnTableRow=_tables[rightTable].getPointsRow(row);
        points+=getTableRow(checkersOnTableRow,Constants.LANES_PER_TABLE);
        points+="|\n";
        return points;
    }

    private String getArrows(int i,boolean pointDown){
        String arrows="";
        arrows+=getArrow(i,1,pointDown);
        arrows+=getBarRow(i,2,0);   //FIXME Correct Paramaters need to be given to this function
        arrows+=getArrow(i,0,pointDown);
        arrows+="\n";
        return arrows;
    }

    private String getBorder(){
        return"=".repeat(5*((Constants.LANES_PER_TABLE*2)-1))+"\n";
    }

    private String getBarRow(int row,int numRows,int player){
        String bar=" ".repeat(2);
        if(numRows-row<=_bar[player].getCheckerCount()){
            bar+=_bar[player].getResidentColour();
        }
        else{
            bar+=" ";
        }
        bar+=" ".repeat(2);
        return bar;
    }

    private String getPointNumbers(boolean top, int player){
        String numbers="  ";
        for (int point=5;point>=0;point--){
            numbers+=Integer.toString(_tables[2].getPointNumber(point,player))+"  ";
        }
        numbers+="Bar   ";
        for (int point=5;point>=0;point--){
            numbers+=Integer.toString(_tables[3].getPointNumber(point,player))+"  ";
        }
        return numbers+"\n";  //TODO Unfinished
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
}
