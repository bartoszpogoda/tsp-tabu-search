package tsp.algorithm.thread;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

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
	private XYSeries plotSerie;
	private Algorithm algorithm;

	public BestDistanceSampler(BestDistanceHistory bestDistanceHistory, Algorithm algorithm) {
		this.bestDistanceHistory = bestDistanceHistory;
		this.algorithm = algorithm;
	}

	public void setPlotSerie(XYSeries plotSerie) {
		this.plotSerie = plotSerie;
	}
	
	public void setSamplingInterval(int ms) {
		samplingIntervalMs = ms;
	}

	public void terminate() {
		running = false;
	}

	@Override
	public void run() {
		int temp = 0;
		while (running) {
			try {
				Thread.sleep(samplingIntervalMs);

				double currentBestDistance = algorithm.getCurrentBestDistance();
				//double currentBestDistance = algorithm.getCurrentDistance();
				bestDistanceHistory.add(currentBestDistance);
				
				if(plotSerie != null) {
					plotSerie.add(temp++, currentBestDistance);
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
