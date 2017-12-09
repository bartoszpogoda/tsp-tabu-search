package tsp.algorithm.thread;

import tsp.algorithm.Algorithm;
import tsp.algorithm.BestDistanceHistory;

/***
 * Thread that samples current best distance and saves new probes to the
 * referenced BestDistanceHistory
 * 
 * @author Student225988
 *
 */
public class BestDistanceSampler extends Thread {

	private volatile boolean running = true;

	private int samplingIntervalMs = 1000;

	private BestDistanceHistory bestDistanceHistory;
	private Algorithm algorithm;

	public BestDistanceSampler(BestDistanceHistory bestDistanceHistory, Algorithm algorithm) {
		this.bestDistanceHistory = bestDistanceHistory;
		this.algorithm = algorithm;
	}

	public void setSamplingInterval(int ms) {
		samplingIntervalMs = ms;
	}

	public void terminate() {
		running = false;
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(samplingIntervalMs);

				int currentBestDistance = algorithm.getCurrentBestDistance();
				bestDistanceHistory.add(currentBestDistance);
				
				System.out.println("Best distance found so far: " + currentBestDistance);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
