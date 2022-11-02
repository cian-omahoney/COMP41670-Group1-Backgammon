import java.util.Random;

public class Player {
    private String _name;
    private Checker _playerColour;
    private int _diceRoll[];

    public Player(String name, Checker playerColour){
        this._name=name;
        this._diceRoll = new int[]{1,1};
        this._playerColour = playerColour;
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
    
    public Checker getColour() {
    	return _playerColour;
    }
}
