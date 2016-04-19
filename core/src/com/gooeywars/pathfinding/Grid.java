package com.gooeywars.pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Grid {
	public Array<Array<Node>> nodeGrid;
	public Vector2 worldSize;
	public float nodeRadius;
	
	private float nodeDiameter;
	private int gridSizeX, gridSizeY;
	
	public Grid(Vector2 worldSize, float nodeRadius) {
		this.worldSize = worldSize;
		this.nodeRadius = nodeRadius;
		nodeGrid = new Array<Array<Node>>();
		createGrid();
	}
	
	private void createGrid() {
		nodeDiameter = nodeRadius * 2;
		gridSizeX = Math.round(worldSize.x / nodeDiameter);
		gridSizeY = Math.round(worldSize.y / nodeDiameter);
		int startPosX = 0;
		int startPosY = 0;
			
		for (int x = 0; x < gridSizeX; x++) {
			for (int y = 0; y < gridSizeY; y++) {
				nodeGrid.add(new Array<Node>());
				nodeGrid.get(x).add(new Node(true, new Vector2(startPosX, startPosY), x, y));	
				
				startPosY += Math.round(nodeDiameter);
			}
			startPosY = 0;
			startPosX += Math.round(nodeDiameter);
		}
	}

	public Array<Node> GetNeighbours(Node node) {
		Array<Node> NodeAr = new Array<Node>();
		
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (x == 0 && y == 0) {
					continue;
				}
				
				int checkX = node.gridX + x;
				int checkY = node.gridY + y;
				
				if (checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeY) {
					NodeAr.add(nodeGrid.get(checkX).get(checkY));
				}
			}
		}
		return NodeAr;
	}
	
	public Node nodeFromWorldPoint(Vector2 vec) {
		float percentX = (vec.x) / worldSize.x;
		float percentY = (vec.y) / worldSize.y;
		
		percentX = Math.min(Math.max(percentX, 0), 1);
		percentY = Math.min(Math.max(percentY, 0), 1);
		
		int x = Math.round((gridSizeX - 1) * percentX);
		int y = Math.round((gridSizeY - 1) * percentY);
		return nodeGrid.get(x).get(y);
	}
	
	public float getNodeRadius() {
		return nodeRadius;
	}

	public void setNodeRadius(float nodeRadius) {
		this.nodeRadius = nodeRadius;
	}
	
	
}
