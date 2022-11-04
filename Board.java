public class Board {
    private Point[] _points; 
    
    public Board() {
    	this._points = new Point[Point.MAXIMUM_PIP_NUMBER+1];
    	for(int i=1; i<=Point.MAXIMUM_PIP_NUMBER; i++) {
    		_points[i] = new Point(i); 
    	}
    	setupCheckersInitial();
        return;
    }
    
    private void setupCheckersInitial() {
    	_points[6].addCheckers(Checker.WHITE, 6);
    	_points[8].addCheckers(Checker.WHITE, 3);
    	_points[13].addCheckers(Checker.WHITE, 2);
    	_points[24].addCheckers(Checker.WHITE, 5);
    	
    	_points[20].addCheckers(Checker.RED, 6);
    	_points[18].addCheckers(Checker.RED, 3);
    	_points[12].addCheckers(Checker.RED, 5);
    	_points[1].addCheckers(Checker.RED, 2);
    }

    public String toString(){

        return "test";
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
