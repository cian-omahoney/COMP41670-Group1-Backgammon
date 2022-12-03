// Team 1 Backgammon Project
/**
 * This class is a representation of the tables that make up the full board. This representation is only used for
 * printing the UI as it is helpful to breakup the board into these sections, however the gameplay behind these
 * uses just the points as it is more helpful to consider the points as continuous 1-24, rather than having them broken
 * up into tables.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
public class Table {
    public static final int NUMBER_LANES=6;

    private Point[] _points;
    private int tableNumber;

    public Table(int number, Point[] points){
        this._points = new Point[NUMBER_LANES];
        tableNumber = number;
    	for(int i=0; i<NUMBER_LANES; i++) {
            int j=i;
            if (tableNumber>1){
                j=(NUMBER_LANES-1)-i;
            }
    		this._points[j] = points[i+(tableNumber*NUMBER_LANES)];
    	}
    }

    public String[] getPointsRow(int row){
        String[] checkers = new String[NUMBER_LANES];
        for (int i=0;i<NUMBER_LANES;i++){
            checkers[i]=" ";
            if (_points[i].getCheckerCount()>row){
                checkers[i]=_points[i].getResidentColour().getSymbol();
            }
        }
        return checkers;
    }

    public int getPointMaxLength(){
        int currMax=1;  //Print 1 space by default in each point if the entire table is empty  
        for (Point point : _points){
            currMax=Math.max(currMax, point.getCheckerCount());
        }
        return currMax;
    }

    public int getPointNumber(int point,int player){
        return _points[point].getPointNumber(player);
    }
}
