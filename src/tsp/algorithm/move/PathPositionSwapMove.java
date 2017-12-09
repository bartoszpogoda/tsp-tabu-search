package tsp.algorithm.move;

import tsp.algorithm.Path;

public class PathPositionSwapMove implements Move {

	private int pathIdA, pathIdB;

	public PathPositionSwapMove(int pathIdA, int pathIdB) {
		this.pathIdA = pathIdA;
		this.pathIdB = pathIdB;
	}

	@Override
	public void applyOn(Path path) {
		int cityA = path.getCity(pathIdA);
		path.setCity(pathIdA, path.getCity(pathIdB));
		path.setCity(pathIdB, cityA);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PathPositionSwapMove) {
			PathPositionSwapMove anotherMove = (PathPositionSwapMove) obj;
			if ((this.pathIdA == anotherMove.pathIdA && this.pathIdB == anotherMove.pathIdB)
					|| this.pathIdA == anotherMove.pathIdB && this.pathIdB == anotherMove.pathIdA) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
