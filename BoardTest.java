import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit tests for {@code Board} class.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
class BoardTest {
    private Board board;
    private Player whitePlayer;
    private Player redPlayer;
    private DoublingCube doublingCube;

    @BeforeEach
    void setUp() {
        this.whitePlayer = new Player(Checker.WHITE, 0);
        this.redPlayer= new Player(Checker.RED, 1);
        this.doublingCube = new DoublingCube();
        this.board = new Board();
    }

    @Test
    void getMatchScore() {
        assertEquals(board.getMatchScore(redPlayer, whitePlayer, doublingCube),2);
    }

    @Test
    void isGammoned() {
        assertTrue(board.isGammoned());
    }

    @Test
    void isBackgammoned() {
        assertFalse(board.isBackgammoned(redPlayer, whitePlayer));
    }

    @Test
    void isMatchOver() {
        assertFalse(board.isMatchOver(redPlayer,whitePlayer,doublingCube));
    }

    @Test
    void getWinner() {
        assertEquals(board.getWinner(redPlayer,whitePlayer,redPlayer), redPlayer);
    }

    @Test
    void isBarEmpty() {
        assertTrue(board.isBarEmpty(redPlayer));
        assertTrue(board.isBarEmpty(whitePlayer));
    }

    @Test
    void isBearOff() {
        assertFalse(board.isBearOff(redPlayer));
        assertFalse(board.isBearOff(whitePlayer));
    }

    @Test
    void getPipCount() {
        assertEquals(board.getPipCount(redPlayer), 167);
        assertEquals(board.getPipCount(whitePlayer), 167);
    }

    @Test
    void getValidBarMoves() {
        assertEquals(board.getValidBarMoves(redPlayer), new ArrayList<>());
        assertEquals(board.getValidBarMoves(whitePlayer), new ArrayList<>());
    }

    @Test
    void getValidMoves() {
        assertEquals(board.getValidMoves(redPlayer).size(), 0);
        assertEquals(board.getValidMoves(whitePlayer).size(), 0);
    }

    @Test
    void moveChecker() {
        ArrayList<Integer>  moveSeq = new ArrayList<>();
        moveSeq.add(6);
        moveSeq.add(4);
        moveSeq.add(3);
        board.moveChecker(moveSeq, whitePlayer);
        assertEquals(board.getPipCount(whitePlayer), 164);
    }
}