// Team 1 Backgammon Project
import java.util.*;

/**
 * Class that converts the data stored in classes into a string representation of the
 * current state of the board, that can be printed by the UI.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
public class BoardString {
    public static final int POINT_WIDTH = 3;    //How many character spaces each point will take up
    public static final int ARROW_LAYERS =(POINT_WIDTH+1)/2;

    private Table[] _tables;
    private HashMap<Checker, Bar> _barMap;
    private int[] _bearOff;

    public BoardString(Table[] tables,HashMap<Checker, Bar> barMap,int[] bearOff){  //Get a snapshot of the current board
        _tables=tables;
        _barMap=barMap;
        _bearOff=bearOff;
    }

    // Get the length of the longest point on this half of the board
    public int getPointMaxLength(int table1){
        int table1Length=_tables[table1].getPointMaxLength();
        int table2Length=_tables[table1+1].getPointMaxLength();
        return Math.max(table1Length,table2Length);
    }

    /**
     * Return all of the points held on one half of the board, in string representation
     * @param length Length of the points that need to be converted to strings
     * @param top Whether this is the top or bottom half of the board
     *   */ 
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
            points+=getTableRow(checkersOnTableRow);

            points+="|"+getBarRow(barLayer,barColour);

            checkersOnTableRow=_tables[rightTable].getPointsRow(row);
            points+=getTableRow(checkersOnTableRow);
            points+="|\n";
        }
        return points;
    }

    /**
     * 
     * @param pointLength The length of the points before this arrow - used for deciding placement of checkers inside the bar
     * @param pointDown Whether these arrows point up or down - related to whether this is the top or bottom half of the board
     * @return The string representation of the arrows at the end of each point
     */
    public String getArrows(int pointLength,boolean pointDown){
        String arrows="";

        Checker barColour=Checker.WHITE;
        if(!pointDown){

            barColour=Checker.RED;
        }
        int totalBarLength=_barMap.get(barColour).getCheckerCount();
        int standAloneBar=totalBarLength-(ARROW_LAYERS+pointLength);
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
            arrows+=getBarRow(barLayer,barColour);  //Put the bar at the middle of these arrows
            arrows+=getArrow(row,0,pointDown);

            if (i==0 && pointDown){
                arrows+="       Bear off Area"; //Print the bear off area to the right of the arrows
            }
            else if (row==1){
                arrows+="     ===================";
            }
            arrows+="\n";
        }
        return arrows;
    }

    //Return a string that will represent a boarder for the table
    public String getBorder(){
        return"=".repeat(5*((Table.NUMBER_LANES*2)-1))+"\n";
    }

    //Return the bar at the centre of the board
    public String centreBar(int topLength,int bottomLength){
        String bar="";
        int whiteLength=_barMap.get(Checker.WHITE).getCheckerCount();
        int redLength=_barMap.get(Checker.RED).getCheckerCount();
        String blankSpace=" ".repeat(4*Table.NUMBER_LANES);
    
        bar+=getCentreBarSection(blankSpace,Checker.WHITE,Checker.RED,ARROW_LAYERS+topLength,whiteLength,1);
        bar+=blankSpace+"|"+" ".repeat(5)+"|"+blankSpace+"     |"+" ".repeat(17)+"|\n";
        bar+=getCentreBarSection(blankSpace, Checker.RED, Checker.WHITE, 0, redLength-(bottomLength+ARROW_LAYERS),0);
        return bar;
    }

    private String getCentreBarSection(String blankSpace, Checker thisChecker,Checker otherChecker,int offset,int size,int playerNumber){
        String bar="";
        int i=1;
        do{
            bar+=blankSpace+"|";
            bar+=getBarRow(i,thisChecker)+"|";

            if (i==1){
                bar+=blankSpace+"     | "+otherChecker.getSymbol().repeat(_bearOff[playerNumber])+" ".repeat(15-_bearOff[playerNumber])+" |";
            }
            bar+="\n";
            i++;
        }while (i+offset<size);
        return bar;
    }

    // Return string representing this layer of the bar
    private String getBarRow(int row,Checker playerColour){
        String bar=" ".repeat(2);
        if(row>=0 && row<=_barMap.get(playerColour).getCheckerCount()){ //If there is an element in the bar that should be included at this layer, include it as part of this string
            bar+=_barMap.get(playerColour).getResidentColour().getSymbol();
        }
        else{
            bar+=" ";
        }
        bar+=" ".repeat(2);
        return bar;
    }

    // Return string representing the point numbers depending on which players turn it is
    public String getPointNumbers(boolean top, int player){
        String numbers="  ";
        //The relevant tables if printing the bottom half of the board
        int tableLeft=1;
        int tableRight=0;
        if (top){
            //The relevant tables if printing the top half of the board
            tableLeft=2;
            tableRight=3;
        }

        numbers+=getTableNums(tableLeft, player);
        numbers+="Bar   ";
        numbers+=getTableNums(tableRight, player);
        return numbers+"\n";
    }

    // String representing the number of each table depending on which player is playing
    private String getTableNums(int table,int player){
        String tableNums="";
        for (int point=Table.NUMBER_LANES-1;point>=0;point--){
            int num=_tables[table].getPointNumber(point,player);
            String numString = num +"  ";
            if (num<10)
            {
                numString="0"+numString;
            }
            tableNums+= numString; 
        }
        return tableNums;
    }

    private String getTableRow(String[] checkers){
        String rowString="";
        for(int i = Table.NUMBER_LANES -1; i>=0; i--){
            rowString+="| "+checkers[i]+" ";
        }
        return rowString;
    }

    private String getArrow(int layer,int leftSide,boolean pointDown){
        String left="\\";
        String right="/";
        String arrow;

        if (!pointDown)
        {
            left="/";
            right="\\";
        }

        arrow=" ".repeat(layer)+left+" ".repeat((ARROW_LAYERS)-(layer*2))+right+" ".repeat(layer);
        return "|".repeat(1-leftSide)+arrow.repeat(Table.NUMBER_LANES)+"|".repeat(leftSide);
    }
}
