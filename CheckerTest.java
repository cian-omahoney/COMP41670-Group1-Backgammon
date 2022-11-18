import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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