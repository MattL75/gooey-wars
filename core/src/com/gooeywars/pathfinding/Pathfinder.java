package com.gooeywars.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Pathfinder {
	private Array<Node> closed = new Array<Node>();
	private Array<Node> open = new Array<Node>();
	Grid grid = new Grid(new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), 10);
	
	public Pathfinder() {
		setupArrays();
	}
	
	private boolean isEndPosValid() {
		//Does the node above the end point contain an obstacle?
		//If false, return. Or maybe move to closest node
		//If true, keeps going
		return false;
	}
	
	public void setupArrays() {
		//Check if a node contains an obstacle
		//if so, add node to closed
		//if not, add node to open
	}

	public void findPath(Vector2 iniPos, Vector2 endPos) {
		Node initialPos = grid.nodeFromWorldPoint(iniPos);
	}
	
	public void clearArrays() {
		closed.clear();
		open.clear();
	}	
}
