package tsp.instance;

/***
 * Class responsible for holding TS Problem instance data
 * 
 * @author Student225988
 */
public class Instance {
	private double adjacencyMatrix[][];
	private int numberOfCities;

	public Instance(int numberOfCities) {
		this.numberOfCities = numberOfCities;

		adjacencyMatrix = new double[numberOfCities][numberOfCities];
	}

	public int getNumberOfCities() {
		return numberOfCities;
	}

	public void setDistance(int startCity, int endCity, double distance) {
		adjacencyMatrix[startCity][endCity] = distance;
	}

	public double getDistance(int startCity, int endCity) {
		return adjacencyMatrix[startCity][endCity];
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0 ; i < numberOfCities ; i++) {
			for(int j = 0 ; j < numberOfCities ; j++) {
				stringBuilder.append(String.format("%8.2f", getDistance(i, j)));
			}
			stringBuilder.append("\n");
		}
		
		return stringBuilder.toString();
	}
}
