package tsp.algorithm.neighbourhood;

import java.util.List;

import tsp.algorithm.Path;
import tsp.algorithm.move.Move;

/***
 * Interface for strategy of generating move neighborhood for given path
 * 
 * @author Student225988
 *
 */
public interface Neighborhood {
	List<Move> generateNeighborhood(Path path);
}
