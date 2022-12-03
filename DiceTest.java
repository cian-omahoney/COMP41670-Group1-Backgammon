// Team 1 Backgammon Project
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test for Dice class.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
class DiceTest {
    private Dice _testDice;

    @BeforeEach
    void setUp() {
        _testDice = new Dice();
    }

    @Test
    void testRoll_isBetween1And6() {
        _testDice.roll();
        assertTrue(_testDice.getRollValue() <= 6);
        assertTrue(_testDice.getRollValue() >= 1);
    }

    @Test
    void testRoll_isGetRollValue() {
        _testDice.roll();
        assertTrue(_testDice.getRollValue() <= 6);
        assertTrue(_testDice.getRollValue() >= 1);
    }
}