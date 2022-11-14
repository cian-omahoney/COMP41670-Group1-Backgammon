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
		activePlayer = UserInterface.getFirstRoll(playerRed, playerWhite);

        UserInterface.printBoard(board, playerRed, playerWhite, activePlayer.getNumber());
		UserInterface.printDashboard(activePlayer);
        currentCommand = new Command("FIRST");
		do{
			if(currentCommand.isHint()) {
				UserInterface.printHint();
			}
			else if(currentCommand.isPip()) {
				UserInterface.printPipCount(playerWhite, playerRed, board);
			}
			else if(currentCommand.isDouble()) {

			}
			else if(currentCommand.isRoll() || currentCommand.isFirst()) {
				if(currentCommand.isRoll()) {
					activePlayer.rollBothDice();
				}

				while(activePlayer.availableMovesRemaining()) {
					UserInterface.printDice(activePlayer);
					if(board.isBarEmpty(activePlayer)) {
						moveSequence = UserInterface.selectValidMove(board.getValidMoves(activePlayer));
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

			if(!board.isGameOver(playerRed, playerWhite)) {
				UserInterface.printDashboard(activePlayer);
				currentCommand = UserInterface.getCommand(activePlayer);
			}
		}while(!currentCommand.isQuit() && !board.isGameOver(playerRed, playerWhite));

		if(currentCommand.isQuit()){
			UserInterface.printQuit();
		}

		if(board.isGameOver(playerRed, playerWhite)) {
			UserInterface.printWinner(playerRed, playerWhite, board);
		}

		UserInterface.closeUserInput();
	}
}