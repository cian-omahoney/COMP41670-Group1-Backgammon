import java.util.*;

public class Backgammon {
	public static void main(String[] args) {
		UI userInterface = new UI();
		Game game;
		boolean isPlayAnotherGame = true;
		do {
			game = new Game(userInterface);
			isPlayAnotherGame = game.play();
		}while(isPlayAnotherGame);

		userInterface.closeUserInput();
	}
}
