import java.util.*;

public class Backgammon {
	public static void main(String[] args) {
		UI UserInterface = new UI();
		Command currentCommand;
		Player activePlayer;
        Board board=new Board();
		Player playerRed = new Player(Checker.RED);
		Player playerWhite = new Player(Checker.WHITE);

		UserInterface.getPlayerNames(playerRed, playerWhite);
        //UserInterface.printBoard(board, playerRed, playerWhite);
        
        // Randomly choose player to get first turn:
        activePlayer = new Random().nextBoolean() ? playerRed : playerWhite;
		UserInterface.printFirstPlayerChosen(activePlayer);
        
		do{
			currentCommand = UserInterface.getCommand(activePlayer);
			if(currentCommand.isHelp()) {
				UserInterface.printHelp();
			}
			else if(currentCommand.isRoll()) {
				activePlayer.rollDice();
				UserInterface.printDice(activePlayer);
				UserInterface.printValidMoves(board.getValidMoves(activePlayer));
			}
			
			// After the active player finishes their turn, switch to the next player:
			activePlayer = switch(activePlayer.getColour()) {
			case WHITE -> playerRed;
			case RED -> playerWhite;
			default -> activePlayer;
			};
		}while(!currentCommand.isQuit());

		if(currentCommand.isQuit()){
			UserInterface.printQuit();
		}

		UserInterface.closeUserInput();
	}
}