package com.gooeywars.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;

public class Friction extends Component{
	Array<Entity> entities;
	Vector2 friction;
	float coefficient = 0.5f;
	Vector2 normal;
	@Override
	public void create() {
		entities = Main.findGameBox("game").getEntities();
		friction = new Vector2();
		
	}

	@Override
	public void update() {
		for(int i = 0; i < entities.size; i++){
			if(entities.get(i).getPhysicsEnabled()){
				
				Vector2 velocity = new Vector2(entities.get(i).getVelocity());
				normal = new Vector2(velocity.x, velocity.y).nor();
				friction = new Vector2(normal.scl(-1f * coefficient * 9.8f * 50));
				entities.get(i).addForce(friction);
			}
			
		}
		
	}

}
