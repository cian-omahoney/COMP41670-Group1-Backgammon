import java.util.*;

public class BoardString {
    public static final int POINT_WIDTH = 3;
    public static final int ARROW_LAYERS =(POINT_WIDTH+1)/2;

    private Table[] _tables;
    private HashMap<Checker, Bar> _barMap;
    private int[] _bearOff;

    public BoardString(Table[] tables,HashMap<Checker, Bar> barMap,int[] bearOff){
        _tables=tables;
        _barMap=barMap;
        _bearOff=bearOff;
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
        Checker barColour=Checker.WHITE;
        if (!top){
            leftTable=1;
            rightTable=0;
            barColour=Checker.RED;
        }
        int totalBarLength=_barMap.get(barColour).getCheckerCount();

        String points="";
        for (int i=0; i<length;i++){
            int row=i;

            int standAloneBar=totalBarLength-(ARROW_LAYERS+length);
            if (standAloneBar<1){
                standAloneBar=1;
            }

            int barLayer=totalBarLength-((length-row)+ARROW_LAYERS+standAloneBar);
            if (!top){
                row=(length-1)-i;
                barLayer=standAloneBar+ARROW_LAYERS+i+1;
            }
            String[]checkersOnTableRow=_tables[leftTable].getPointsRow(row);
            points+=getTableRow(checkersOnTableRow,Constants.LANES_PER_TABLE);

            points+="|"+getBarRow(barLayer,barColour);

            checkersOnTableRow=_tables[rightTable].getPointsRow(row);
            points+=getTableRow(checkersOnTableRow,Constants.LANES_PER_TABLE);
            points+="|\n";
        }
        return points;
    }

    public String getArrows(int pointLength,boolean pointDown){
        String arrows="";

        Checker barColour=Checker.WHITE;
        if(!pointDown){

            barColour=Checker.RED;
        }
        int totalBarLength=_barMap.get(barColour).getCheckerCount();
        int standAloneBar=totalBarLength-(ARROW_LAYERS+pointLength);    //TODO - pass this in?
        if (standAloneBar<1){
            standAloneBar=1;
        }
        for (int i=0; i<ARROW_LAYERS;i++){
            int row=i;
            int barLayer=standAloneBar+(ARROW_LAYERS-i);
            if(!pointDown){
                row=(ARROW_LAYERS-1)-i;
                barLayer=standAloneBar+i+1;
            }

            arrows+=getArrow(row,1,pointDown);
            arrows+=getBarRow(barLayer,barColour);
            arrows+=getArrow(row,0,pointDown);

            if (i==0 && pointDown){
                arrows+="       Bear off Area";
            }
            else if (row==1){
                arrows+="     ===================";
            }
            arrows+="\n";
        }
        return arrows;
    }

    public String getBorder(){
        return"=".repeat(5*((Constants.LANES_PER_TABLE*2)-1))+"\n";
    }

    public String centreBar(int topLength,int bottomLength){
        String bar="";
        int whiteLength=_barMap.get(Checker.WHITE).getCheckerCount();
        int redLength=_barMap.get(Checker.RED).getCheckerCount();
        String blankSpace=" ".repeat(4*Constants.LANES_PER_TABLE);

        int i=1;    //TODO These loops are the same - simplify?
        do{
            bar+=blankSpace+"|";
            bar+=getBarRow(i,Checker.WHITE)+"|";

            if (i==1){
                bar+=blankSpace+"     | "+Checker.RED.getSymbol().repeat(_bearOff[1])+" ".repeat(15-_bearOff[1])+" |";
            }
            bar+="\n";
            i++;
        }while (i+ARROW_LAYERS+topLength<whiteLength);
            

        bar+=blankSpace+"|"+" ".repeat(5)+"|"+blankSpace+"     |"+" ".repeat(17)+"|\n";

        i=1;
        do{
            bar+=blankSpace+"|";
            bar+=getBarRow(i,Checker.RED)+"|";

            if (i==1){
                bar+=blankSpace+"     | "+Checker.WHITE.getSymbol().repeat(_bearOff[0])+" ".repeat(15-_bearOff[0])+" |";
            }
            bar+="\n";
            i++;
        } while(i<redLength-(bottomLength+ARROW_LAYERS));
       
        return bar;
    }

    private String getBarRow(int row,Checker playerColour){
        String bar=" ".repeat(2);
        if(row>=0 && row<=_barMap.get(playerColour).getCheckerCount()){
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

        if (!pointDown)
        {
            left="/";
            right="\\";
        }

        arrow=" ".repeat(layer)+left+" ".repeat((ARROW_LAYERS)-(layer*2))+right+" ".repeat(layer);
        return "|".repeat(1-leftSide)+arrow.repeat(Constants.LANES_PER_TABLE)+"|".repeat(leftSide);
    }
}
