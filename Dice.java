// Team 1 Backgammon Project
// By 
/***@author Cian O'Mahoney Github:cian-omahoney 
 *  @author Ciarán Cullen  Github:TangentSplash
*/


import java.util.Random;

//Class representing a single die
public class Dice {
    private static final int MAX_VALUE = 6;
    private static final int MIN_VALUE = 1;
    private static Random rand = new Random();

    private int _rollValue;

    public Dice() {
        _rollValue = 1;
    }

    public int roll() {
        _rollValue = rand.nextInt(MIN_VALUE, MAX_VALUE+1);
        return _rollValue;
    }

    public int getRollValue() {
        return _rollValue;
    }
}
