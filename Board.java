import java.util.*;

public class Board {
    private Point[] _points;
    
    public Board() {
    	this._points = new Point[Point.MAXIMUM_PIP_NUMBER];
    	for(int i=0; i<Point.MAXIMUM_PIP_NUMBER; i++) {
    		_points[i] = new Point(i); 
    	}
    	setupCheckersInitial();
    }

    private void setupCheckersInitial() {
    	_points[5].addCheckers(Checker.WHITE, 5);
    	_points[7].addCheckers(Checker.WHITE, 3);
    	_points[12].addCheckers(Checker.WHITE, 5);
    	_points[23].addCheckers(Checker.WHITE, 2);

        _points[0].addCheckers(Checker.RED, 2);
        _points[11].addCheckers(Checker.RED, 5);
        _points[16].addCheckers(Checker.RED, 3);
    	_points[18].addCheckers(Checker.RED, 5);
    }

    public void getValidMoves(Player activePlayer) {
        int sourcePoint;
        int destinationPoint;
        int availableMoveIndex = 0;
        int nextDiceValue;
        for(Point currentPoint : _points) {
            if(currentPoint.getResidentColour() == activePlayer.getColour()) {
                sourcePoint = currentPoint.getPointNumber(activePlayer.getColour());
                for(int i=0; i<4; i++) {
                    nextDiceValue = activePlayer.getAvailableMoves()[availableMoveIndex];
                    destinationPoint = isMoveValid(sourcePoint, nextDiceValue, (), activePlayer.getColour());
                }
            }
        }
    }


    // TODO: change so only one return statement
    private int isMoveValid(int sourcePoint, int rollValue, Checker playerColour) {
        int destinationPoint = sourcePoint - rollValue;

        int destinationIndex = destinationPoint - 1;
        if(playerColour == Checker.RED) {
            destinationIndex = Point.MAXIMUM_PIP_NUMBER - destinationPoint;
        }

        if(destinationPoint > 0) {
            if (_points[destinationIndex].getResidentColour() == playerColour || _points[destinationIndex].getResidentColour() == Checker.EMPTY) {
                System.out.printf("Valid move from point %d to point %d\n", sourcePoint, destinationPoint);
                return destinationPoint;
            } else {
                if (_points[destinationIndex].getCheckerCount() == 1) {
                    System.out.printf("Valid move from point %d to point %d\n", sourcePoint, destinationPoint);
                    return destinationPoint;
                }
            }
        }
        return -1;
    }


    // TODO: I changed the _point indexing from [1:24] to [0:23].  It was leading to
    //       some problems.
    @Override
    public String toString(){
        Stack<Checker>[] boardStack= new Stack[Point.MAXIMUM_PIP_NUMBER+1];    //FIXME - List of stacks?
        for (int points=1;points<=Point.MAXIMUM_PIP_NUMBER;points++){
            boardStack[points]=_points[points].getCheckers();
        }
        int topMaxRows=getPointMaxLength(boardStack, 1, Point.MAXIMUM_PIP_NUMBER/2);
        int bottomMaxRows=getPointMaxLength(boardStack, (Point.MAXIMUM_PIP_NUMBER/2)+1, Point.MAXIMUM_PIP_NUMBER);

        String board="";
        
        board+=getBorder();

        //Get Top Table
        for (int i=0; i<5;i++){
            board+=getPoints(this, i,true);
        }

        for (int i=0; i<2;i++){
            board+=getArrows(i,true);
        }

        for (int i=0;i<3;i++){
            board+=" ".repeat(4*Constants.LANES_PER_TABLE)+"|     |\n";
        }

        //Print Bottom Table 
        for(int i=1;i>=0;i--){
            board+=getArrows(i,false);
        }

        for (int i=4; i>=0;i--){
            board+=getPoints(this, i,false);
        }
        board+=getBorder();

        return board;
    }

    private int getPointMaxLength(Stack<Checker>[] boardStack,int start,int end){
        int max=-1;
        for(int i=start;i<=end;i++){
            int size=boardStack[i].size();
            max=size>max? size: max;
        }
        return max;
    }

    private String getPoints(Board board,int row,boolean topTable){
        String points=""; 

        //String[] checkersOnTableRow=
        String[] checkersOnTableRow=Arrays.copyOfRange(board.toStringBottomCheckers()[row], 0, Constants.LANES_PER_TABLE);
        points+=getTableRow(checkersOnTableRow);
        points+="|"+" ".repeat(5);
        checkersOnTableRow=Arrays.copyOfRange(board.toStringBottomCheckers()[row], Constants.LANES_PER_TABLE, 2*Constants.LANES_PER_TABLE);
        points+=getTableRow(checkersOnTableRow);
        points+="|\n";
        return points;
    }

    private String getArrows(int i,boolean pointDown){
        String arrows="";
        arrows+=getArrow(i,1,pointDown);
        arrows+=" ".repeat(5);
        arrows+=getArrow(i,0,pointDown);
        arrows+="\n";
        return arrows;
    }

    private String getBorder(){
        return"=".repeat(5*((Constants.LANES_PER_TABLE*2)-1))+"\n";
    }

    private String getTableRow(String[] checkers){
        String row="";
        for(int i=0;i<Constants.LANES_PER_TABLE;i++){
            row+="| "+checkers[i]+" ";
        }
        return row;
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


    public String[][] toStringTopCheckers() {
    	return Board.setupInitialCheckers(Checker.RED.getSymbol(), Checker.WHITE.getSymbol(),true);
    }

    public String[][] toStringBottomCheckers() {
    	return Board.setupInitialCheckers(Checker.WHITE.getSymbol(), Checker.RED.getSymbol(),false);
    }

    public static String[][] setupInitialCheckers(String homeChecker, String awayChecker, boolean rotateUp)
    {
        String [][] checkers=new String[5][Constants.LANES_PER_TABLE*2];
        for (int j=0;j<5;j++){
            for (int i=0;i<Constants.LANES_PER_TABLE*2;i++){
                String checker=" ";
                switch (i){
                    case 0 -> checker=awayChecker;
                    case 4-> {
                        if(j<3){checker=homeChecker;}
                    }
                    case 6-> checker=homeChecker;
                    case 11-> {
                        if (j<2){checker=awayChecker;}
                    }
                }
                checkers[j][i]=checker;
            }
        }
        return checkers;
    }
}
