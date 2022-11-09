import java.util.*;

public class UI{
	private static final String CLEAR_SCREEN 		= "\033[H\033[2J";
	public static final String  CLEAR_COLOURS 		= "\033[0m";
	private static final String YELLOW_TEXT_COLOUR 	= "\033[1;33m";
	private static final String CYAN_TEXT_COLOUR 	= "\033[1;36m";
	public static final String WHITE_CHECKER_COLOUR = "\033[0;39m";
	public static final String RED_CHECKER_COLOUR   = "\033[1;31m";
	private static final String DASH_LINE = YELLOW_TEXT_COLOUR + "=".repeat(82) + CLEAR_COLOURS;


	private Scanner _userInput;
	private Command _command;

    public UI(){
    	_userInput = new Scanner(System.in);
    	printIntro();
    }
    
    public void getPlayerNames(Player redPlayer, Player whitePlayer) {
		do {
			System.out.print("Enter Red Checker Player Name:\t\t");
			redPlayer.setName(getLine());
			System.out.print("Enter White Checker Player Name:\t");
			whitePlayer.setName(getLine());
			if(redPlayer.getName().toLowerCase().equals(whitePlayer.getName().toLowerCase())) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tPlayer names must be different!");
				System.out.print(CLEAR_COLOURS);
			}
		}while(redPlayer.getName().toLowerCase().equals(whitePlayer.getName().toLowerCase()));
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
		    	System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tThis command is invalid! Try again.");
		    	System.out.print(CLEAR_COLOURS);
			}
		}
		while (!validCommandEntered);
		return _command;
    }
    
    public String getLine() {
    	return _userInput.nextLine().trim();
    }
    
    public void printFirstPlayerChosen(Player player) {
    	System.out.println(CYAN_TEXT_COLOUR);
    	System.out.println("\tThe player to go first is selected randomly...");
    	System.out.println("\t" + player.getName() + " is selected to go first.");
    	System.out.println(CLEAR_COLOURS);
    }
    
    public void printQuit() {
    	System.out.println(DASH_LINE);
    	System.out.println("\t\tYou quit. Game Over.");
    	System.out.println(DASH_LINE);
    }

    public void printDice(Player player){
    	System.out.print(CYAN_TEXT_COLOUR);
		System.out.print("\tResults: ");
		for(int diceRoll : player.getAvailableMoves()) {
			System.out.printf("[%d] ", diceRoll);
		}
		System.out.println();
    	System.out.print(CLEAR_COLOURS);
    }

    private void printIntro(){
		System.out.print(CLEAR_SCREEN);
        System.out.flush();
    	System.out.println(DASH_LINE);
        System.out.println("\tWelcome to Backgammon");
    	System.out.println(DASH_LINE);
    }

	public void printValidMoves(List<ArrayList<Integer>> validMoveList){
		System.out.print(CYAN_TEXT_COLOUR);
		System.out.printf("\t%d Valid Moves Possible:\n", validMoveList.size());
		for(List<Integer> moveSequence : validMoveList) {
			System.out.printf("\t>> Move checker from point %2d ", moveSequence.get(0));
			for(int destinationPoint : moveSequence.subList(1, moveSequence.size())) {
				System.out.printf("--> point %2d ", destinationPoint);
			}
			System.out.println();
		}
		System.out.print(CLEAR_COLOURS);
	}


    public void printHelp(){   //TODO write help
    	System.out.print(CYAN_TEXT_COLOUR);
        System.out.println("\t>> Enter 'QUIT' to quit game.");
        System.out.println("\t>> Enter 'ROLL' to roll dice.");
        System.out.println("\t>> Enter 'HELP' for help.");
    	System.out.print(CLEAR_COLOURS);
    }

    public void printBoard(Board board, Player redPlayer, Player whitePlayer){
		System.out.print(CLEAR_SCREEN);
        System.out.flush();
        System.out.println(DASH_LINE);
        System.out.printf("Player Red: %s\t\t\tPlayer White: %s\n", redPlayer.getName(), whitePlayer.getName());
        System.out.println(DASH_LINE);

        String boardString=board.toString();
        System.out.println(boardString);
    }

	public void closeUserInput() {
		_userInput.close();
	}
}
