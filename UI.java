import org.w3c.dom.html.HTMLImageElement;

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
	private static final String GAME_LENGTH_REGEX = "[13579]";

	private static Scanner _userInputScan;
	private static Scanner _textFileScan;
	private Command _command;

    public UI(){
    	_userInputScan = new Scanner(System.in);
    	printBackgammonIntro();
    }

	private void printBackgammonIntro(){
		System.out.print(CLEAR_SCREEN);
		System.out.flush();
		System.out.println(DASH_LINE);
		printTextFile(TITLE_TEXT_FILE);
		System.out.println();
		System.out.println(DASH_LINE);
		System.out.printf("\n\t\t\t* * * %sWelcome to Backgammon!%s * * *\n\n", UNDERLINE_TEXT, CLEAR_COLOURS);
	}
    
    public void getPlayerNames(Player redPlayer, Player whitePlayer) {
		Boolean validNames = false;
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
			else if(redPlayer.getName().toLowerCase().equals(whitePlayer.getName().toLowerCase())) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tPlayer names must be different!\n");
				validNames = false;
				System.out.print(CLEAR_COLOURS);
			}
			else if(redPlayer.getName().length() > MAX_NAME_LENGTH || whitePlayer.getName().length() > MAX_NAME_LENGTH) {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.printf("\tPlayer names must be less than %d characters long!\n", MAX_NAME_LENGTH);
				validNames = false;
				System.out.print(CLEAR_COLOURS);
			}
		}while(!validNames);
		System.out.println();
    }

	public int getGameLength() {
		Boolean validGameLength = false;
		int gameLength = 1;
		String userInput;

		do {
			System.out.print(">> Enter Game Length:\t\t\t");
			userInput = getLine();
			if(userInput.matches(GAME_LENGTH_REGEX)) {
				gameLength = Integer.parseInt(userInput);
				validGameLength = true;
			}
			else {
				System.out.print(CYAN_TEXT_COLOUR);
				System.out.println("\tGame length must be an odd number between 1 and 9.\n");
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
		System.out.printf("\t* Type command 'HINT' for help.\n");
		System.out.println(CLEAR_COLOURS);
		System.out.print(">> Press ENTER to begin game...");
		getLine();
	}
    
    public Command getCommand(Player player) {
		System.out.printf(">> Enter command %s:  ", player.getName());
		String input = getLine();
		_command = new Command(input);
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
						System.out.printf("--> OFF ");
					}
					else {
						System.out.printf("--> Point %2d ", destinationPoint);
					}
				}
				System.out.println();
			}

			System.out.println(CLEAR_COLOURS);
			do {
				System.out.printf(">> Enter letter code for desired move: ");
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
					System.out.print("\t>> Press ENTER to try another letter code...");
					getLine();
					System.out.print(CLEAR_COLOURS);
				}
			}while(validMove == false);
			chosenValidMove = validMoveList.get(moveIndex);
		}
		else if(validMoveList.size() == 1) {
			System.out.println(CYAN_TEXT_COLOUR);
			System.out.printf("\tThere was only 1 valid move possible.", UNDERLINE_TEXT, validMoveList.size());
			System.out.print(CLEAR_COLOURS);
			chosenValidMove = validMoveList.get(0);
		}
		else {
			System.out.println(CYAN_TEXT_COLOUR);
			System.out.printf("\tThere was no valid move possible!\n", UNDERLINE_TEXT);
			System.out.print(CLEAR_COLOURS);
			chosenValidMove = new ArrayList<>();
		}
		return chosenValidMove;
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
		if(moveSequence.size() > 0) {
			System.out.println(CYAN_TEXT_COLOUR);
			if(moveSequence.get(0) == Board.BAR_PIP_NUMBER) {
				System.out.printf("\tOne checker moved from Bar", moveSequence.get(0));
			}
			else {
				System.out.printf("\tOne checker moved from Point %2d", moveSequence.get(0));
			}
			for(int destinationPoint : moveSequence.subList(1, moveSequence.size())) {
				if(destinationPoint == Board.BEAR_OFF_PIP_NUMBER) {
					System.out.printf(" --> OFF");
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
		System.out.printf("Score:      %s%3d%s       |              Match: %d/%d             |   Score:        %s%3d%s\n", MAGENTA_TEXT_COLOUR, redPlayer.getScore(), CLEAR_COLOURS, matchNumber, gameLength,MAGENTA_TEXT_COLOUR, whitePlayer.getScore(), CLEAR_COLOURS);
		System.out.printf("Double:     %s%3s%s       |                 %3s                 |   Double:       %s%3s%s\n", MAGENTA_TEXT_COLOUR, board.doublingCubeToString(redPlayer), CLEAR_COLOURS, board.doublingCubeToString(), MAGENTA_TEXT_COLOUR, board.doublingCubeToString(whitePlayer), CLEAR_COLOURS);
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
			System.out.printf("\n>> Press ENTER to Roll Your First Dice %s...", playerRed.getName());
			getLine();
			redDiceRoll = singleDice.roll();
			System.out.print(CYAN_TEXT_COLOUR);
			System.out.printf("\tYou Rolled: [%d]\n", redDiceRoll);
			System.out.print(CLEAR_COLOURS);

			System.out.printf("\n>> Press ENTER to Roll Your First Dice %s...", playerWhite.getName());
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
		System.out.printf(CLEAR_COLOURS);
		System.out.print("\n>> Press ENTER to begin your turn...");
		getLine();
		System.out.println(DASH_LINE);
		return selectedPlayer;
	}

	public void printMatchWinner(Player playerA, Player playerB, Board board, int matchNumber) {
		String winnerName = "";
		if(board.getPipCount(playerA) == 0) {
			winnerName = playerA.getName();
		}
		else if(board.getPipCount(playerB) == 0) {
			winnerName = playerB.getName();
		}

		if(!winnerName.equals("")) {
			printTextFile(CONGRATULATIONS_TEXT_FILE);
			System.out.printf("\n\t\t\t* * * %s%s Won Match %d!%s * * *\n", UNDERLINE_TEXT, winnerName, matchNumber, CLEAR_COLOURS);
			System.out.print("\n>> Press ENTER to begin next match...");
			getLine();
			System.out.println(DASH_LINE);
		}
	}

	/**
	 * Print text file.
	 * @param fileName Name of file to print.
	 */
	private static void printTextFile(String fileName) {
		File mediaFile = new File(MEDIA_ROOT + fileName);
		if(mediaFile.exists() && mediaFile.canRead()) {
			try {
				_textFileScan = new Scanner(mediaFile);
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

	public void closeUserInput() {
		_userInputScan.close();
	}
}