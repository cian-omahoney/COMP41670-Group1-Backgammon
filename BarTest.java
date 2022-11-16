import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BarTest {

    @Test
    void testGetResidentColour(){
        Checker _barColour=Checker.WHITE;
        Bar bar=new Bar(_barColour);
        assertTrue(bar.getResidentColour().equals(_barColour));

        _barColour=Checker.RED;
        bar=new Bar(_barColour);
        assertTrue(bar.getResidentColour().equals(_barColour));
    }
}
