package tsp;

import java.io.IOException;

import tsp.algorithm.Algorithm;
import tsp.algorithm.path.Path;
import tsp.algorithm.path.PathGenerator;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class Main {

	public static void main(String[] args) throws IOException {
		InstanceFileReader instanceFileReader = new InstanceFileReader();
		Instance instance = instanceFileReader.read("input/bays29.tsp");

		System.out.println(instance.toString());
		
		PathGenerator pathGenerator = new PathGenerator();
		Path randomPath = pathGenerator.generateRandomPath(instance);
		System.out.println(randomPath);
		
		AlgorithmTerminator algorithmTerminator = new AlgorithmTerminator();
		algorithmTerminator.setTimeLimitMs(10000);
		
		Algorithm algorithm = new Algorithm();
		algorithm.setAlgorithmTerminator(algorithmTerminator);
		algorithm.execute(instance);
		
		
	}

}
