// Team 1 Backgammon Project
// By 
/***@author Cian O'Mahoney Github:cian-omahoney 
 *  @author Ciarán Cullen  Github:TangentSplash
*/

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
