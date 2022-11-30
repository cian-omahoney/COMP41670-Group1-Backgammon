// Team 1 Backgammon Project
// By 
/***@author Cian O'Mahoney Github:cian-omahoney 
 *  @author Ciarán Cullen  Github:TangentSplash
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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