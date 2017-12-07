package tsp.algorithm;

public class SwapMove implements Move {

	private int pathIdA, pathIdB;
	
	public SwapMove(int pathIdA, int pathIdB) {
		this.pathIdA = pathIdA;
		this.pathIdB = pathIdB;
	}
	
	@Override
	public void applyOn(Path path) {
		int cityA = path.getCity(pathIdA);
		path.setCity(pathIdA, path.getCity(pathIdB));
		path.setCity(pathIdB, cityA);
	}

}
