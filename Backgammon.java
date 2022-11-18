import java.util.*;

public class Backgammon {
	public static void main(String[] args) {
		UI userInterface = new UI();
		Game game = new Game(userInterface);
		game.play();
		userInterface.closeUserInput();
	}
}
