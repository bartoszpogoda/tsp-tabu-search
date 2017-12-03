package tsp;

import java.io.IOException;

import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class Main {

	public static void main(String[] args) throws IOException {
		InstanceFileReader instanceFileReader = new InstanceFileReader();
		Instance instance = instanceFileReader.read("input/bays29.tsp");

		System.out.println(instance.toString());
	}

}
