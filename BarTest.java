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
        assertTrue(_barWhite.getResidentColour().equals(Checker.WHITE));
        assertTrue(_barRed.getResidentColour().equals(Checker.RED));
    }

    @Test
    void testAddCheckers() {
        _barWhite.addCheckers();
        assertEquals(_barWhite.isEmpty(), false);
        assertEquals(_barWhite.getCheckerCount(), 1);
    }
}
