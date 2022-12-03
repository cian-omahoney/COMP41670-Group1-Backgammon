// Team 1 Backgammon Project
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test for CommandType class.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
class CommandTypeTest {
    private CommandType _testCommandType;

    @BeforeEach
    void setUp() {
        _testCommandType = CommandType.ROLL;
    }

    @Test
    void getRegex() {
        assertEquals(_testCommandType.getRegex(), "ROLL");
    }
}