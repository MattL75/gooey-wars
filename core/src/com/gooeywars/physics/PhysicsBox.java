package com.gooeywars.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.util.shape.Polygon;

public class PhysicsBox {
	private Array<Entity> entities;
	
	public static float pixelsPerMeter = 100;
	
	public PhysicsBox(){
		entities = new Array<Entity>();
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
		
		for(int i = 0; i < entities.size; i++){
			//System.out.println(i);
			tempEnt = entities.get(i);
			//System.out.println(tempEnt.getPhysicsEnabled());
			if (tempEnt.getPhysicsEnabled()) {
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
				
				Vector2 test = checkCollisions(tempEnt, i);
				entities.get(i).setPosition(entities.get(i).getX() + test.x, entities.get(i).getY() + test.y);
				
				if(test.len2() != 0 && velocity.len2() != 0){
					test.rotate90(1);
					entities.get(i).setVelocity(Polygon.projection(test, entities.get(i).getVelocity()));
				}
			}
		}
	}
	
	
	private Vector2 checkCollisions(Entity ent, int index){
		Vector2 testResult;
		
		for(int i = 0; i < entities.size; i++){
			if(i != index){
				
				testResult = ent.collide(entities.get(i));
				if(testResult.len2() > 0){
					return testResult;
				}
				for(int j = 0; j < entities.get(i).getChildren().size; j++){
					testResult = ent.collide(entities.get(i).getChildren().get(j));
				}
				if(testResult.len2() > 0){
					return testResult;
				}
			}
		}
		
		return new Vector2();
	}
	
	public void addEntity(Entity ent){
		entities.add(ent);
	}

	public void clearEntities(){
		entities.clear();
	}
	
	public float getPixelsPerMeter() {
		return pixelsPerMeter;
	}

	public void setPixelsPerMeter(float pixelsPerMeter) {
		this.pixelsPerMeter = pixelsPerMeter;
	}
	
	
}
