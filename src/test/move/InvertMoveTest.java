package test.move;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import test.PredefinedTestObjects;
import tsp.algorithm.move.InvertMove;
import tsp.algorithm.move.Move;
import tsp.algorithm.path.Path;
import tsp.instance.Instance;


public class InvertMoveTest {
	private Instance instance = null;
	private Path path = null;

	@Before
	public void setUp() {
		instance = PredefinedTestObjects.createPredefinedInstanceB();
		path = PredefinedTestObjects.createPredefinedPath();
	}
	
	@Test
	public void shouldApplyTest1() {
		Move move = new InvertMove(1, 2);

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
		Move move = new InvertMove(4, 2);

		move.applyOn(path);
		
		assertEquals(0, path.getCity(0));
		assertEquals(1, path.getCity(1));
		assertEquals(4, path.getCity(2));
		assertEquals(3, path.getCity(3));
		assertEquals(2, path.getCity(4));
		assertEquals(0, path.getCity(5));
	}
	
	@Test
	public void shouldApplyTest3() {
		Move move = new InvertMove(1, 4);
		
		move.applyOn(path);
		
		assertEquals(0, path.getCity(0));
		assertEquals(4, path.getCity(1));
		assertEquals(3, path.getCity(2));
		assertEquals(2, path.getCity(3));
		assertEquals(1, path.getCity(4));
		assertEquals(0, path.getCity(5));
	}
	
	@Test
	public void shouldApplyTest4() {
		Move move = new InvertMove(1, 3);
		
		move.applyOn(path);
		
		assertEquals(0, path.getCity(0));
		assertEquals(3, path.getCity(1));
		assertEquals(2, path.getCity(2));
		assertEquals(1, path.getCity(3));
		assertEquals(4, path.getCity(4));
		assertEquals(0, path.getCity(5));
	}
	
	@Test
	public void shouldCalculateDistanceChangeCorrectly1() {
		Move move = new InvertMove(1, 2);

		double distanceChange = move.distanceChange(instance, path);
		assertEquals(-3, distanceChange, 0.01);
	}
	
	@Test
	public void shouldCalculateDistanceChangeCorrectly2() {
		Move move = new InvertMove(4, 2);

		double distanceChange = move.distanceChange(instance, path);
		assertEquals(4, distanceChange, 0.01);
	}
	
	@Test
	public void shouldCalculateDistanceChangeCorrectly3() {
		Move move = new InvertMove(1, 4);

		double distanceChange = move.distanceChange(instance, path);
		assertEquals(3, distanceChange, 0.01);
	}


}
