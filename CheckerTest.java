// Team 1 Backgammon Project
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test for Checker class.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
class CheckerTest {
    private Checker _testCheckerWhite;
    private Checker _testCheckerEmpty;

    @BeforeEach
    void setUp() {
        _testCheckerWhite = Checker.WHITE;
        _testCheckerEmpty = Checker.EMPTY;
    }

    @Test
    @DisplayName("Get Checker symbol.")
    void testGetSymbol() {
        assertEquals(_testCheckerWhite.getSymbol(), UI.WHITE_CHECKER_COLOUR + "O" + UI.CLEAR_COLOURS);
        assertEquals(_testCheckerEmpty.getSymbol(), "-");
    }
}