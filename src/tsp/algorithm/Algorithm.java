package tsp.algorithm;

import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.instance.Instance;

public class Algorithm {
	private volatile boolean running = true;

	private AlgorithmTerminator algorithmTerminator;
	
	public void setAlgorithmTerminator(AlgorithmTerminator algorithmTerminator) {
		this.algorithmTerminator = algorithmTerminator;
		algorithmTerminator.setAlgorithm(this);
	}

	public int getCurrentBestDistance() {
		// TODO implement
		return -1;
	}
	
	public synchronized Path execute(Instance instance) {
		algorithmTerminator.start();
		
		localSearchLoop();
		
		return null;
	}
	
	public void localSearchLoop() {
		while(running) {
			try {
				Thread.sleep(500);
				System.out.println("Local search loop running...");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

	public void terminate() {
		running = false;
	}
}
