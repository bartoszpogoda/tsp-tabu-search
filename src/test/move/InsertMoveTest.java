package test.move;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import test.PredefinedTestObjects;
import tsp.algorithm.move.InsertMove;
import tsp.algorithm.move.Move;
import tsp.algorithm.path.Path;
import tsp.instance.Instance;

public class InsertMoveTest {

	private Instance instance = null;
	private Path path = null;

	@Before
	public void setUp() {
		instance = PredefinedTestObjects.createPredefinedInstanceA();
		path = PredefinedTestObjects.createPredefinedPath();
	}
	
	@Test
	public void shouldApplyTest1() {
		Move move = new InsertMove(1, 2);
		
		move.applyOn(path);
		
		assertEquals(0, path.getCity(0));
		assertEquals(2, path.getCity(1));
		assertEquals(1, path.getCity(2));
		assertEquals(3, path.getCity(3));
		assertEquals(4, path.getCity(4));
		assertEquals(0, path.getCity(5));
	}
	
	@Test
	public void shouldApplyTest2() {
		Move move = new InsertMove(4, 2);
		
		move.applyOn(path);
		
		assertEquals(0, path.getCity(0));
		assertEquals(1, path.getCity(1));
		assertEquals(4, path.getCity(2));
		assertEquals(2, path.getCity(3));
		assertEquals(3, path.getCity(4));
		assertEquals(0, path.getCity(5));
	}
	
	@Test
	public void shouldApplyTest3() {
		Move move = new InsertMove(1, 4);
		
		move.applyOn(path);
		
		assertEquals(0, path.getCity(0));
		assertEquals(2, path.getCity(1));
		assertEquals(3, path.getCity(2));
		assertEquals(4, path.getCity(3));
		assertEquals(1, path.getCity(4));
		assertEquals(0, path.getCity(5));
	}

	@Test
	public void distanceChangeInsertedAfterAdjacentTest() {
		Move move = new InsertMove(1, 2);

		double distanceChange = move.distanceChange(instance, path);
		assertEquals(-9, distanceChange, 0.01);
	}

	@Test
	public void distanceChangeInsertedAfterNotAdjacentTest() {
		Move move = new InsertMove(2, 4);

		double distanceChange = move.distanceChange(instance, path);
		assertEquals(-24, distanceChange, 0.01);
	}

	@Test
	public void distanceChangeInsertedBeforeAdjacentTest() {
		Move move = new InsertMove(4, 3);

		double distanceChange = move.distanceChange(instance, path);
		assertEquals(-27, distanceChange, 0.01);
	}

	
	@Test
	public void distanceChangeInsertedBeforeNotAdjacentTest() {
		Move move = new InsertMove(4, 2);

		double distanceChange = move.distanceChange(instance, path);
		assertEquals(-28, distanceChange, 0.01);
	}

	
}
