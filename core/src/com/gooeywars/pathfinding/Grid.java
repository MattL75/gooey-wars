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
	
	//The fix-it yourself algorithm AKA the billy mays algorithm
	public Node nodeFromWorldPoint(Vector2 vec) {
		int nodeX = 0, nodeY = 0;
		int coX = 0, coY = 0;
		for (nodeX = 0; nodeX <= vec.x; nodeX += nodeRadius * 2) {
			for (nodeY = 0; nodeY <= vec.y; nodeY += nodeRadius * 2) {
				
			}
		}
		nodeX = Math.round(nodeX - nodeRadius * 2);
		nodeY = Math.round(nodeY - nodeRadius * 2);
		
		for (int i = 0; i < nodeGrid.size; i++) {
			for (int j = 0; j < nodeGrid.get(i).size; j++) {
				if (nodeGrid.get(i).get(j).getWorldPos().x == nodeX && nodeGrid.get(i).get(j).getWorldPos().y == nodeY) {
					coX = i;
					coY = j;
				}
			}
		}
		return nodeGrid.get(coX).get(coY);
		
		/*/float percentX = (vec.x) / worldSize.x;
		float percentY = (vec.y) / worldSize.y;
		
		percentX = Math.min(Math.max(percentX, 0), 1);
		percentY = Math.min(Math.max(percentY, 0), 1);
		
		int x = Math.round((gridSizeX - 1) * percentX);
		int y = Math.round((gridSizeY - 1) * percentY);
		return nodeGrid.get(x).get(y);/*/
	}
	
	public float getNodeRadius() {
		return nodeRadius;
	}

	public void setNodeRadius(float nodeRadius) {
		this.nodeRadius = nodeRadius;
	}
	
	
}
