package tsp.algorithm.move;

import tsp.algorithm.path.Path;
import tsp.instance.Instance;

public class InsertMove implements Move {

	private int pathIdToMove;
	private int pathIdInsert;
	
	public InsertMove(int pathIdToMove, int pathIdInsert) {
		this.pathIdToMove = pathIdToMove;
		this.pathIdInsert = pathIdInsert;
	}

	@Override
	public void applyOn(Path path) {
		int movedCity = path.getCity(pathIdToMove);
		
		if(pathIdToMove < pathIdInsert) {
			for(int i = pathIdToMove ; i < pathIdInsert; i++) {
				path.setCity(i, path.getCity(i+1));
			}
		} else {
			for(int i = pathIdToMove ; i > pathIdInsert; i--) {
				path.setCity(i, path.getCity(i-1));
			}
		}
		
		path.setCity(pathIdInsert, movedCity);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof InsertMove) {
			InsertMove anotherMove = (InsertMove) obj;
			if (pathIdToMove == anotherMove.pathIdToMove && pathIdInsert == anotherMove.pathIdInsert) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	@Override
	public double distanceChange(Instance instance, Path path) {
		double lengthOfRemovedEdges = 0;
		double lengthOfAddedEdges = 0;
		

		lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdToMove-1), path.getCity(pathIdToMove));
		lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdToMove), path.getCity(pathIdToMove+1));

		lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdToMove-1), path.getCity(pathIdToMove+1));
		
		if(pathIdToMove < pathIdInsert) {
			lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdInsert), path.getCity(pathIdInsert+1));
			
			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdInsert), path.getCity(pathIdToMove));
			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdToMove), path.getCity(pathIdInsert+1));
		} else {
			lengthOfRemovedEdges += instance.getDistance(path.getCity(pathIdInsert-1), path.getCity(pathIdInsert));
			
			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdToMove), path.getCity(pathIdInsert));
			lengthOfAddedEdges += instance.getDistance(path.getCity(pathIdInsert-1), path.getCity(pathIdToMove));
		}
		
		
		return lengthOfAddedEdges - lengthOfRemovedEdges;
	}

}
