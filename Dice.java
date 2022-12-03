// Team 1 Backgammon Project
import java.util.Random;

/**
 * Class representing a single die
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
public class Dice {
    private static final int MAX_VALUE = 6;
    private static final int MIN_VALUE = 1;
    private static Random rand = new Random();
    private int _rollValue;

    /**
     * Dice constructor.
     */
    public Dice() {
        _rollValue = 1;
    }

    /**
     * Roll dice and return value.
     * @return Dice value.
     */
    public int roll() {
        _rollValue = rand.nextInt(MIN_VALUE, MAX_VALUE+1);
        return _rollValue;
    }

    /**
     * Get value of latest dice roll.
     * @return Dice value.
     */
    public int getRollValue() {
        return _rollValue;
    }
}
