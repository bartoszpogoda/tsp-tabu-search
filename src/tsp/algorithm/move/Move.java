package tsp.algorithm.move;

import tsp.algorithm.Path;

/***
 * Interface for strategy of move that changes the path
 * 
 * @author Student225988
 *
 */
public interface Move {
	void applyOn(Path path);
	
	@Override
	boolean equals(Object obj);
}
