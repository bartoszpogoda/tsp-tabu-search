package tsp;

import java.io.IOException;

import tsp.algorithm.Algorithm;
import tsp.algorithm.BestDistanceHistory;
import tsp.algorithm.neighbourhood.NeighborhoodGenerator;
import tsp.algorithm.neighbourhood.PathPositionSwapNeighborhoodGenerator;
import tsp.algorithm.tabu.TabuList;
import tsp.algorithm.tabu.TabuListImpl;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class Main {

	public static void main(String[] args) throws IOException {
		InstanceFileReader instanceFileReader = new InstanceFileReader();
		Instance instance = instanceFileReader.read("input/bays29.tsp");

		AlgorithmTerminator algorithmTerminator = new AlgorithmTerminator();
		algorithmTerminator.setTimeLimitMs(60000);
		
		TabuList tabuList = new TabuListImpl(29*3);
		
		NeighborhoodGenerator neighborhoodGenerator = new PathPositionSwapNeighborhoodGenerator();
		
		Algorithm algorithm = new Algorithm(tabuList, neighborhoodGenerator);
		algorithm.setAlgorithmTerminator(algorithmTerminator);
		
		BestDistanceSampler bestDistanceSampler = new BestDistanceSampler(new BestDistanceHistory(), algorithm);
		algorithm.setBestDistanceSampler(bestDistanceSampler);
		
		algorithm.execute(instance);
	}

}
