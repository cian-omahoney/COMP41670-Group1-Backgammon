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

    public String getPoints(int length,boolean top){
        int leftTable=2;
        int rightTable=3;
        if (!top){
            leftTable=1;
            rightTable=0;
        }
        String points="";
        for (int i=0; i<length;i++){
            int row=i;
            Checker player=Checker.WHITE;
            if (!top){
                row=(length-1)-i;
                player=Checker.RED;
            }
            String[]checkersOnTableRow=_tables[leftTable].getPointsRow(row);
            points+=getTableRow(checkersOnTableRow,Constants.LANES_PER_TABLE);

            points+="|"+getBarRow(row,length+2,player);

            checkersOnTableRow=_tables[rightTable].getPointsRow(row);
            points+=getTableRow(checkersOnTableRow,Constants.LANES_PER_TABLE);
            points+="|\n";
        }
        return points;
    }

    public String getArrows(int pointLength,int size,boolean pointDown){
        String arrows="";
        for (int i=0; i<2;i++){
            int row=i;
            Checker player=Checker.WHITE;
            if(!pointDown){
                row=(size-1)-i;
                player=Checker.RED;
            }
            arrows+=getArrow(row,1,pointDown);
            arrows+=getBarRow(row,pointLength+size,player);
            arrows+=getArrow(row,0,pointDown);
            arrows+="\n";
        }
        return arrows;
    }

    public String getBorder(){
        return"=".repeat(5*((Constants.LANES_PER_TABLE*2)-1))+"\n";
    }

    public String centreBar(int topLength,int bottomLength,int arrowSize){
        String bar="";
        int whiteLength=_barMap.get(Checker.WHITE).getCheckerCount();
        int redLength=_barMap.get(Checker.RED).getCheckerCount();
        String blankSpace=" ".repeat(4*Constants.LANES_PER_TABLE)+"|";

        int i=topLength+arrowSize;
        do{
            bar+=blankSpace;
            bar+=getBarRow(i,topLength+arrowSize,Checker.WHITE)+"|\n"; //FIXME Correct Paramaters need to be given to this function
            i++;
        }while (i<whiteLength);
            

        bar+=blankSpace+" ".repeat(5)+"|\n";

        i=0;
        do{
            bar+=blankSpace;
            bar+=getBarRow(i,bottomLength+arrowSize,Checker.RED)+"|\n"; //FIXME Correct Paramaters need to be given to this function
            i++;
        } while(i<redLength-(bottomLength+arrowSize));
       
        return bar;
    }

    private String getBarRow(int row,int numRows,Checker playerColour){
        String bar=" ".repeat(2);
        if(numRows-row<_barMap.get(playerColour).getCheckerCount()){
            bar+=_barMap.get(playerColour).getResidentColour().getSymbol();
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

    private String getTableNums(int table,int player){
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
