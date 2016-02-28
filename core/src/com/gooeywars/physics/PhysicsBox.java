package com.gooeywars.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;

public class PhysicsBox {
	private Array<Entity> entities;
	
	
	public PhysicsBox(){
		entities = new Array<Entity>();
	}
	
	public void update(float deltaTime){
		checkCollisions();
		calculateVectors();
		calculatePosition(deltaTime);
	}
	
	private void checkCollisions(){
		/*for(int i = 0; i < entities.size; i++){
			
		}*/
	}
	
	private void calculateVectors(){
		Vector2 tempForce = null;
		
		for(int i = 0; i < entities.size; i++){
			
			if(entities.get(i).getPhysicsEnabled()){
				tempForce = entities.get(i).getForce();
				entities.get(i).setAcceleration(new Vector2(tempForce.scl(1/(float)entities.get(i).getMass())));
			}
			
		}
	}
	
	private void calculatePosition(float deltaTime){
		Entity tempEnt = null;
		
		for(int i = 0; i < entities.size; i++){
			tempEnt = entities.get(i);
			
			
			
			float x = tempEnt.getX() + tempEnt.getVelocity().x * deltaTime + 1 / 2 * tempEnt.getAcceleration().x * deltaTime * deltaTime;
			float y = tempEnt.getY() + tempEnt.getVelocity().y * deltaTime + 1 / 2 * tempEnt.getAcceleration().y * deltaTime * deltaTime;
			
			Vector2 velocity = new Vector2(tempEnt.getVelocity().x + tempEnt.getAcceleration().x * deltaTime, tempEnt.getVelocity().y + tempEnt.getAcceleration().y * deltaTime);
			
			if(Math.abs(velocity.x) < 5f && Math.abs(velocity.y) < 5f){
				velocity.setZero();
			}
			
			tempEnt.setPosition(x, y);
			tempEnt.setVelocity(velocity);
		}
		
		tempEnt.nullifyForce();
	}
	
	public void addEntity(Entity ent){
		entities.add(ent);
	}
}
