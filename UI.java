import java.util.*;

public class UI{
	private static final String CLEAR_SCREEN 		= "\033[H\033[2J";
	public static final String  CLEAR_COLOURS 		= "\033[0m";
	private static final String YELLOW_TEXT_COLOUR 	= "\033[1;33m";
	private static final String CYAN_TEXT_COLOUR 	= "\033[1;36m";
	private static final String MAGENTA_TEXT_COLOUR = "\033[1;35m";
	private static final String UNDERLINE_TEXT      = "\033[4m";
	public static final String WHITE_CHECKER_COLOUR = "\033[0;39m";
	public static final String RED_CHECKER_COLOUR   = "\033[1;31m";
	private static final String DASH_LINE = YELLOW_TEXT_COLOUR + "=".repeat(82) + CLEAR_COLOURS;
	private static final int ALPHABET_SIZE = 26;
	private static final String MOVE_REGEX = "[A-Z]*";

	private Scanner _userInput;
	private Command _command;

    public UI(){
    	_userInput = new Scanner(System.in);
    	printIntro();
    }
    
    public void getPlayerNames(Player redPlayer, Player whitePlayer) {
		Boolean validNames = false;
		do {
			System.out.print("Enter Red Checker Player Name:\t\t");
			redPlayer.setName(getLine());
			System.out.print("Enter White Checker Player Name:\t");
			whitePlayer.setName(getLine());
			validNames = true;
			if(redPlayer.getName().equals("") || whitePlayer.getName().equals("")) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tBoth players must have non-empty names!");
				validNames = false;
				System.out.print(CLEAR_COLOURS);
			}
			else if(redPlayer.getName().toLowerCase().equals(whitePlayer.getName().toLowerCase())) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tPlayer names must be different!");
				validNames = false;
				System.out.print(CLEAR_COLOURS);
			}
		}while(!validNames);
    }
    
    public Command getCommand(Player player) {
		System.out.printf(">> Enter command %s:  ", player.getName());
		String input = getLine();
		_command = new Command(input);
		if (_command.isInvalid()) {
			System.out.print(CYAN_TEXT_COLOUR);
			System.out.println("\tThis command is invalid! Try \"HINT\".");
			System.out.print("\t>> Press enter to try another command...");
			getLine();
			System.out.print(CLEAR_COLOURS);
		}
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

    public void printDice(Player player){
    	System.out.println(CYAN_TEXT_COLOUR);
		System.out.print("\tAvailable Dice: ");
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

	public List<Integer> selectValidMove(List<ArrayList<Integer>> validMoveList){
		boolean validMove = false;
		int moveIndex = 0;
		String input;

		System.out.println(CYAN_TEXT_COLOUR);
		System.out.printf("\t%s%d Valid Moves Possible:\n", UNDERLINE_TEXT, validMoveList.size());
		System.out.print(CLEAR_COLOURS);

		System.out.print(CYAN_TEXT_COLOUR);
		for(List<Integer> moveSequence : validMoveList) {
			if(moveSequence.get(0) == Bar.BAR_POINT_NUMBER) {
				System.out.printf("\t[%s] : Move checker from Bar ", convertIndexToLabel(validMoveList.indexOf(moveSequence)));
			}
			else {
				System.out.printf("\t[%s] : Move checker from point %2d ", convertIndexToLabel(validMoveList.indexOf(moveSequence)), moveSequence.get(0));
			}

			for(int destinationPoint : moveSequence.subList(1, moveSequence.size())) {
				System.out.printf("--> point %2d ", destinationPoint);
			}
			System.out.println();
		}

		System.out.println(CLEAR_COLOURS);
		do {
			System.out.printf(">> Enter letter code for desired move:  ");
			input = getLine();
			if(input.toUpperCase().matches(MOVE_REGEX)) {
				moveIndex = convertLabelToIndex(input.toUpperCase());
				if(moveIndex >= 0 && moveIndex < validMoveList.size()) {
					validMove = true;
				}
			}

			if(validMove == false) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tThis is not a valid letter code!.");
				System.out.print("\t>> Press enter to try another letter code...");
				getLine();
				System.out.print(CLEAR_COLOURS);
			}
		}while(validMove == false);

		return validMoveList.get(moveIndex);
	}

	private String convertIndexToLabel(int index) {
		String label = "";
		if(index < ALPHABET_SIZE) {
			label += (char) ('A' + index % ALPHABET_SIZE);
		}
		else {
			label += (char) ('A' + ((int)(index/ALPHABET_SIZE)-1) % ALPHABET_SIZE);
			label += (char) ('A' + index % ALPHABET_SIZE);
		}
		return label;
	}

	private int convertLabelToIndex (String label) {
		int index = 0;

		for(int i = label.length()-1; i>=0; i--) {
			index += (int)(label.charAt(i)-'A'+1)*Math.pow(ALPHABET_SIZE, (label.length() - 1 - i));
		}
		index--;
		return index;
	}

	public void printMoves(List<Integer> moveSequence) {
		System.out.println(CYAN_TEXT_COLOUR);
		if(moveSequence.get(0) == Bar.BAR_POINT_NUMBER) {
			System.out.printf("\tOne checker moved from Bar", moveSequence.get(0));
		}
		else {
			System.out.printf("\tOne checker moved from point %2d", moveSequence.get(0));
		}
		for(int destinationPoint : moveSequence.subList(1, moveSequence.size())) {
			System.out.printf(" --> point %2d", destinationPoint);
		}
		System.out.println(".");
		System.out.print("\t>> Press enter to continue your turn...");
		getLine();
		System.out.print(CLEAR_COLOURS);
	}

    public void printHelp(){   //TODO write help
    	System.out.print(CYAN_TEXT_COLOUR);
        System.out.println("\t* Enter 'QUIT' to quit game.");
        System.out.println("\t* Enter 'ROLL' to roll dice.");
        System.out.println("\t* Enter 'HINT' for help.");
		System.out.println("\t* Enter 'PIP' to view players pip count.");
		System.out.println();
		System.out.print("\t>> Press enter to continue your turn...");
		getLine();
    	System.out.print(CLEAR_COLOURS);
    }

	public void finishTurn() {
		System.out.println(CYAN_TEXT_COLOUR);
		System.out.println("\tYour turn is over.");
		System.out.print("\t>> Press enter to continue...");
		getLine();
		System.out.print(CLEAR_COLOURS);
	}


    public void printBoard(Board board, Player redPlayer, Player whitePlayer,int player){
		System.out.print(CLEAR_SCREEN);
        System.out.flush();
        System.out.println(DASH_LINE);
        System.out.printf("Player Red: %s\t\t\tPlayer White: %s\n", redPlayer.getName(), whitePlayer.getName());
        System.out.println(DASH_LINE);
		System.out.println();
        String boardString=board.toString(player);
        System.out.println(boardString);
		System.out.println(DASH_LINE);
    }

	public void printDashboard(Player activePlayer) {
		System.out.print(MAGENTA_TEXT_COLOUR);
		System.out.printf("Current Player:\t%s\n", activePlayer.getName());
		System.out.print(CLEAR_COLOURS);
		System.out.println(DASH_LINE);
	}

	public void closeUserInput() {
		_userInput.close();
	}

	public void printPipCount(Player playerOne, Player playerTwo, Board board) {
		System.out.print(CYAN_TEXT_COLOUR);
		System.out.printf("\t%s's Pip Count is:  %-3d.\n", playerOne.getName(), board.getPipCount(playerOne));
		System.out.printf("\t%s's Pip Count is:  %-3d.\n", playerTwo.getName(), board.getPipCount(playerTwo));
		System.out.println();
		System.out.print("\t>> Press enter to continue your turn...");
		getLine();
		System.out.print(CLEAR_COLOURS);
	}

	public Player getFirstRoll(Player playerRed, Player playerWhite) {
		Player selectedPlayer;
		Dice singleDice = new Dice();
		int redDiceRoll;
		int whiteDiceRoll;

		do {
			System.out.printf(">> Press Enter to Roll Your First Dice %s:  ", playerRed.getName());
			getLine();
			redDiceRoll = singleDice.roll();
			System.out.print(CYAN_TEXT_COLOUR);
			System.out.printf("\tYou Rolled: [%d]\n", redDiceRoll);
			System.out.print(CLEAR_COLOURS);

			System.out.printf(">> Press Enter to Roll Your First Dice %s:  ", playerWhite.getName());
			getLine();
			whiteDiceRoll = singleDice.roll();
			System.out.print(CYAN_TEXT_COLOUR);
			System.out.printf("\tYou Rolled: [%d]\n", whiteDiceRoll);
			System.out.print(CLEAR_COLOURS);

			if(redDiceRoll == whiteDiceRoll) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.printf("\tYou Rolled the Same Value! Roll Again...\n");
				System.out.print(CLEAR_COLOURS);
			}
		}while(redDiceRoll == whiteDiceRoll);
		selectedPlayer = redDiceRoll > whiteDiceRoll ? playerRed : playerWhite;
		selectedPlayer.addAvailableMove(redDiceRoll);
		selectedPlayer.addAvailableMove(whiteDiceRoll);
		System.out.println(CYAN_TEXT_COLOUR);
		System.out.printf("\t%s will go first!\n", selectedPlayer.getName());
		System.out.println(CLEAR_COLOURS);
		System.out.println(DASH_LINE);
		return selectedPlayer;
	}
}
