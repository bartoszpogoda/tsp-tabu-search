package tsp.algorithm.neighborhood;

import java.util.ArrayList;
import java.util.List;

import tsp.algorithm.move.InsertMove;
import tsp.algorithm.move.Move;
import tsp.algorithm.path.Path;

public class InsertNeighborhoodGenerator implements NeighborhoodGenerator {

	@Override
	public List<Move> generateNeighborhood(Path path) {
		List<Move> neighborhood = new ArrayList<>();

		for (int i = 1; i < path.getLength() - 1; i++) {
			for (int j = 1; j < path.getLength() - 1; j++) {
				if (i != j) {
					neighborhood.add(new InsertMove(i, j));
				}
			}
		}

		return neighborhood;
	}

	@Override
	public String toString() {
		return "Insert Moves";
	}

}
