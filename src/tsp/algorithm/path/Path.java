package tsp.algorithm.path;

import tsp.algorithm.move.Move;

public class Path {
	private int[] path = null;

	public Path(int length) {
		this.path = new int[length];
	}

	public int getLength() {
		return path.length;
	}
	
	public int getCity(int pathId) {
		return path[pathId];
	}

	public void setCity(int pathId, int city) {
		if (pathId == 0 || pathId == path.length - 1) {
			throw new StartCityCorruptionException();
		}

		path[pathId] = city;
	}

	public void setStartCity(int startCity) {
		path[0] = startCity;
		path[path.length - 1] = startCity;
	}

	public void applyMove(Move move) {
		move.applyOn(this);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < path.length; i++) {
			builder.append(path[i] + (i == path.length - 1 ? "" : " -> "));
		}
		return builder.toString();
	}
}
