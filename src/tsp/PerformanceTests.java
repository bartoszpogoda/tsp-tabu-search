package tsp;

import java.io.IOException;

import tsp.algorithm.Algorithm;
import tsp.algorithm.neighborhood.InsertNeighborhoodGenerator;
import tsp.algorithm.neighborhood.InvertNeighborhoodGenerator;
import tsp.algorithm.neighborhood.NeighborhoodGenerator;
import tsp.algorithm.neighborhood.SwapNeighborhoodGenerator;
import tsp.algorithm.path.Path;
import tsp.algorithm.tabu.TabuList;
import tsp.algorithm.tabu.TabuListImpl;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.algorithm.thread.DiversificationChecker;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class PerformanceTests {
	public static void main(String[] args) throws IOException {
		
		// eil101.tsp
		long timeLimitMs = 6 * 60 * 1000;
//		run("input/eil101.tsp", timeLimitMs, new SwapNeighborhoodGenerator(), false, "101_swap_no");
//		run("input/eil101.tsp", timeLimitMs, new SwapNeighborhoodGenerator(), true, "101_swap_yes");
//		
//		run("input/eil101.tsp", timeLimitMs, new InsertNeighborhoodGenerator(), false, "101_ins_no");
//		run("input/eil101.tsp", timeLimitMs, new InsertNeighborhoodGenerator(), true, "101_ins_yes");
//		
//		run("input/eil101.tsp", timeLimitMs, new InvertNeighborhoodGenerator(), false, "101_inv_no");
//		run("input/eil101.tsp", timeLimitMs, new InvertNeighborhoodGenerator(), true, "101_inv_yes");
		
		
		// rgb403.atsp
		
//		run("input/rbg403.atsp", timeLimitMs, new SwapNeighborhoodGenerator(), false, "403_swap_no");
//		run("input/rbg403.atsp", timeLimitMs, new SwapNeighborhoodGenerator(), true, "403_swap_yes");
//		
//		run("input/rbg403.atsp", timeLimitMs, new InsertNeighborhoodGenerator(), false, "403_ins_no");
//		run("input/rbg403.atsp", timeLimitMs, new InsertNeighborhoodGenerator(), true, "403_ins_yes");
//		
//		run("input/rbg403.atsp", timeLimitMs, new InvertNeighborhoodGenerator(), false, "403_inv_no");
//		run("input/rbg403.atsp", timeLimitMs, new InvertNeighborhoodGenerator(), true, "403_inv_yes");
//		
		run("input/d1291.tsp", timeLimitMs, new SwapNeighborhoodGenerator(), false, "1291_swap_no");
		run("input/d1291.tsp", timeLimitMs, new SwapNeighborhoodGenerator(), true, "1291_swap_yes");
		
		run("input/d1291.tsp", timeLimitMs, new InsertNeighborhoodGenerator(), false, "1291_ins_no");
		run("input/d1291.tsp", timeLimitMs, new InsertNeighborhoodGenerator(), true, "1291_ins_yes");
		
		run("input/d1291.tsp", timeLimitMs, new InvertNeighborhoodGenerator(), false, "1291_inv_no");
		run("input/d1291.tsp", timeLimitMs, new InvertNeighborhoodGenerator(), true, "1291_inv_yes");
		
	}
	
	private static void run(String instanceFilename, long timeLimitMs, NeighborhoodGenerator neighborhoodGenerator, boolean diversification, String outputFilename) throws IOException {
		InstanceFileReader ifr = new InstanceFileReader();
		Instance instance = ifr.read(instanceFilename);
		
		TabuList tabuList = new TabuListImpl(instance.getNumberOfCities()*3);
		
		Algorithm algorithm = new Algorithm(tabuList, neighborhoodGenerator);
		
		AlgorithmTerminator algorithmTerminator = new AlgorithmTerminator();
		algorithmTerminator.setTimeLimitMs(timeLimitMs);
		algorithmTerminator.setAlgorithm(algorithm);
		algorithm.setAlgorithmTerminator(algorithmTerminator);
		
		BestDistanceSampler bestDistanceSampler = new BestDistanceSampler(algorithm);
		bestDistanceSampler.setFilename(outputFilename);
		
		algorithm.setBestDistanceSampler(bestDistanceSampler);
		
		if(diversification) {
			DiversificationChecker diversificationChecker = new DiversificationChecker(30000);
			diversificationChecker.setAlgorithm(algorithm);
			algorithm.setDiversificationChecker(diversificationChecker);
		}
		
		Path path = algorithm.execute(instance);
		System.out.println(path);
		
		System.out.println("Finished: " + instanceFilename + ";" + neighborhoodGenerator + ";" + (diversification ? "div" : "nodiv"));
	}
	
	
}
