public class Board {
    private Table[] _tables;
    
    public Board() {
        _tables=new Table[4];
    	for(int i=0; i<4; i++) {
    		_tables[i] = new Table(i); 
    	}
    	setupCheckersInitial();
        return;
    }
    
    private void setupCheckersInitial() {
    	_tables[0].addCheckers(6, Checker.WHITE, 5);
        _tables[1].addCheckers(2, Checker.WHITE, 3);
        _tables[2].addCheckers(6, Checker.WHITE, 5);
        _tables[3].addCheckers(1, Checker.WHITE, 2);
        /*_points[6].addCheckers(Checker.WHITE, 6);
    	_points[8].addCheckers(Checker.WHITE, 3);
    	_points[13].addCheckers(Checker.WHITE, 2);
    	_points[24].addCheckers(Checker.WHITE, 5);*/
    	
        _tables[3].addCheckers(6, Checker.RED, 5);
        _tables[2].addCheckers(2, Checker.RED, 3);
        _tables[1].addCheckers(6, Checker.RED, 5);
        _tables[0].addCheckers(1, Checker.RED, 2);
    	
        /*_points[20].addCheckers(Checker.RED, 6);
    	_points[18].addCheckers(Checker.RED, 3);
    	_points[12].addCheckers(Checker.RED, 5);
    	_points[1].addCheckers(Checker.RED, 2);*/
    }

    public String toString(){
        String board="";
        
        board+=getBorder();

        //Get Top Table
        int length=getPointMaxLength(2);
        for (int i=0; i<length;i++){ //FIXME
            board+=getPoints(i,2,3);
        }

        for (int i=0; i<2;i++){
            board+=getArrows(i,true);
        }

        for (int i=0;i<3;i++){
            board+=" ".repeat(4*Constants.LANES_PER_TABLE)+"|     |\n";
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
        
        points+="|"+" ".repeat(5);

        checkersOnTableRow=_tables[rightTable].getPointsRow(row);
        points+=getTableRow(checkersOnTableRow,Constants.LANES_PER_TABLE);
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
