import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointTest {
	private Point _point;
	
	@BeforeEach
	void setUp(){
		_point = new Point(1);
	}

	@Test
	void testAddCheckersChecker() {
		_point.addCheckers(Checker.WHITE);
		assertEquals(_point.getCheckerCount(), 1);
		assertEquals(_point.getResidentColour(), Checker.WHITE);
		
		_point.addCheckers(Checker.WHITE);
		assertEquals(_point.getCheckerCount(), 2);
		assertEquals(_point.getResidentColour(), Checker.WHITE);
	}

	@Test
	void testAddCheckersCheckerInt() {
		_point.addCheckers(Checker.WHITE, 5);
		assertEquals(_point.getCheckerCount(), 5);
		assertEquals(_point.getResidentColour(), Checker.WHITE);
	}

	/*@Test
	void testGetWhitePointNumber() {
		assertEquals(_point.getWhitePointNumber(), 1);
	}

	@Test
	void testGetRedPointNumber() {
		assertEquals(_point.getRedPointNumber(), 24);
	}*/

	@Test
	void testGetResidentColour() {
		assertEquals(_point.getResidentColour(), Checker.EMPTY);
	}

	@Test
	void testGetCheckerCount() {
		assertEquals(_point.getCheckerCount(), 0);
	}
}
