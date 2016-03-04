package com.gooeywars.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.util.shape.Polygon;

public class PhysicsBox {
	private Array<Entity> entities;
	private Array<Collider> colliders;
	
	public PhysicsBox(){
		entities = new Array<Entity>();
		colliders = new Array<Collider>();
	}
	
	public void update(float deltaTime){
		checkCollisions();
		calculateVectors();
		calculatePosition(deltaTime);
	}
	
	private void checkCollisions(){
		
		for(int i = 0; i < colliders.size; i++){
			Polygon poly1 = colliders.get(i).getPolygon();
			
			for(int j = i+1; j < colliders.size; j++){
				Polygon poly2 = colliders.get(j).getPolygon();
				
				System.out.println(poly1.collide(poly2));
			}
		}
	}
	
	private void checkCollision(Polygon poly1, Polygon poly2){
		poly1.collide(poly2);
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
			if (tempEnt.getPhysicsEnabled()) {
				float x = tempEnt.getX() + tempEnt.getVelocity().x * deltaTime
						+ 1 / 2 * tempEnt.getAcceleration().x * deltaTime * deltaTime;
				float y = tempEnt.getY() + tempEnt.getVelocity().y * deltaTime
						+ 1 / 2 * tempEnt.getAcceleration().y * deltaTime * deltaTime;

				Vector2 velocity = new Vector2(tempEnt.getVelocity().x + tempEnt.getAcceleration().x * deltaTime,
						tempEnt.getVelocity().y + tempEnt.getAcceleration().y * deltaTime);

				if (Math.abs(velocity.x) < 5f && Math.abs(velocity.y) < 5f) {
					velocity.setZero();
				}

				tempEnt.setPosition(x, y);
				tempEnt.setVelocity(velocity);
				tempEnt.nullifyForce();
			}
		}
		
		
	}
	
	public void addEntity(Entity ent){
		for(int i = 0; i < ent.getColliders().size; i++){
			colliders.add(ent.getColliders().get(i));
		}
		entities.add(ent);
	}
}
