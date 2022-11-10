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
        UserInterface.printBoard(board, playerRed, playerWhite,playerWhite.getNumber());	//TODO Does this display the correct player at the start?
        
        // Randomly choose player to get first turn:
		// TODO: CHange this to have first dice roll!!
        activePlayer = new Random().nextBoolean() ? playerRed : playerWhite;
		UserInterface.printFirstPlayerChosen(activePlayer);
		UserInterface.printDashboard(activePlayer);
        
		do{
			currentCommand = UserInterface.getCommand(activePlayer);
			if(currentCommand.isHint()) {
				UserInterface.printHelp();
			}
			else if(currentCommand.isPip()) {
				UserInterface.printPipCount(playerWhite, playerRed, board);
			}
			else if(currentCommand.isRoll()) {
				activePlayer.rollDice();

				while(activePlayer.availableMovesRemaining()) {
					UserInterface.printDice(activePlayer);
					moveSequence = UserInterface.selectValidMove(board.getValidMoves(activePlayer));
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
		}while(!currentCommand.isQuit());

		if(currentCommand.isQuit()){
			UserInterface.printQuit();
		}

		UserInterface.closeUserInput();
	}
}