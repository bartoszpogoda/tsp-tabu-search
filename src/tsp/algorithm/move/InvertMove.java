package tsp.algorithm.move;

import tsp.algorithm.path.Path;
import tsp.instance.Instance;

public class InvertMove implements Move {

	private int inversionRangeStart;
	private int inversionRangeEnd;

	public InvertMove(int rangeA, int rangeB) {
		this.inversionRangeStart = Math.min(rangeA, rangeB);
		this.inversionRangeEnd = Math.max(rangeA, rangeB);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof InvertMove) {
			InvertMove anotherMove = (InvertMove) obj;
			if (inversionRangeStart == anotherMove.inversionRangeStart
					&& inversionRangeEnd == anotherMove.inversionRangeEnd) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void applyOn(Path path) {

		for (int i = inversionRangeStart; i < (inversionRangeStart + inversionRangeEnd + 1) / 2; i++) {
			int citySwapBuffer = path.getCity(i);
			path.setCity(i, path.getCity(inversionRangeEnd + inversionRangeStart - i));
			path.setCity(inversionRangeEnd + inversionRangeStart - i, citySwapBuffer);
		}

	}

	@Override
	public double distanceChange(Instance instance, Path path) {
		double lengthOfRemovedEdges = 0;
		double lengthOfAddedEdges = 0;

		lengthOfRemovedEdges += instance.getDistance(path.getCity(inversionRangeStart - 1),
				path.getCity(inversionRangeStart));
		lengthOfRemovedEdges += instance.getDistance(path.getCity(inversionRangeEnd),
				path.getCity(inversionRangeEnd + 1));
		for (int i = inversionRangeStart; i < inversionRangeEnd; i++) {
			lengthOfRemovedEdges += instance.getDistance(path.getCity(i), path.getCity(i + 1));
		}

		lengthOfAddedEdges += instance.getDistance(path.getCity(inversionRangeStart - 1),
				path.getCity(inversionRangeEnd));
		lengthOfAddedEdges += instance.getDistance(path.getCity(inversionRangeStart),
				path.getCity(inversionRangeEnd + 1));
		for (int i = inversionRangeEnd; i > inversionRangeStart; i--) {
			lengthOfAddedEdges += instance.getDistance(path.getCity(i), path.getCity(i - 1));
		}

		return lengthOfAddedEdges - lengthOfRemovedEdges;
	}

}
