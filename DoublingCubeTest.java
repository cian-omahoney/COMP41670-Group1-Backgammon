// Team 1 Backgammon Project
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test for DoublingCube class.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
class DoublingCubeTest {
    private DoublingCube doublingCube;

    @BeforeEach
    void setUp() {
        doublingCube = new DoublingCube();
    }

    @Test
    void doubleStakes() {
        doublingCube.doubleStakes();
        assertEquals(doublingCube.getDoublingCubeValue(),2);
    }

    @Test
    void isDoublingCubeOwner() {
        Player redPlayer = new Player(Checker.RED,1);
        Player whitePlayer = new Player(Checker.WHITE, 0);
        assertTrue(doublingCube.isDoublingCubeOwner(redPlayer));
        assertFalse(doublingCube.isDoublingCubeOwner(whitePlayer));
    }

    @Test
    void doubleRefused() {
        doublingCube.doubleRefused();
        assertTrue(doublingCube.isDoublingRefused());
    }


    @Test
    void getDoublingCubeValue() {
        doublingCube.doubleStakes();
        assertEquals(doublingCube.getDoublingCubeValue(), 2);
    }

    @Test
    void isDoublingRefused() {
        assertFalse(doublingCube.isDoublingRefused());
    }
}