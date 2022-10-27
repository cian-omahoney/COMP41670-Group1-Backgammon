import java.util.Arrays;

public class UI{
    public UI(){
        String[][] topCheckers=setupCheckers("X", "O",true);
        String[][] bottomCheckers=setupCheckers("O", "X",false);
        Board board=new Board(topCheckers,bottomCheckers);
        printBoard(board);
    }

    private String[][] setupCheckers(String homeChecker, String awayChecker,boolean rotateUp)
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

    private void displayDice(){

    }

    private void printTaskbar(){

    }

    private void printIntro(){
        System.out.println("Welcome to Backgammon");
        printHelp();
    }

    private void printHelp(){   //TODO write help
        System.out.println("The Help");
    }

    public void printBoard(Board board){
        System.out.println("=".repeat(5*((Constants.LANES_PER_TABLE*2)-1)));
        for (int i=0; i<5;i++){
            String[] checkersOnTableRow=Arrays.copyOfRange(board.topCheckers()[i], 0, Constants.LANES_PER_TABLE);
            printTableRow(checkersOnTableRow);
            System.out.print("|"+" ".repeat(5));
            checkersOnTableRow=Arrays.copyOfRange(board.topCheckers()[i], Constants.LANES_PER_TABLE, 2*Constants.LANES_PER_TABLE);
            printTableRow(checkersOnTableRow);
            System.out.println("|");
        }
        for (int i=0; i<2;i++){
            printArrow(i,1,true);
            System.out.print(" ".repeat(5));
            printArrow(i,0,true);
            System.out.println("");
        }

        System.out.println(" ".repeat(4*Constants.LANES_PER_TABLE)+"|     |");
        System.out.println(" ".repeat(4*Constants.LANES_PER_TABLE)+"|     |");
        System.out.println(" ".repeat(4*Constants.LANES_PER_TABLE)+"|     |");

        for(int i=1;i>=0;i--){
            printArrow(i,1,false);
            System.out.print(" ".repeat(5));
            printArrow(i,0,false);
            System.out.println("");
        }
        for (int i=4; i>=0;i--){
            String[] checkersOnTableRow=Arrays.copyOfRange(board.bottomCheckers()[i], 0, Constants.LANES_PER_TABLE);
            printTableRow(checkersOnTableRow);
            System.out.print("|"+" ".repeat(5));
            checkersOnTableRow=Arrays.copyOfRange(board.bottomCheckers()[i], Constants.LANES_PER_TABLE, 2*Constants.LANES_PER_TABLE);
            printTableRow(checkersOnTableRow);
            System.out.println("|");
        }
        System.out.println("=".repeat(5*((Constants.LANES_PER_TABLE*2)-1)));
        System.out.println("\nCiarán [6] [5]");
    }

    private void printTableRow(String[] checkers){
        for(int i=0;i<Constants.LANES_PER_TABLE;i++){
            System.out.print("| "+checkers[i]+" ");
        }
    }

    private void printArrow(int layer,int leftSide,boolean pointDown){
        String left="\\";
        String right="/";
        if (!pointDown)
        {
            left="/";
            right="\\";
        }
        String arrow="";
        int layers=2;
        arrow=" ".repeat(layer)+left+" ".repeat((layers)-(layer*2))+right+" ".repeat(layer);
        System.out.print("|".repeat(1-leftSide)+arrow.repeat(Constants.LANES_PER_TABLE)+"|".repeat(leftSide));
    }

}
