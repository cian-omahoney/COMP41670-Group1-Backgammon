import java.util.*;

public class Backgammon {
	
	public Backgammon() {
		UI UserInterface = new UI();
		Command currentCommand;

		Player playerOne = UserInterface.getPlayerName("One");
		Player playerTwo = UserInterface.getPlayerName("Two");
		Player activePlayer;
		
		String[][] topCheckers= UserInterface.setupCheckers("X", "O",true);
        String[][] bottomCheckers= UserInterface.setupCheckers("O", "X",false);
        Board board=new Board(topCheckers,bottomCheckers);
        
        UserInterface.printBoard(board);
        
        // Randomly choose player to get first turn:
        activePlayer = new Random().nextBoolean() ? playerOne : playerTwo;
		
        do{
			currentCommand = UserInterface.getCommand(activePlayer);
			if(currentCommand.isHelp()) {
				UserInterface.printHelp();
			}
			else if(currentCommand.isRoll()) {
				activePlayer.rollDice();
				UserInterface.displayDice(activePlayer);
			}
			
			activePlayer = switch(activePlayer.getNumber()) {
			case 1 -> playerTwo;
			case 2 -> playerOne;
			default -> playerOne;
			};
		}while(!currentCommand.isQuit());

		if(currentCommand.isQuit()){
			UserInterface.printQuit();
		}
	}
}