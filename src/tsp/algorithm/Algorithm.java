package tsp.algorithm;

import java.util.List;

import tsp.algorithm.move.Move;
import tsp.algorithm.neighbourhood.NeighborhoodGenerator;
import tsp.algorithm.path.Path;
import tsp.algorithm.path.PathGenerator;
import tsp.algorithm.tabu.TabuList;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.instance.Instance;

public class Algorithm {
	private volatile boolean running = true;

	private Path currentPath;
	private double currentDistance = Double.MAX_VALUE;

	private Path currentBestPath;
	private double currentBestDistance = Double.MAX_VALUE;

	private PathGenerator pathGenerator;

	// dependencies
	private AlgorithmTerminator algorithmTerminator;
	private BestDistanceSampler bestDistanceSampler;

	private TabuList tabuList;
	private NeighborhoodGenerator neighborhoodGenerator;

	public void setAlgorithmTerminator(AlgorithmTerminator algorithmTerminator) {
		this.algorithmTerminator = algorithmTerminator;
		algorithmTerminator.setAlgorithm(this);
	}

	public void setBestDistanceSampler(BestDistanceSampler bestDistanceSampler) {
		this.bestDistanceSampler = bestDistanceSampler;
	}

	public Algorithm(TabuList tabuList, NeighborhoodGenerator neighborhoodGenerator) {
		this.tabuList = tabuList;
		this.neighborhoodGenerator = neighborhoodGenerator;

		pathGenerator = new PathGenerator();
	}

	public double getCurrentBestDistance() {
		return currentBestDistance;
	}

	public synchronized Path execute(Instance instance) {
		algorithmTerminator.start();
		bestDistanceSampler.start();

		initStartingPath(instance);

		localSearchLoop(instance);

		bestDistanceSampler.terminate();

		return currentBestPath;
	}

	public void initStartingPath(Instance instance) {
		// TODO implement and generate greedy here
		currentPath = pathGenerator.generateRandomPath(instance);
		currentDistance = instance.calculateTotalDistance(currentPath);
		
		System.out.println("Init distance: " + currentDistance);

		currentBestPath = new Path(currentPath);
		currentBestDistance = currentDistance;
	}

	public void localSearchLoop(Instance instance) {
		while (running) {
			List<Move> neighborhood = neighborhoodGenerator.generateNeighborhood(currentPath);

			Move bestMove = null;
			double bestDistanceChange = Double.MAX_VALUE;
			for (Move move : neighborhood) {
				double distanceChange = move.distanceChange(instance, currentPath);
				if (distanceChange < bestDistanceChange) {
					bestMove = move;
					bestDistanceChange = distanceChange;
				}
			}
			
			bestMove.applyOn(currentPath);
			currentDistance += bestDistanceChange;
			
			if(currentDistance < currentBestDistance) {
				currentBestDistance = currentDistance;
				currentBestPath = new Path(currentPath);
			}

		}
	}

	public void terminate() {
		running = false;
	}
}
