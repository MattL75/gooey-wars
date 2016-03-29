package com.gooeywars.pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Grid {
	public Array<Node> nodeGrid;
	public Vector2 worldSize;
	public float nodeRadius;
	
	private int gridX, gridY;
	
	public Grid(Vector2 worldSize, float nodeRadius) {
		this.worldSize = worldSize;
		this.nodeRadius = nodeRadius;
		createGrid();
	}
	
	private void createGrid() {
		
	}
}
