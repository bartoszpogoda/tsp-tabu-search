package tsp.algorithm.tabu;

import tsp.algorithm.move.Move;

/***
 * Interface for tabu list
 * 
 * @author Student225988
 */
public interface TabuList {
	void addMove(Move move);
	
	void setCadency(int cadency);
	
	void clear();
	
	boolean isTabu(Move move);
}
