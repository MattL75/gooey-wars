package com.gooeywars.physics;

import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;

public class PhysicsBox {
	private Array<Entity> entities;
	
	public PhysicsBox(){
		entities = new Array<Entity>();
		//test
	}
	
	public void update(){
		
	}
	
	public void addEntity(Entity ent){
		entities.add(ent);
	}
}
