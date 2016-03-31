package com.gooeywars.pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Pathfinder {
	private Array<Node> closed = new Array<Node>();
	private Array<Node> open = new Array<Node>();
	Vector2 iniPos = new Vector2(0, 0);
	Vector2 endPos = new Vector2(0, 0);
	
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
	
	public void findPath() {
		//find the best path available
		//by using the arrays and A*
		//Execute path
	}

	public void findPath(Vector2 iniPos, Vector2 endPos) {
		this.iniPos = iniPos;
		this.endPos = endPos;
		//Does same as findPath
	}
	
	public void clearArrays() {
		closed.clear();
		open.clear();
	}
	
	public Vector2 getIniPos() {
		return iniPos;
	}

	public void setIniPos(Vector2 iniPos) {
		this.iniPos = iniPos;
	}

	public Vector2 getEndPos() {
		return endPos;
	}

	public void setEndPos(Vector2 endPos) {
		this.endPos = endPos;
	}
	
}
