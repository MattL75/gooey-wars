package com.gooeywars.physics;

import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Main;
import com.gooeywars.util.shape.Shape;

public class Collider {
	private Shape shape;
	private boolean isSolid;
	private int id;
	private Entity entity;
	private int zIndex;
	private Array<Collider> colliders;
	
	
	public Collider(Entity ent){
		entity = ent;
	}
	
	public int[][] checkCollisions(){
		colliders = Main.findGameBox("name").getColliders();
		
		int[][] collisions = new int[0][0];
		
		for(int i = 0; i < colliders.size; i++){
			
		}
		
		return collisions;
	}
}
