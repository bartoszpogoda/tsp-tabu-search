package tsp.algorithm.move;

import tsp.algorithm.path.Path;
import tsp.instance.Instance;

/***
 * Interface for strategy of move that changes the path
 * 
 * @author Student225988
 *
 */
public interface Move {
	void applyOn(Path path);

	/**
	 * Calculates difference between total distance after move application and
	 * total distance before. The less is the value the better the move is
	 * 
	 * @param path
	 * @return
	 */
	double distanceChange(Instance instance, Path path);

	@Override
	boolean equals(Object obj);
}
