import java.util.*;

public class Backgammon {
	public static void main(String[] args) {
		UI UserInterface = new UI();
		Command currentCommand;
		Player activePlayer;
        Board board=new Board();
		
		Player playerOne = UserInterface.getPlayerName("One");
		Player playerTwo = UserInterface.getPlayerName("Two");

        UserInterface.printBoard(board);
        
        // Randomly choose player to get first turn:
        activePlayer = new Random().nextBoolean() ? playerOne : playerTwo;
		UserInterface.printFirstPlayerChosen(activePlayer);
        
		do{
			currentCommand = UserInterface.getCommand(activePlayer);
			if(currentCommand.isHelp()) {
				UserInterface.printHelp();
			}
			else if(currentCommand.isRoll()) {
				activePlayer.rollDice();
				UserInterface.printDice(activePlayer);
			}
			
			// After the active player finishes their turn, switch to the next player:
			activePlayer = switch(activePlayer.getColour()) {
			case WHITE -> playerTwo;
			case RED -> playerOne;
			default -> playerOne;
			};
		}while(!currentCommand.isQuit());

		if(currentCommand.isQuit()){
			UserInterface.printQuit();
		}
	}
}