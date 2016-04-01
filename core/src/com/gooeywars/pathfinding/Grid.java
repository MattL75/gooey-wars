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
				
				//TODO this debugging more efficiently?
				if (Main.debug) {
					Pixmap pixmap = new Pixmap(Math.round(nodeDiameter), Math.round(nodeDiameter), Format.RGBA8888);
					pixmap.setColor(Color.RED);
					pixmap.drawRectangle(0, 0, Math.round(nodeDiameter), Math.round(nodeDiameter));
					Texture tex = new Texture(pixmap);
					pixmap.dispose();
					Main.findGameBox("game").getEntities().add(new Entity(new Sprite(tex), startPosX, startPosY));
				}
				
				startPosY += Math.round(nodeDiameter);
			}
			startPosY = 0;
			startPosX += Math.round(nodeDiameter);
		}
	}

	public float getNodeRadius() {
		return nodeRadius;
	}

	public void setNodeRadius(float nodeRadius) {
		this.nodeRadius = nodeRadius;
	}
	
}