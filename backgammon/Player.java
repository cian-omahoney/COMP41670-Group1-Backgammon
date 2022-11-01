package backgammon;
import java.util.Random;

public class Player {
    private String _name;
    private int _playerNumber;
    private int _diceRoll[];

    public Player(String name, int playerNumber){
        this._name=name;
        this._diceRoll = new int[]{1,1};
        this._playerNumber = playerNumber;
    }

    public void rollDice(){
        Random rand = new Random();
        _diceRoll[0]=rand.nextInt(1,7);
        _diceRoll[1]=rand.nextInt(1,7);
    }
    
    public int[] getDiceRoll() {
    	return _diceRoll;
    }

    public String getName(){
        return _name;
    }
    
    public int getNumber() {
    	return _playerNumber;
    }
}
