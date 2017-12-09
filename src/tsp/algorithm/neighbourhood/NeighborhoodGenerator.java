package tsp.algorithm.neighbourhood;

import java.util.List;

import tsp.algorithm.move.Move;
import tsp.algorithm.path.Path;

/***
 * Interface for strategy of generating move neighborhood for given path
 * 
 * @author Student225988
 *
 */
public interface NeighborhoodGenerator {
	List<Move> generateNeighborhood(Path path);
}
