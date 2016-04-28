package com.gooeywars.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.util.shape.Polygon;

public class PhysicsBox {
	private Array<Entity> entities;
	
	public static float pixelsPerMeter = 10;
	
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
				
				entities.get(i).setAcceleration(new Vector2(tempForce.scl(entities.get(i).getVelocityFactor()/30f)));//(float)entities.get(i).getMass())));
			}
			
		}
	}
	
	private void calculatePosition(float deltaTime){
		
		
		for(int i = 0; i < entities.size; i++){
			//System.out.println(i);
			
			//System.out.println(tempEnt.getPhysicsEnabled());
			if (entities.get(i).getPhysicsEnabled()) {
				float x = entities.get(i).getX()/pixelsPerMeter + entities.get(i).getVelocity().x * deltaTime
						+ 1 / 2 * entities.get(i).getAcceleration().x * deltaTime * deltaTime;
				float y = entities.get(i).getY()/pixelsPerMeter + entities.get(i).getVelocity().y * deltaTime
						+ 1 / 2 * entities.get(i).getAcceleration().y * deltaTime * deltaTime;

				Vector2 velocity = new Vector2(entities.get(i).getVelocity().x + entities.get(i).getAcceleration().x * deltaTime,
						entities.get(i).getVelocity().y + entities.get(i).getAcceleration().y * deltaTime);

				if (Math.abs(velocity.x) < 0.1f && Math.abs(velocity.y) < 0.1f) {
					velocity.setZero();
				}
				
				
				
				entities.get(i).setPosition(x*pixelsPerMeter, y*pixelsPerMeter);
				entities.get(i).setVelocity(velocity);
				entities.get(i).nullifyForce();
				
				Vector2 test = checkCollisions(entities.get(i), i);
				entities.get(i).setX(entities.get(i).getX() + test.x);
				entities.get(i).setY(entities.get(i).getY() + test.y);
				
				if(test.len2() != 0 && velocity.len2() != 0){
					test.rotate90(1);
					entities.get(i).setVelocity(Polygon.projection(test, entities.get(i).getVelocity()));
				}
			}
		}
	}
	
	
	private Vector2 checkCollisions(Entity ent, int index){
		/*Array<Collider> entColliders = ent.getColliders();
		Entity testingEnt = null;
		Vector2 testResult;
		Collider coll1 = null;
		Collider coll2 = null;
		for(int i = 0; i < entColliders.size; i++){
			coll1 = entColliders.get(i);
			for(int j = 0; j < entColliders.size; j++){
				if(j != index){
					testingEnt = entities.get(j);
					for(int k = 0; k < testingEnt.getColliders().size; k++){
						coll2 = testingEnt.getColliders().get(k);
						testResult = coll1.collide(coll2);
						if(testResult.len2() > 0){
							return testResult;
						}
					}
				}
			}
		}
		return new Vector2();*/
		
		//Vector2 largest = new Vector2();
		
		/*for(int i = 0; i < entities.size; i++){
			if(i != index){
				for(int)
			}
		}*/
		
		Vector2 testResult;
		Vector2 largest = new Vector2();
		
		for(int i = 0; i < entities.size; i++){
			if(i != index){
				
				testResult = ent.collide(entities.get(i));
				if(testResult.len2() > largest.len2()){
					largest = testResult.cpy();
				}
				for(int j = 0; j < entities.get(i).getChildren().size; j++){
					testResult = ent.collide(entities.get(i).getChildren().get(j));
					if(testResult.len2() > largest.len2()){
						largest = testResult.cpy();
					}
				}
			}
		}
		
		return largest;
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
