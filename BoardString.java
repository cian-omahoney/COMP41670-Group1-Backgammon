import java.util.*;

public class BoardString {
    private Table[] _tables;
    private HashMap<Checker, Bar> _barMap;
    public BoardString(Table[] tables,HashMap<Checker, Bar> barMap){
        _tables=tables;
        _barMap=barMap;
    }

    public int getPointMaxLength(int table1){
        int table1Length=_tables[table1].getPointMaxLength();
        int table2Length=_tables[table1+1].getPointMaxLength();
        int max=Math.max(table1Length,table2Length);
        return max;
    }

    public String getPoints(int row,int leftTable,int rightTable){
        String points="";
        String[]checkersOnTableRow=_tables[leftTable].getPointsRow(row);
        points+=getTableRow(checkersOnTableRow,Constants.LANES_PER_TABLE);

        points+="|"+getBarRow(row,5,Checker.RED);//FIXME Correct Paramaters need to be given to this function

        checkersOnTableRow=_tables[rightTable].getPointsRow(row);
        points+=getTableRow(checkersOnTableRow,Constants.LANES_PER_TABLE);
        points+="|\n";
        return points;
    }

    public String getArrows(int i,boolean pointDown){
        String arrows="";
        arrows+=getArrow(i,1,pointDown);
        arrows+=getBarRow(i,2,Checker.RED);   //FIXME Correct Paramaters need to be given to this function
        arrows+=getArrow(i,0,pointDown);
        arrows+="\n";
        return arrows;
    }

    public String getBorder(){
        return"=".repeat(5*((Constants.LANES_PER_TABLE*2)-1))+"\n";
    }

    public String getBarRow(int row,int numRows,Checker playerColour){
        String bar=" ".repeat(2);
        if(numRows-row<=_barMap.get(playerColour).getCheckerCount()){
            bar+=_barMap.get(playerColour).getResidentColour().toString();
        }
        else{
            bar+=" ";
        }
        bar+=" ".repeat(2);
        return bar;
    }

    public String getPointNumbers(boolean top, int player){
        String numbers="  ";
        int tableLeft=1;
        int tableRight=0;
        if (top){
            tableLeft=2;
            tableRight=3;
        }

        numbers+=getTableNums(tableLeft, player);
        numbers+="Bar   ";
        numbers+=getTableNums(tableRight, player);
        return numbers+"\n";
    }

    public String getTableNums(int table,int player){
        String tableNums="";
        for (int point=Constants.LANES_PER_TABLE-1;point>=0;point--){ //TODO Make function
            int num=_tables[table].getPointNumber(point,player);
            String numString=Integer.toString(num)+"  ";
            if (num<10)
            {
                numString="0"+numString;
            }
            tableNums+= numString; 
        }
        return tableNums;
    }

    public String getTableRow(String[] checkers, int size){
        String rowString="";
        for(int i=size-1;i>=0;i--){
            rowString+="| "+checkers[i]+" ";
        }
        return rowString;
    }

    public String getArrow(int layer,int leftSide,boolean pointDown){
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
