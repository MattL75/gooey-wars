package com.gooeywars.pathfinding;

import com.badlogic.gdx.math.Vector2;

public class Node {
	private boolean isWalkable;
	private Vector2 worldPos;
	
	public Node() {
		this.isWalkable = false;
		this.worldPos = new Vector2(0, 0);
	}
	
	public Node(boolean walkable, Vector2 worldPos) {
		this.isWalkable = walkable;
		this.worldPos = worldPos;
	}

	public boolean isWalkable() {
		return isWalkable;
	}

	public void setWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
	}

	public Vector2 getWorldPos() {
		return worldPos;
	}

	public void setWorldPos(Vector2 worldPos) {
		this.worldPos = worldPos;
	}
} 
