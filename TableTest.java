import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TableTest {
    private Table _table;
    private Point[] _points;

    @BeforeEach
    void setUp(){
        _points=new Point[6];
        for(int i=0; i<6; i++) {
            _points[i] = new Point(i);
            _points[i].addCheckers(Checker.WHITE,i);
        }
        _table = new Table(0,_points);

    }

    @Test
    void testGetPointMaxLength() {
        assertEquals(_table.getPointMaxLength(), 5);
    }

    @Test
    void testGetPointsRow(){
        String O = Checker.WHITE.getSymbol();
        String[][] testSetup={{" ",O,O,O,O,O},{" "," ",O,O,O,O},{" "," "," ",O,O,O},{" "," "," "," ",O,O},{" "," "," "," "," ",O},{" "," "," "," "," "," "}};

        for (int i=0;i<6;i++) {
            String[] tableRow = _table.getPointsRow(i);
            assertArrayEquals(testSetup[i], tableRow);
        }
    }
}
