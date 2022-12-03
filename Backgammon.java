// Team 1 Backgammon Project
/**
 * Backgammon game.  Contains main method.
 *
 * Architectural Pattern: This project implements the Model-View-Controller architectural pattern
 * 						  by separating the UI and interaction from the data.
 *
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */

public class Backgammon {
	/**
	 * Main method implementing backgammon game.
	 * @param args No inputs.
	 */
	public static void main(String[] args) {
		UI userInterface = new UI();
		Game game;
		boolean isPlayAnotherGame;

		// Loop allows players to play multiple games in a row:
		do {
			game = new Game(userInterface);
			isPlayAnotherGame = game.play();
		}while(isPlayAnotherGame);

		// Close user input scanner once no longer needed:
		userInterface.closeUserInput();
	}
}