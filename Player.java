import java.util.Random;

public class Player {
    private String _name;
    private int _diceRoll[];

    public Player(String name){
        this._name=name;
    }

    public int[] rollDice(){
        Random rand=new Random();
        for (int i=0;i<2;i++)
        {
            _diceRoll[i]=rand.nextInt(1,7);
        }
        return _diceRoll;
    }

    public int[] getDiceRoll(){  //required?
        return _diceRoll;
    }

    public String getName(){
        return _name;
    }
}
