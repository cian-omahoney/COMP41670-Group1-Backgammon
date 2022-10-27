import java.util.Arrays;
import java.util.*;

public class UI{
	private static final String CLEAR_SCREEN 		= "\033[H\033[2J";
	
	private static final String CLEAR_COLOURS 		= "\033[0m";
	private static final String YELLOW_TEXT_COLOUR 	= "\033[1;33m";
	private static final String CYAN_TEXT_COLOUR 	= "\033[1;36m";
	
	private static final String DASH_LINE = YELLOW_TEXT_COLOUR + repeatChar('=', 82) + CLEAR_COLOURS;
	
	private Scanner _userInput;
	Command _command;
	Player _playerOne;
	Player _playerTwo;
	
    public UI(){
    	_userInput = new Scanner(System.in);
    	printIntro();
    }
    
    public Player getPlayerName(String playerNumber) {
    	System.out.print("Enter Player " + playerNumber + " Name: ");
		String userInput = getLine();
		
		switch(playerNumber) {
		case "One":
			_playerOne = new Player(userInput, 1);
			return _playerOne;
		case "Two":
			_playerTwo = new Player(userInput, 2);
			return _playerTwo;
		default:
			_playerOne = new Player(userInput, 1);
			return _playerOne;
		}
    }
    
    public Command getCommand(Player player) {
		boolean validCommandEntered = false;
		do {
			System.out.printf("Enter command %s: ", player.getName());
			String input = getLine();
			if (Command.isValid(input)) {
				_command = new Command(input);
				validCommandEntered = true;
			} else {
				System.out.println("\tThis command is invalid! Try again.");
			}
		}
		while (!validCommandEntered);
		return _command;
    }
    
    public String getLine() {
    	return _userInput.nextLine().trim();
    }
    
    public void printQuit() {
    	System.out.println(DASH_LINE);
    	System.out.println("\t\tYou quit. Game Over.");
    	System.out.println(DASH_LINE);
    }

    public String[][] setupCheckers(String homeChecker, String awayChecker,boolean rotateUp)
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

    public void displayDice(Player player){
    	System.out.print(CYAN_TEXT_COLOUR);
    	System.out.printf("\tResults: [%d] [%d]\n", player.getDiceRoll()[0], 
    												player.getDiceRoll()[1]);
    	System.out.print(CLEAR_COLOURS);
    }

    private void printTaskbar(){

    }

    private void printIntro(){
		System.out.print(CLEAR_SCREEN);
        System.out.flush();
    	System.out.println(DASH_LINE);
        System.out.println("\tWelcome to Backgammon");
    	System.out.println(DASH_LINE);
    }

    public void printHelp(){   //TODO write help
    	System.out.print(CYAN_TEXT_COLOUR);
        System.out.println("\t>> Enter 'QUIT' to quit game.");
        System.out.println("\t>> Enter 'ROLL' to roll dice.");
        System.out.println("\t>> Enter 'HELP' for help.");
    	System.out.print(CLEAR_COLOURS);
    }

    public void printBoard(Board board){
		System.out.print(CLEAR_SCREEN);
        System.out.flush();
        System.out.println(DASH_LINE);
        System.out.printf("Player 1: %s\t\t\tPlayer 2: %s\n", _playerOne.getName(), _playerTwo.getName());
        System.out.println(DASH_LINE);
        
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
    
	private static final String repeatChar(char repeatedChar, int length) {
	    char[] data = new char[length];
	    Arrays.fill(data, repeatedChar);
	    return new String(data);
	}
}
