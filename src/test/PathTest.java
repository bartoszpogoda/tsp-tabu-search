package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tsp.algorithm.path.Path;
import tsp.instance.Instance;

public class PathTest {

	private Instance instance = null;
	private Path path = null;

	@Before
	public void setUp() {
		instance = PredefinedTestObjects.createPredefinedInstance();
		path = PredefinedTestObjects.createPredefinedPath();
	}

	@Test
	public void totalDistanceTest() {
		double totalDistance = instance.calculateTotalDistance(path);
		
		assertEquals(39, totalDistance, 0.01);
	}
	
	@Test
	public void copiedPathShouldntBeReferenced() {
		Path copiedPath = new Path(path);
		
		// City at position 1 is originally 1
		path.setCity(1, 3);
		
		assertNotEquals(path.getCity(1), copiedPath.getCity(1));
	}

}
