package com.gooeywars.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Goo;
import com.gooeywars.util.shape.Circle;
import com.gooeywars.util.shape.Polygon;

public class PhysicsBox {
	private Array<Entity> entities;
	private Array<Collider> colliders;
	
	private float pixelsPerMeter = 10;
	
	public PhysicsBox(){
		entities = new Array<Entity>();
		colliders = new Array<Collider>();
	}
	
	public void update(float deltaTime){
		
		calculateVectors();
		calculatePosition(deltaTime);
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
		Entity proEnt = null;
		
		for(int i = 0; i < entities.size; i++){
			
			tempEnt = entities.get(i);
			proEnt = tempEnt.clone();
			if (tempEnt.getPhysicsEnabled()) {
				//System.out.println("checking physics");
				//System.out.println(tempEnt.getVelocity());
				float x = tempEnt.getX()/pixelsPerMeter + tempEnt.getVelocity().x * deltaTime
						+ 1 / 2 * tempEnt.getAcceleration().x * deltaTime * deltaTime;
				float y = tempEnt.getY()/pixelsPerMeter + tempEnt.getVelocity().y * deltaTime
						+ 1 / 2 * tempEnt.getAcceleration().y * deltaTime * deltaTime;

				Vector2 velocity = new Vector2(tempEnt.getVelocity().x + tempEnt.getAcceleration().x * deltaTime,
						tempEnt.getVelocity().y + tempEnt.getAcceleration().y * deltaTime);

				if (Math.abs(velocity.x) < 0.1f && Math.abs(velocity.y) < 0.1f) {
					velocity.setZero();
				}
				
				
				
				entities.get(i).setPosition(x*pixelsPerMeter, y*pixelsPerMeter);
				entities.get(i).setVelocity(velocity);
				entities.get(i).nullifyForce();
				
				//System.out.println(tempEnt.getX());
				System.out.println(checkCollisions(tempEnt, i));
				
				//proEnt.setPosition(x, y);
				/*if (checkCollisions(proEnt, i)) {
					tempEnt.setVelocity(new Vector2());
					tempEnt.nullifyForce();
				} else {
					tempEnt.setPosition(x, y);
					tempEnt.setVelocity(velocity);
					tempEnt.nullifyForce();
				}*/
			}
		}
		
		
	}
	
	private boolean checkCollisions(Entity ent, int index){
		Array<Collider> entColliders = ent.getColliders();
		Entity testingEnt = null;
		Collider coll1 = null;
		Collider coll2 = null;
		for(int i = 0; i < entColliders.size; i++){
			coll1 = entColliders.get(i);
			
			for(int j = 0; j <entities.size; j++){
				if(j != index){
					testingEnt = entities.get(j);
					for(int k = 0; k < testingEnt.getColliders().size; k++){
						coll2 = testingEnt.getColliders().get(k);
						
						if(coll1.collide(coll2)){
							return true;
						}
						
					}
				}
			}
		}
		
		return false;
		
		//System.out.println(entColliders.size);
		/*for(int i = 0; i < entColliders.size; i++){
			Polygon poly1 = entColliders.get(i).getPolygon();
			Polygon poly2 = null;
			
			
			for(int j = i+1; j < colliders.size; j++){
				poly2 = colliders.get(j).getPolygon();
				System.out.println("i: " + i);
				System.out.println("j: " + j);
				System.out.println("Poly1 y: " + poly1.getY());
				
				
				System.out.println("Poly2 y: " + poly2.getY());
				if(poly1.collide(poly2)){
					return true;
				}
				
			}
		}
		
		return false;*/
	}
	
	public void addEntity(Entity ent){
		for(int i = 0; i < ent.getColliders().size; i++){
			colliders.add(ent.getColliders().get(i));
		}
		entities.add(ent);
	}

	public float getPixelsPerMeter() {
		return pixelsPerMeter;
	}

	public void setPixelsPerMeter(float pixelsPerMeter) {
		this.pixelsPerMeter = pixelsPerMeter;
	}
	
	
}
