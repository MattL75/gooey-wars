package com.gooeywars.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Main;

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
		System.out.println(gridSizeX + " " + gridSizeY);
		int startPosX = 0;
		int startPosY = 0;
			
		for (int x = 0; x < gridSizeX; x++) {
			for (int y = 0; y < gridSizeY; y++) {
				nodeGrid.add(new Array<Node>());
				nodeGrid.get(x).add(new Node(true, new Vector2(startPosX, startPosY)));	
				
				startPosY += Math.round(nodeDiameter);
			}
			startPosY = 0;
			startPosX += Math.round(nodeDiameter);
		}
	}

	public Node nodeFromWorldPoint(Vector2 vec) {
		float percentX = (vec.x + worldSize.x / 2) / worldSize.x;
		float percentY = (vec.y + worldSize.y / 2) / worldSize.y;
		
		percentX = Math.max(0, Math.min(1, percentX));
		percentY = Math.max(0, Math.min(1, percentY));
		
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
