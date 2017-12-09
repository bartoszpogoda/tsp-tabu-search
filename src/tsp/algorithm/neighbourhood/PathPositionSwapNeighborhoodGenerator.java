package tsp.algorithm.neighbourhood;

import java.util.ArrayList;
import java.util.List;

import tsp.algorithm.move.Move;
import tsp.algorithm.move.PathPositionSwapMove;
import tsp.algorithm.path.Path;

public class PathPositionSwapNeighborhoodGenerator implements NeighborhoodGenerator {

	@Override
	public List<Move> generateNeighborhood(Path path) {
		List<Move> neighborhood = new ArrayList<>();

		for (int i = 1; i < path.getLength() - 2; i++) {
			for (int j = i + 1; j < path.getLength() - 1; j++) {
				neighborhood.add(new PathPositionSwapMove(i, j));
			}
		}

		return neighborhood;
	}

}
