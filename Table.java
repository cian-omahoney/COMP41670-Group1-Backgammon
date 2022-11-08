public class Table {
    private Point[] _points;
    private int tableNumber;

    public Table(int number){
        this._points = new Point[Constants.LANES_PER_TABLE];
        tableNumber=number;
    	for(int i=0; i<Constants.LANES_PER_TABLE; i++) {
    		_points[i] = new Point(i+(tableNumber*Constants.LANES_PER_TABLE)); 
    	}
    	//setupCheckersInitial();
        return;
    }

    public String[] getPointsRow(int row){
        String[] checkers = new String[Constants.LANES_PER_TABLE];
        for (int i=0;i<Constants.LANES_PER_TABLE;i++){
            checkers[i]=" ";
            if (_points[i].getCheckerCount()>row){
                checkers[i]=_points[i].getResidentColour().getSymbol();
            }
        }
        return checkers;
    }

    public void addCheckers(int point,Checker checkerColour, int quantity){ //Point here refers to local point numbers eg 1-6
        _points[point].addCheckers(checkerColour, quantity);
    }

    public int getPointMaxLength(){
        int currMax=1;  //Print 1 space by default if the table is empty  
        for (Point point : _points){
            currMax=Math.max(currMax, point.getCheckerCount());
        }
        return currMax;
    }

    public int getPointNumber(){    //FIXME - Return the requested point number
        return _points[1].getRedPointNumber();
    }
}
