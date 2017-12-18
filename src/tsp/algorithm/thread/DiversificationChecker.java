package tsp.algorithm.thread;

import tsp.algorithm.Algorithm;

/**
 * Thread that manages diversification. It periodically checks if current best
 * answer computed by an algorithm has improved since last check. If not- it
 * sets a diversification flag on the algorithm
 * 
 * @author Student225988
 *
 */
public class DiversificationChecker extends Thread {
	private long diversificationIntervalMs = 30000;

	private volatile boolean running = true;

	private double previousBestDistance = Double.MAX_VALUE;

	private Algorithm algorithm;

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public DiversificationChecker(long diversificationIntervalMs) {
		super();
		this.diversificationIntervalMs = diversificationIntervalMs;
	}

	public long getDiversificationIntervalMs() {
		return diversificationIntervalMs;
	}
	
	public void terminate() {
		running = false;
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(diversificationIntervalMs);

				if (algorithm != null) {
					double currentBestDistance = algorithm.getCurrentDiversificationBestDistance();

					if (currentBestDistance >= previousBestDistance) {
						algorithm.setDiversificationFlag();
						previousBestDistance = Double.MAX_VALUE;
					} else {
						previousBestDistance = currentBestDistance;
					}

				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return "Diversification (" + diversificationIntervalMs/1000 + "s)";
	}
}
