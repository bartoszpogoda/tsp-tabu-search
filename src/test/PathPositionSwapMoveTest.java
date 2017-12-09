package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tsp.algorithm.move.Move;
import tsp.algorithm.move.PathPositionSwapMove;
import tsp.algorithm.path.Path;
import tsp.instance.Instance;

public class PathPositionSwapMoveTest {

	private Instance instance = null;
	private Path path = null;

	@Before
	public void setUp() {
		instance = PredefinedTestObjects.createPredefinedInstance();
		path = PredefinedTestObjects.createPredefinedPath();
	}

	@Test
	public void distanceChangeAdjacentCitiesTest() {
		Move move = new PathPositionSwapMove(1, 2);

		int distanceChange = move.distanceChange(instance, path);
		assertEquals(-9, distanceChange);
	}

	@Test
	public void distanceChangeNotAdjacentCitiesTest() {
		Move move = new PathPositionSwapMove(2, 4);

		int distanceChange = move.distanceChange(instance, path);
		assertEquals(-30, distanceChange);
	}

}
