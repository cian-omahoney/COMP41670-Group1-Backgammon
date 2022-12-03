// Team 1 Backgammon Project
// By 
/***@author Cian O'Mahoney Github:cian-omahoney 
 *  @author Ciarán Cullen  Github:TangentSplash
*/

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointTest {
	private Point _point;
	
	@BeforeEach
	void setUp(){
		_point = new Point(0);
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

	@Test
	void testRemoveChecker() {
		_point.addCheckers(Checker.WHITE, 3);
		_point.removeChecker();
		assertEquals(_point.getCheckerCount(), 2);
		assertEquals(_point.getResidentColour(), Checker.WHITE);

		_point.removeChecker();
		_point.removeChecker();
		_point.removeChecker();

		assertEquals(_point.getCheckerCount(), 0);
		assertTrue(_point.isEmpty());
		assertEquals(_point.getResidentColour(), Checker.EMPTY);
	}

	@Test
	void testGetPointNumber_colourVersion() {
		Player whitePlayer =  new Player(Checker.WHITE, 0);
		Player redPlayer =  new Player(Checker.RED, 1);
		assertEquals(_point.getPointNumber(whitePlayer), 1);
		assertEquals(_point.getPointNumber(redPlayer), 24);
		assertEquals(_point.getPointNumber(new Player(Checker.EMPTY, 1)), 0);
	}

	@Test
	void testGetPointNumber_integerVersion() {
		assertEquals(_point.getPointNumber(0), 1);
		assertEquals(_point.getPointNumber(1), 24);
		assertEquals(_point.getPointNumber(2), 0);
	}

	@Test
	void testGetResidentColour() {
		assertEquals(_point.getResidentColour(), Checker.EMPTY);
	}

	@Test
	void testGetCheckerCount() {
		assertEquals(_point.getCheckerCount(), 0);
	}

	@Test
	void testIsEmpty() {
		assertTrue(_point.isEmpty());
		_point.addCheckers(Checker.WHITE);
		assertFalse(_point.isEmpty());
	}
}
