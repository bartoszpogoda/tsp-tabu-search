package tsp.algorithm;

import java.util.List;

import tsp.algorithm.move.Move;
import tsp.algorithm.neighborhood.NeighborhoodGenerator;
import tsp.algorithm.path.Path;
import tsp.algorithm.path.PathGenerator;
import tsp.algorithm.tabu.TabuList;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.algorithm.thread.DiversificationChecker;
import tsp.instance.Instance;

public class Algorithm {
	private volatile boolean running = true;
	
	private volatile boolean diversificationFlag = false;

	private Path currentPath;
	private double currentDistance = Double.MAX_VALUE;

	private Path currentBestPath;
	private double currentBestDistance = Double.MAX_VALUE;
	
	private double currentDiversificationBestDistance = Double.MAX_VALUE;

	private PathGenerator pathGenerator;

	// dependencies
	private AlgorithmTerminator algorithmTerminator;
	private BestDistanceSampler bestDistanceSampler;
	private DiversificationChecker diversificationChecker;

	private TabuList tabuList;
	private NeighborhoodGenerator neighborhoodGenerator;

	public void setAlgorithmTerminator(AlgorithmTerminator algorithmTerminator) {
		this.algorithmTerminator = algorithmTerminator;
		algorithmTerminator.setAlgorithm(this);
	}
	
	public void setDiversificationChecker(DiversificationChecker diversificationChecker) {
		this.diversificationChecker = diversificationChecker;
		diversificationChecker.setAlgorithm(this);
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
	
	public double getCurrentDiversificationBestDistance() {
		return currentDiversificationBestDistance;
	}

	public synchronized Path execute(Instance instance) {
		algorithmTerminator.start();
		if(diversificationChecker != null) {
			diversificationChecker.start();
		}

		initStartingPath(instance);
		
		bestDistanceSampler.start();

		tabuSearchLoop(instance);

		if(diversificationChecker != null) {
			diversificationChecker.terminate();
		}
		bestDistanceSampler.terminate();

		return currentBestPath;
	}

	public void initStartingPath(Instance instance) {
		currentPath = pathGenerator.generateGreedyPath(instance);
		currentDistance = instance.calculateTotalDistance(currentPath);
		
		currentBestPath = new Path(currentPath);
		currentBestDistance = currentDistance;
	}

	public void tabuSearchLoop(Instance instance) {
		while (running) {
			
			if(diversificationFlag) {
				diversificate(instance);
			}
			
			List<Move> neighborhood = neighborhoodGenerator.generateNeighborhood(currentPath);

			Move bestMove = null;
			double bestDistanceChange = Double.MAX_VALUE;
			for (Move move : neighborhood) {
				double distanceChange = move.distanceChange(instance, currentPath);
				if (distanceChange < bestDistanceChange && !tabuList.isTabu(move)) {
					bestMove = move;
					bestDistanceChange = distanceChange;
				}
			}

			tabuList.addMove(bestMove);
			bestMove.applyOn(currentPath);
			currentDistance += bestDistanceChange;
			
			if(currentDistance < currentBestDistance) {
				currentBestDistance = currentDistance;
				currentBestPath = new Path(currentPath);
			}
			
			if(currentDistance < currentDiversificationBestDistance) {
				currentDiversificationBestDistance = currentDistance;
			}

		}
	}

	private void diversificate(Instance instance) {
		currentPath = pathGenerator.generateRandomPath(instance);
		currentDistance = instance.calculateTotalDistance(currentPath);
		
		currentDiversificationBestDistance = Double.MAX_VALUE;
		tabuList.clear();
		
		diversificationFlag = false;
		System.out.println("Diversification executed");
	}

	public void terminate() {
		running = false;
	}

	public void setDiversificationFlag() {
		diversificationFlag = true;
	}
}
