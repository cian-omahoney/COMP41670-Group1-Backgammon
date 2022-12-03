// Team 1 Backgammon Project
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Junit tests for {@code Bar} class.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
public class BarTest {
    private Bar _barWhite;
    private Bar _barRed;

    @BeforeEach
    void setUp() {
        Checker _barColour=Checker.WHITE;
        _barWhite = new Bar(_barColour);
        _barColour=Checker.RED;
        _barRed = new Bar(_barColour);
    }

    @Test
    void testGetResidentColour(){
        assertEquals(_barWhite.getResidentColour(), Checker.WHITE);
        assertEquals(_barRed.getResidentColour(), Checker.RED);
    }

    @Test
    void testAddCheckers() {
        _barWhite.addCheckers();
        assertFalse(_barWhite.isEmpty());
        assertEquals(_barWhite.getCheckerCount(), 1);
    }
}
