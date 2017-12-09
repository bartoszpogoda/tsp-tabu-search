package tsp.algorithm.move;

import tsp.algorithm.path.Path;
import tsp.instance.Instance;

public class PathPositionSwapMove implements Move {

	private int pathIdSmaller, pathIdBigger;

	public PathPositionSwapMove(int pathIdA, int pathIdB) {
		this.pathIdSmaller = Math.min(pathIdA, pathIdB);
		this.pathIdBigger = Math.max(pathIdA, pathIdB);
	}

	@Override
	public void applyOn(Path path) {
		int cityA = path.getCity(pathIdSmaller);
		path.setCity(pathIdSmaller, path.getCity(pathIdBigger));
		path.setCity(pathIdBigger, cityA);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PathPositionSwapMove) {
			PathPositionSwapMove anotherMove = (PathPositionSwapMove) obj;
			if (this.pathIdSmaller == anotherMove.pathIdSmaller && this.pathIdBigger == anotherMove.pathIdBigger) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public double distanceChange(Instance instance, Path path) {
		double lengthOfRemovedEdges = 0;
		double lengthOfAddedEdges = 0;

		if (pathIdSmaller == pathIdBigger) {
			return 0;
		} else if (pathIdSmaller == pathIdBigger - 1) {
			// Adjacent cities

			lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdSmaller - 1), path.getCity(pathIdSmaller));
			lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdSmaller), path.getCity(pathIdBigger));
			lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdBigger), path.getCity(pathIdBigger + 1));

			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdSmaller - 1), path.getCity(pathIdBigger));
			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdBigger), path.getCity(pathIdSmaller));
			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdSmaller), path.getCity(pathIdBigger + 1));
		} else {
			// Not adjacent cities

			lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdSmaller - 1), path.getCity(pathIdSmaller));
			lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdSmaller), path.getCity(pathIdSmaller + 1));
			lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdBigger - 1), path.getCity(pathIdBigger));
			lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdBigger), path.getCity(pathIdBigger + 1));

			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdSmaller - 1), path.getCity(pathIdBigger));
			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdBigger), path.getCity(pathIdSmaller + 1));
			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdBigger - 1), path.getCity(pathIdSmaller));
			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdSmaller), path.getCity(pathIdBigger + 1));
		}
		
		return lengthOfAddedEdges - lengthOfRemovedEdges;
	}

}
