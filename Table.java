public class Table {
    private Point[] _points;
    private int tableNumber;

    public Table(int number, Point[] points){
        this._points = new Point[Constants.LANES_PER_TABLE];
        tableNumber = number;
    	for(int i=0; i<Constants.LANES_PER_TABLE; i++) {
            int j=i;
            if (tableNumber>1){
                j=(Constants.LANES_PER_TABLE-1)-i;
            }
    		this._points[j] = points[i+(tableNumber*Constants.LANES_PER_TABLE)]; //FIXME does this order them correctly?
    	}
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

    public int getPointMaxLength(){
        int currMax=1;  //Print 1 space by default if the table is empty  
        for (Point point : _points){
            currMax=Math.max(currMax, point.getCheckerCount());
        }
        return currMax;
    }

    public int getPointNumber(int point,int player){    //FIXME - Return the requested point number
        return _points[point].getPointNumber(player);
    }
}
