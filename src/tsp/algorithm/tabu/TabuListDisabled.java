package tsp.algorithm.tabu;

import tsp.algorithm.move.Move;

public class TabuListDisabled implements TabuList {

	@Override
	public void addMove(Move move) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isTabu(Move move) {
		return false;
	}

}
