package com.gooeywars.pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Main;

public class Pathfinder {
	private Array<Node> closed = new Array<Node>();
	private Array<Node> open = new Array<Node>();
	private Grid grid;
	//Grid grid = new Grid(new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), 10);
	
	public Pathfinder(Grid grid) {
		this.grid = grid;
		setupObstacles();
	}
	
	private boolean isPosOpen(Vector2 endPos) {
		if (grid.nodeFromWorldPoint(endPos).isWalkable()) {
			return true;
		}
		return false;
	}
	
	//Will's mom algorithm (cause its big and simply beautiful)
	public void setupObstacles() {
		Array<Entity> ent = Main.findGameBox("game").getEntities();
		for (int i = 0; i < ent.size; i++) {
			if (ent.get(i).getType() == Entity.ENVIRONMENT) {
				for (int j = 0; j < ent.get(i).getChildren().size; j++) {
					if (ent.get(i).getChildren().get(j).getType() == Entity.OBSTACLE) {
						//System.out.println(ent.get(i).getChildren().get(j).getX() +" AY "+ ent.get(i).getChildren().get(j).getY());
						//System.out.println(grid.nodeFromWorldPoint(new Vector2(ent.get(i).getChildren().get(j).getX(), ent.get(i).getChildren().get(j).getY())).getWorldPos().x + " YA " + grid.nodeFromWorldPoint(new Vector2(ent.get(i).getChildren().get(j).getX(), ent.get(i).getChildren().get(j).getY())).getWorldPos().y);
						for (float k = ent.get(i).getChildren().get(j).getX(); k < ent.get(i).getChildren().get(j).getX() + ent.get(i).getChildren().get(j).getWidth(); k += (grid.nodeRadius * 2)) {
							for (float h = ent.get(i).getChildren().get(j).getY(); h < ent.get(i).getChildren().get(j).getY() + ent.get(i).getChildren().get(j).getHeight(); h += (grid.nodeRadius * 2)) {
								grid.nodeFromWorldPoint(new Vector2(k, h)).setWalkable(false);
							}
						}
					}
				}
			}
		}
	}

	public Array<Node> findPath(Vector2 iniPos, Vector2 endPos, Grid gooGrid) {
		grid = gooGrid;
		setupObstacles();
		if (!isPosOpen(endPos)) {
			return new Array<Node>();
		}

		Node initialNode = grid.nodeFromWorldPoint(iniPos);
		Node endNode = grid.nodeFromWorldPoint(endPos);
		
		open.add(initialNode);
		
		while (open.size != 0) {
			Node currentNode = open.get(0);
			for (int i = 1; i < open.size; i++) {
				if (open.get(i).fCost() < currentNode.fCost() || open.get(i).fCost() == currentNode.fCost() && open.get(i).hCost < currentNode.hCost) {
					currentNode = open.get(i);
				}
			}
			open.removeValue(currentNode, false);
			closed.add(currentNode);
			
			if (currentNode == endNode) {
				return retracePath(initialNode, endNode);
			}
			
			Array<Node> neighbours = grid.GetNeighbours(currentNode);
			
			for (int i = 0; i < neighbours.size; i++) {
				if (!neighbours.get(i).isWalkable() || closed.contains(neighbours.get(i), false)) {
					continue;
				}
				
				int newMovementCostToNeighbour = currentNode.gCost + getDistance(currentNode, neighbours.get(i));
				if (newMovementCostToNeighbour < neighbours.get(i).gCost || !open.contains(neighbours.get(i), false)) {
					neighbours.get(i).gCost = newMovementCostToNeighbour;
					neighbours.get(i).hCost = getDistance(neighbours.get(i), endNode);
					neighbours.get(i).parent = currentNode;
					
					if (!open.contains(neighbours.get(i), false)) {
						open.add(neighbours.get(i));
					}
				}
			}
		}
		return null;
	}
	
	public Array<Node> retracePath(Node startNode, Node endNode) {
		Array<Node> path = new Array<Node>();
		Node currentNode = endNode;
		while (currentNode != startNode) {
			path.add(currentNode);
			currentNode = currentNode.parent;
		}
		path.reverse();
		return path;
	}
	
	public int getDistance(Node nodeA, Node nodeB) {
		int distX = Math.abs(nodeA.gridX - nodeB.gridX);
		int distY = Math.abs(nodeA.gridY - nodeB.gridY);
		
		if (distX > distY) {
			return 14 * distY + 10 * (distX - distY);
		} 
		return 14 * distX + 10 * (distY - distX);
	}
	
	public void clearArrays() {
		closed.clear();
		open.clear();
	}	
}
