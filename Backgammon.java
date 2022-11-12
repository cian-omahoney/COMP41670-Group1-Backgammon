import java.util.*;

public class Backgammon {
	public static void main(String[] args) {
		UI UserInterface = new UI();
		Command currentCommand;
		Player activePlayer;
        Board board=new Board();
		Player playerRed = new Player(Checker.RED);
		Player playerWhite = new Player(Checker.WHITE);
		List<Integer> moveSequence = new ArrayList<>();

		UserInterface.getPlayerNames(playerRed, playerWhite);
        UserInterface.printBoard(board, playerRed, playerWhite);

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

				board.barEmpty(activePlayer);
				while(activePlayer.availableMovesRemaining()) {
					UserInterface.printDice(activePlayer);
					moveSequence = board.barEmpty(activePlayer) ? UserInterface.selectValidMove(board.getValidPointMoves(activePlayer)) :
					 											  UserInterface.selectValidMove(board.getValidBarMoves(activePlayer));
					board.moveChecker(moveSequence, activePlayer);
					activePlayer.updateAvailableMoves(moveSequence);
					UserInterface.printMoves(moveSequence);
					UserInterface.printBoard(board, playerRed, playerWhite);
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

			UserInterface.printBoard(board, playerRed, playerWhite);
			UserInterface.printDashboard(activePlayer);

			currentCommand = UserInterface.getCommand(activePlayer);
		}while(!currentCommand.isQuit());

		if(currentCommand.isQuit()){
			UserInterface.printQuit();
		}

		UserInterface.closeUserInput();
	}
}