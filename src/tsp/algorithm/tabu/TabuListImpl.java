package tsp.algorithm.tabu;

import tsp.algorithm.move.Move;

public class TabuListImpl implements TabuList {

	LimitedQueue<Move> tabuMoves;
	
	public TabuListImpl(int tabuListSize) {
		tabuMoves = new LimitedQueue<>(tabuListSize);
	}
	
	@Override
	public void addMove(Move move) {
		tabuMoves.add(move);
	}

	@Override
	public boolean isTabu(Move move) {
		return tabuMoves.contains(move);
	}

}
