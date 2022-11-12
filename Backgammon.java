import java.util.*;

public class Backgammon {
	public static void main(String[] args) {
		UI UserInterface = new UI();
		Command currentCommand;
		Player activePlayer;
        Board board=new Board();
		Player playerWhite = new Player(Checker.WHITE,0);
		Player playerRed = new Player(Checker.RED,1);
		List<Integer> moveSequence = new ArrayList<>();

		UserInterface.getPlayerNames(playerRed, playerWhite);
        UserInterface.printBoard(board, playerRed, playerWhite,1);


		activePlayer = UserInterface.getFirstRoll(playerRed, playerWhite);
		UserInterface.printDashboard(activePlayer);
        currentCommand = new Command("FIRST");
		do{
			if(currentCommand.isHint()) {
				UserInterface.printHelp();
			}
			else if(currentCommand.isPip()) {
				UserInterface.printPipCount(playerWhite, playerRed, board);
			}
			else if(currentCommand.isRoll() || currentCommand.isFirst()) {
				if(currentCommand.isRoll()) {
					activePlayer.rollBothDice();
				}

				while(activePlayer.availableMovesRemaining()) {
					UserInterface.printDice(activePlayer);
					if(board.isBarEmpty(activePlayer)) {
						if(board.isBearOff(activePlayer)) {
							moveSequence = UserInterface.selectValidMove(board.getValidBearOffMoves(activePlayer));
						}
						else {
							moveSequence = UserInterface.selectValidMove(board.getValidPointMoves(activePlayer));
						}
					}
					else {
						moveSequence = UserInterface.selectValidMove(board.getValidBarMoves(activePlayer));
					}

					board.moveChecker(moveSequence, activePlayer);
					activePlayer.updateAvailableMoves(moveSequence);
					UserInterface.printMoves(moveSequence);
					UserInterface.printBoard(board, playerRed, playerWhite,activePlayer.getNumber());
					UserInterface.printDashboard(activePlayer);
				}

				// After the active player finishes their turn, switch to the next player:
				activePlayer = switch(activePlayer.getColour()) {
					case WHITE -> playerRed;
					case RED -> playerWhite;
					default -> activePlayer;
				};
				UserInterface.finishTurn();
			}

			UserInterface.printBoard(board, playerRed, playerWhite,activePlayer.getNumber());
			UserInterface.printDashboard(activePlayer);

			currentCommand = UserInterface.getCommand(activePlayer);
		}while(!currentCommand.isQuit());

		if(currentCommand.isQuit()){
			UserInterface.printQuit();
		}

		UserInterface.closeUserInput();
	}
}