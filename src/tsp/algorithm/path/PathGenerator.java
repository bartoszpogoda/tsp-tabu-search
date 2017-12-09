package tsp.algorithm.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tsp.instance.Instance;

public class PathGenerator {
	public Path generateRandomPath(Instance instance) {
		Path randomPath = new Path(instance.getNumberOfCities() + 1);
		randomPath.setStartCity(0);

		Random random = new Random(System.currentTimeMillis());
		
		List<Integer> availableCities = new ArrayList<Integer>();
		for(int i = 1 ; i < instance.getNumberOfCities() ; i++) {
			availableCities.add(i);
		}
		
		int pathIndex = 1;
		while(!availableCities.isEmpty()) {
			int randomAvailableCitiesPosition = random.nextInt(availableCities.size());
			randomPath.setCity(pathIndex, availableCities.remove(randomAvailableCitiesPosition));
			pathIndex++;
		}
		
		return randomPath;
	}
	
	public Path generateGreedyPath(Instance instance) {
		return null;
	}
}
