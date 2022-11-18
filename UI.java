import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class UI{
	private static final String MEDIA_ROOT      	= "./media/";
	private static final String TITLE_TEXT_FILE 	= "backgammonTitle.txt";
	private static final String CONGRATULATIONS_TEXT_FILE   	= "congratulations.txt";
	private static final String CLEAR_SCREEN 		= "\033[H\033[2J";
	public static final String  CLEAR_COLOURS 		= "\033[0m";
	private static final String YELLOW_TEXT_COLOUR 	= "\033[1;33m";
	private static final String GREEN_TEXT_COLOUR 	= "\033[1;32m";
	private static final String CYAN_TEXT_COLOUR 	= "\033[1;36m";
	private static final String MAGENTA_TEXT_COLOUR = "\033[1;35m";
	private static final String UNDERLINE_TEXT      = "\033[1;4m";
	private static final String BOLD_TEXT			= "\033[1;1m";
	public static final String WHITE_CHECKER_COLOUR = "\033[0;39m";
	public static final String RED_CHECKER_COLOUR   = "\033[1;31m";
	private static final String DASH_LINE = YELLOW_TEXT_COLOUR + "=".repeat(85) + CLEAR_COLOURS;
	private static final int ALPHABET_SIZE = 26;
	private static final int MAX_NAME_LENGTH = 7;
	private static final String MOVE_REGEX = "[A-Z]*";
	private static final String GAME_LENGTH_REGEX = "[0-9]{1,3}";
	private static final String ACCEPT_REGEX = "ACCEPT";
	private static final String REFUSE_REGEX = "REFUSE";

	private static Scanner _userInputScan;

	public UI(){
    	_userInputScan = new Scanner(System.in);
    }

	public void printBackgammonIntro(){
		System.out.print(CLEAR_SCREEN);
		System.out.flush();
		System.out.println(DASH_LINE);
		printTextFile(TITLE_TEXT_FILE);
		System.out.println();
		System.out.println(DASH_LINE);
		System.out.printf("\n\t\t\t* * * %sWelcome to Backgammon!%s * * *\n\n", UNDERLINE_TEXT, CLEAR_COLOURS);
	}
    
    public void getPlayerNames(Player redPlayer, Player whitePlayer) {
		boolean validNames;
		do {
			System.out.print(">> Enter Red Checker Player Name:\t");
			redPlayer.setName(getLine());
			System.out.print(">> Enter White Checker Player Name:\t");
			whitePlayer.setName(getLine());
			validNames = true;
			if(redPlayer.getName().equals("") || whitePlayer.getName().equals("")) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tBoth players must have non-empty names!\n");
				validNames = false;
				System.out.print(CLEAR_COLOURS);
			}
			else if(redPlayer.getName().equalsIgnoreCase(whitePlayer.getName())) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tPlayer names must be different!\n");
				validNames = false;
				System.out.print(CLEAR_COLOURS);
			}
			else if(redPlayer.getName().length() > MAX_NAME_LENGTH || whitePlayer.getName().length() > MAX_NAME_LENGTH) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.printf("\tPlayer names must be less than %d characters long!\n", MAX_NAME_LENGTH);
				validNames = false;
				System.out.println(CLEAR_COLOURS);
			}
		}while(!validNames);
		System.out.println();
    }

	public int getGameLength() {
		boolean validGameLength;
		int gameLength = 1;
		String userInput;

		do {
			System.out.print(">> Enter Game Length:\t\t\t");
			userInput = getLine();
			if(userInput.matches(GAME_LENGTH_REGEX) && Integer.parseInt(userInput) % 2 == 1) {
				gameLength = Integer.parseInt(userInput);
				validGameLength = true;
			}
			else {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tGame length must be an odd integer number.\n");
				validGameLength = false;
				System.out.print(CLEAR_COLOURS);
			}
		}while(!validGameLength);

		System.out.println();
		return gameLength;
	}

	public void printGameIntro(Player redPlayer, Player whitePlayer) {
		System.out.print(DASH_LINE);
		System.out.println(CYAN_TEXT_COLOUR);
		System.out.println("\n\tThe game is ready to begin!");
		System.out.printf("\n\t* %s controls the red checkers (%s%s).\n", redPlayer.getName(), Checker.RED.getSymbol(), CYAN_TEXT_COLOUR);
		System.out.printf("\t* %s controls the white checkers (%s%s).\n", whitePlayer.getName(), Checker.WHITE.getSymbol(), CYAN_TEXT_COLOUR);
		System.out.print("\t* Type command 'HINT' for help.\n");
		System.out.println(CLEAR_COLOURS);
		System.out.print(">> Press ENTER to begin game...");
		getLine();
	}
    
    public Command getCommand(Player player) {
		System.out.printf(">> Enter command %s:  ", player.getName());
		String input = getLine();
		Command _command = new Command(input);
		if (_command.isInvalid()) {
			System.out.print(CYAN_TEXT_COLOUR);
			System.out.println("\tThis command is invalid! Try \"HINT\".");
			System.out.print("\t>> Press ENTER to try another command...");
			getLine();
			System.out.print(CLEAR_COLOURS);
		}
		return _command;
    }
    
    public String getLine() {
		String userInputLine;
		System.out.printf(GREEN_TEXT_COLOUR);
		userInputLine = _userInputScan.nextLine().trim();
		System.out.printf(CLEAR_COLOURS);
    	return userInputLine;
    }

    public void printQuit(Player activePlayer) {
    	System.out.println(DASH_LINE);
    	System.out.printf("\t\t\t\t%s Quit. Game Over.\n", activePlayer.getName());
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

	public void printNotDoublingCubeOwner() {
		System.out.print(CYAN_TEXT_COLOUR);
		System.out.println("\tYou cannot offer a double!");
		System.out.println("\tYou are not the owner of the doubling cube.");
		System.out.print("\t>> Press ENTER to try another command...");
		getLine();
		System.out.print(CLEAR_COLOURS);
	}

	public boolean offerDouble(Player activePlayer, Player playerRed, Player playerWhite) {
		String nonActivePlayerName;
		boolean validInput;
		boolean isOfferAccepted = false;
		String userInput;
		if(activePlayer == playerRed) {
			nonActivePlayerName = playerWhite.getName();
		}
		else {
			nonActivePlayerName = playerRed.getName();
		}

		System.out.print(MAGENTA_TEXT_COLOUR);
		System.out.println(UNDERLINE_TEXT);
		System.out.printf("\tPass Control to %s:\n", nonActivePlayerName);
		System.out.print(CLEAR_COLOURS);

		System.out.print(CYAN_TEXT_COLOUR);
		System.out.printf("\t%s would like to offer a double.\n", activePlayer.getName());
		System.out.print(CLEAR_COLOURS);
		do{
			System.out.print("\t>> Enter 'ACCEPT' to accept or 'REFUSE' to refuse:\t");
			userInput = getLine().toUpperCase();
			if(userInput.matches(ACCEPT_REGEX)){
				validInput = true;
				isOfferAccepted = true;

				System.out.print(MAGENTA_TEXT_COLOUR);
				System.out.println(UNDERLINE_TEXT);
				System.out.printf("\tPass Control Back to %s:\n", activePlayer.getName());
				System.out.print(CLEAR_COLOURS);
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.printf("\t%s accepted the offer to double.\n", nonActivePlayerName);
				System.out.print("\t>> Press ENTER to continue your turn...");
				getLine();
				System.out.print(CLEAR_COLOURS);
			}
			else if(userInput.matches(REFUSE_REGEX)){
				validInput = true;
				System.out.print(MAGENTA_TEXT_COLOUR);
				System.out.println(UNDERLINE_TEXT);
				System.out.printf("\tPass Control Back to %s:\n", activePlayer.getName());
				System.out.print(CLEAR_COLOURS);
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.printf("\t%s refused the offer to double.\n", nonActivePlayerName);
				System.out.print("\t>> Press ENTER to finish this match...");
				getLine();
				System.out.print(CLEAR_COLOURS);
			}
			else {
				validInput = false;
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tThis command is invalid! Try 'ACCEPT' or 'REFUSE'...");
				System.out.print(CLEAR_COLOURS);
			}
		}while(!validInput);

		return isOfferAccepted;
	}

	public List<Integer> selectValidMove(List<ArrayList<Integer>> validMoveList){
		boolean validMove = false;
		int moveIndex = 0;
		List<Integer> chosenValidMove;
		String input;

		if(validMoveList.size() > 1) {
			System.out.println(CYAN_TEXT_COLOUR);
			System.out.printf("\t%s%d Valid Moves Possible:\n", UNDERLINE_TEXT, validMoveList.size());
			System.out.print(CLEAR_COLOURS);

			System.out.print(CYAN_TEXT_COLOUR);
			for(List<Integer> moveSequence : validMoveList) {
				if(moveSequence.get(0) == Board.BAR_PIP_NUMBER) {
					System.out.printf("\t[%s] : Move checker from Bar ", convertIndexToLabel(validMoveList.indexOf(moveSequence)));
				}
				else {
					System.out.printf("\t[%s] : Move checker from Point %2d ", convertIndexToLabel(validMoveList.indexOf(moveSequence)), moveSequence.get(0));
				}

				for(int destinationPoint : moveSequence.subList(1, moveSequence.size())) {
					if(destinationPoint == Board.BEAR_OFF_PIP_NUMBER) {
						System.out.print("--> OFF ");
					}
					else {
						System.out.printf("--> Point %2d ", destinationPoint);
					}
				}
				System.out.println();
			}

			System.out.println(CLEAR_COLOURS);
			do {
				System.out.print(">> Enter letter code for desired move: ");
				input = getLine();
				if(input.toUpperCase().matches(MOVE_REGEX)) {
					moveIndex = convertLabelToIndex(input.toUpperCase());
					if(moveIndex >= 0 && moveIndex < validMoveList.size()) {
						validMove = true;
					}
				}

				if(!validMove) {
					System.out.print(CYAN_TEXT_COLOUR);
					System.out.println("\tThis is not a valid letter code!.");
					System.out.print("\t>> Press ENTER to try another letter code...");
					getLine();
					System.out.print(CLEAR_COLOURS);
				}
			}while(!validMove);
			chosenValidMove = validMoveList.get(moveIndex);
		}
		else if(validMoveList.size() == 1) {
			System.out.println(CYAN_TEXT_COLOUR);
			System.out.print("\tThere was only 1 valid move possible.");
			System.out.print(CLEAR_COLOURS);
			chosenValidMove = validMoveList.get(0);
		}
		else {
			System.out.println(CYAN_TEXT_COLOUR);
			System.out.print("\tThere was no valid move possible!\n");
			System.out.print(CLEAR_COLOURS);
			chosenValidMove = new ArrayList<>();
		}
		return chosenValidMove;
	}

	private String convertIndexToLabel(int index) {
		String label = "";
		if (index >= ALPHABET_SIZE) {
			label += (char) ('A' + ((index / ALPHABET_SIZE) - 1) % ALPHABET_SIZE);
		}
		label += (char) ('A' + index % ALPHABET_SIZE);
		return label;
	}

	private int convertLabelToIndex (String label) {
		int index = 0;

		for(int i = label.length()-1; i>=0; i--) {
			index += (label.charAt(i)-'A'+1) *Math.pow(ALPHABET_SIZE, (label.length() - 1 - i));
		}
		index--;
		return index;
	}

	public void printMoves(List<Integer> moveSequence) {
		if(moveSequence.size() > 0) {
			System.out.println(CYAN_TEXT_COLOUR);
			if(moveSequence.get(0) == Board.BAR_PIP_NUMBER) {
				System.out.print("\tOne checker moved from Bar");
			}
			else {
				System.out.printf("\tOne checker moved from Point %2d", moveSequence.get(0));
			}
			for(int destinationPoint : moveSequence.subList(1, moveSequence.size())) {
				if(destinationPoint == Board.BEAR_OFF_PIP_NUMBER) {
					System.out.print(" --> OFF");
				}
				else {
					System.out.printf(" --> Point %2d", destinationPoint);
				}
			}
			System.out.println(".\n");
			System.out.print("\t>> Press ENTER to continue your turn...");
			getLine();
			System.out.print(CLEAR_COLOURS);
		}
		else {
			System.out.println(CYAN_TEXT_COLOUR);
			System.out.print("\t>> Press ENTER to finish your turn...");
			getLine();
			System.out.print(CLEAR_COLOURS);
		}
	}

    public void printHint(){   //TODO write help
    	System.out.print(CYAN_TEXT_COLOUR);
        System.out.println("\t* Enter 'QUIT' to quit game.");
        System.out.println("\t* Enter 'ROLL' to roll dice.");
        System.out.println("\t* Enter 'HINT' for help.");
		System.out.println("\t* Enter 'DOUBLE' to offer double to other player.");
		System.out.println("\t* Enter 'PIP' to view players pip count.");
		System.out.println();
		System.out.print("\t>> Press ENTER to continue your turn...");
		getLine();
    	System.out.print(CLEAR_COLOURS);
    }

	public void finishTurn() {
		System.out.println(CYAN_TEXT_COLOUR);
		System.out.println("\tYour turn is over.");
		System.out.print("\t>> Press ENTER to continue...");
		getLine();
		System.out.print(CLEAR_COLOURS);
	}


    public void printBoard(Board board, Player redPlayer, Player whitePlayer,int player, int matchNumber, int gameLength){
		System.out.print(CLEAR_SCREEN);
        System.out.flush();
        System.out.println(DASH_LINE);
		System.out.printf("Player Red: %s%-7s%s   |                                     |   Player White: %s%-7s%s\n",MAGENTA_TEXT_COLOUR,redPlayer.getName(), CLEAR_COLOURS, MAGENTA_TEXT_COLOUR, whitePlayer.getName(), CLEAR_COLOURS);
        System.out.printf("Pip Count:  %s%3d%s       |   * * * %sB A C K G A M M O N%s * * *   |   Pip Count:    %s%3d%s\n", MAGENTA_TEXT_COLOUR, board.getPipCount(redPlayer), CLEAR_COLOURS,UNDERLINE_TEXT, CLEAR_COLOURS,  MAGENTA_TEXT_COLOUR, board.getPipCount(whitePlayer), CLEAR_COLOURS);
		System.out.printf("Score:      %s%3d%s       |            Match: %3d/%-3d           |   Score:        %s%3d%s\n", MAGENTA_TEXT_COLOUR, redPlayer.getScore(), CLEAR_COLOURS, matchNumber, gameLength,MAGENTA_TEXT_COLOUR, whitePlayer.getScore(), CLEAR_COLOURS);
		System.out.printf("Double:     %s%-5s%s     |                 %3s                 |   Double:       %s%-5s%s\n", MAGENTA_TEXT_COLOUR, board.doublingCubeToString(redPlayer), CLEAR_COLOURS, board.doublingCubeToString(), MAGENTA_TEXT_COLOUR, board.doublingCubeToString(whitePlayer), CLEAR_COLOURS);
		System.out.println(DASH_LINE);
		System.out.println();
        String boardString = board.toString(player);
        System.out.println(boardString);
		System.out.println(DASH_LINE);
    }

	public void printDashboard(Player activePlayer) {
		System.out.print(MAGENTA_TEXT_COLOUR);
		System.out.printf("\t\t\t\tCurrent Player:\t%s\n", activePlayer.getName());
		System.out.print(CLEAR_COLOURS);
		System.out.println(DASH_LINE);
	}

	public void printPipCount(Player playerOne, Player playerTwo, Board board) {
		System.out.print(CYAN_TEXT_COLOUR);
		System.out.printf("\t%s's Pip Count is:  %-3d.\n", playerOne.getName(), board.getPipCount(playerOne));
		System.out.printf("\t%s's Pip Count is:  %-3d.\n", playerTwo.getName(), board.getPipCount(playerTwo));
		System.out.println();
		System.out.print("\t>> Press ENTER to continue your turn...");
		getLine();
		System.out.print(CLEAR_COLOURS);
	}

	public Player getFirstRoll(Player playerRed, Player playerWhite, int matchNumber) {
		Player selectedPlayer;
		Dice singleDice = new Dice();
		int redDiceRoll;
		int whiteDiceRoll;

		System.out.println(CLEAR_SCREEN);
		System.out.println(DASH_LINE);
		System.out.print(MAGENTA_TEXT_COLOUR);
		System.out.printf("\t\t\t\t* * * MATCH %d * * *\n", matchNumber);
		System.out.print(CLEAR_COLOURS);
		System.out.println(DASH_LINE);
		do {
			System.out.printf("\n>> Press ENTER to roll your first dice %s...", playerRed.getName());
			getLine();
			redDiceRoll = singleDice.roll();
			System.out.print(CYAN_TEXT_COLOUR);
			System.out.printf("\tYou Rolled: [%d]\n", redDiceRoll);
			System.out.print(CLEAR_COLOURS);

			System.out.printf("\n>> Press ENTER to roll your first dice %s...", playerWhite.getName());
			getLine();
			whiteDiceRoll = singleDice.roll();
			System.out.print(CYAN_TEXT_COLOUR);
			System.out.printf("\tYou Rolled: [%d]\n", whiteDiceRoll);
			System.out.print(CLEAR_COLOURS);

			if(redDiceRoll == whiteDiceRoll) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.print("\tYou Rolled the Same Value! Roll Again...\n");
				System.out.print(CLEAR_COLOURS);
			}
		}while(redDiceRoll == whiteDiceRoll);
		selectedPlayer = redDiceRoll > whiteDiceRoll ? playerRed : playerWhite;
		selectedPlayer.addAvailableMove(redDiceRoll);
		selectedPlayer.addAvailableMove(whiteDiceRoll);
		System.out.println(CYAN_TEXT_COLOUR);
		System.out.printf("\t%s will go first!\n", selectedPlayer.getName());
		System.out.printf(CLEAR_COLOURS);
		System.out.print("\n>> Press ENTER to begin your turn...");
		getLine();
		System.out.println(DASH_LINE);
		return selectedPlayer;
	}

	public void printWinner(Player winner, Player playerA, Player playerB, int matchNumber, int gameLength, Board board) {
		printTextFile(CONGRATULATIONS_TEXT_FILE);
		System.out.printf("\n\t\t\t--> %s%s Won Match %d!%s\n", UNDERLINE_TEXT,winner.getName(), matchNumber, CLEAR_COLOURS);

		if(board.isBackgammoned(playerA, playerB)) {
			System.out.print("\t\t\t--> Game Ended in Backgammon\n");
		}
		else if (board.isGammoned()){
			System.out.print("\t\t\t--> Game Ended in Gammon\n");
		}
		else {
			System.out.print("\t\t\t--> Game Ended in Single\n");
		}
		System.out.printf("\t\t\t--> %s's score:%3d\n", winner.getName(), winner.getScore());
		if(winner.getScore() < gameLength) {
			System.out.print("\n>> Press ENTER to begin next match...");
		}
		else {
			System.out.printf("\n\t\t\t--> %s%s is the Overall Game Winner!%s\n", UNDERLINE_TEXT,winner.getName(), CLEAR_COLOURS);
			System.out.print("\t\t\tThe final scores were:\n");
			System.out.printf("\t\t\t--> %s scored %d/%d.\n", playerA.getName(), playerA.getScore(), gameLength);
			System.out.printf("\t\t\t--> %s scored %d/%d.\n\n", playerB.getName(), playerB.getScore(), gameLength);

			System.out.print(DASH_LINE);
			System.out.print("\n>> Press ENTER to finish game...");
		}
		getLine();
		System.out.println(DASH_LINE);
	}

	/**
	 * Print text file.
	 * @param fileName Name of file to print.
	 */
	private static void printTextFile(String fileName) {
		File mediaFile = new File(MEDIA_ROOT + fileName);
		if(mediaFile.exists() && mediaFile.canRead()) {
			try {
				Scanner _textFileScan = new Scanner(mediaFile);
				while(_textFileScan.hasNextLine()) {
					System.out.printf(BOLD_TEXT);
					System.out.println(_textFileScan.nextLine());
					System.out.printf(CLEAR_COLOURS);
				}
				_textFileScan.close();
			} catch (FileNotFoundException e) {
				System.out.println("UI.printTextFile: File Not Found.");
			}
		}
		else {
			System.out.println("UI.printTextFile: File Not Found.");
		}
	}

	public boolean playAnotherGame() {
		boolean playAnotherGame = false;
		boolean validInput = false;
		String userInput;
		do{
			System.out.print(">> Would you like to play again?\n>> Type 'NEW' to play again or 'QUIT' to exit: ");
			userInput = getLine().toUpperCase();
			if(userInput.matches("NEW")) {
				validInput = true;
				playAnotherGame = true;
			}
			else if (userInput.matches("QUIT")){
				validInput = true;
			}
			else {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tThis command is invalid! Try 'NEW' or 'QUIT'...");
				System.out.print(CLEAR_COLOURS);
			}
		}while(!validInput);
		System.out.println(DASH_LINE);
		return playAnotherGame;
	}

	public void closeUserInput() {
		_userInputScan.close();
	}
}