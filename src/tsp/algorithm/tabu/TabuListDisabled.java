package tsp.algorithm.tabu;

import tsp.algorithm.move.Move;

public class TabuListDisabled implements TabuList {

	@Override
	public void addMove(Move move) {
		// Do nothing
	}

	@Override
	public boolean isTabu(Move move) {
		return false;
	}
	
	@Override
	public void setCadency(int cadency) {
		// Do nothing
		
	}

	@Override
	public void clear() {
		// Do nothing
		
	}

	@Override
	public String toString() {
		return "Disabled";
	}
}
