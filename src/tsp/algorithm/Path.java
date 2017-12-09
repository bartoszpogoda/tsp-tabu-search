package tsp.algorithm;

import tsp.algorithm.move.Move;

public class Path {
	private int[] path = null;
	
	public Path(int length) {
		this.path = new int[length];
	}
	
	public int getCity(int pathId) {
		return path[pathId];
	}
	
	public void setCity(int pathId, int city) {
		path[pathId] = city;
	}

	public void applyMove(Move move) {
		move.applyOn(this);
	}
}
