package test;

import tsp.algorithm.path.Path;
import tsp.instance.Instance;

public class PredefinedTestObjects {
	static Instance createPredefinedInstance() {
		Instance instance = new Instance(5);

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				instance.setDistance(i, j, 1);
			}
		}

		instance.setDistance(0, 1, 5);
		instance.setDistance(1, 2, 4);
		instance.setDistance(2, 3, 3);
		instance.setDistance(3, 4, 7);
		instance.setDistance(4, 0, 20);
		
		return instance;
	}
	
	static Path createPredefinedPath() {
		Path path = new Path(5 + 1);

		path.setStartCity(0);
		path.setCity(1, 1);
		path.setCity(2, 2);
		path.setCity(3, 3);
		path.setCity(4, 4);
		
		return path;
	}
}
