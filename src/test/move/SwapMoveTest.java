package test.move;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import test.PredefinedTestObjects;
import tsp.algorithm.move.Move;
import tsp.algorithm.move.SwapMove;
import tsp.algorithm.path.Path;
import tsp.instance.Instance;

public class SwapMoveTest {

	private Instance instance = null;
	private Path path = null;

	@Before
	public void setUp() {
		instance = PredefinedTestObjects.createPredefinedInstanceA();
		path = PredefinedTestObjects.createPredefinedPath();
	}

	@Test
	public void distanceChangeAdjacentCitiesTest() {
		Move move = new SwapMove(1, 2);

		double distanceChange = move.distanceChange(instance, path);
		assertEquals(-9, distanceChange, 0.01);
	}

	@Test
	public void distanceChangeNotAdjacentCitiesTest() {
		Move move = new SwapMove(2, 4);

		double distanceChange = move.distanceChange(instance, path);
		assertEquals(-30, distanceChange, 0.01);
	}

}
