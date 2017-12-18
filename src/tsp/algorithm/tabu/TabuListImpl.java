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
	
	@Override
	public String toString() {
		return "LimitedQueue (" + tabuMoves.getLimit() + ")";
	}

	@Override
	public void setCadency(int cadency) {
		tabuMoves = new LimitedQueue<>(cadency);
		
	}

	@Override
	public void clear() {
		tabuMoves.clear();
	}
}
