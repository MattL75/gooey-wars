package com.gooeywars.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;

public class Friction extends Component{
	Array<Entity> entities;
	Vector2 friction;
	float coefficient;
	@Override
	public void create() {
		entities = Main.gameBoxes.get(1).getEntities();
		friction = new Vector2();
		coefficient = 500f;
		
	}

	@Override
	public void update() {
		for(int i = 0; i < entities.size; i++){
			Vector2 velocity = new Vector2(entities.get(i).getVelocity());
			friction = new Vector2(velocity.nor().scl(-1f * coefficient));
			System.out.println("Friction" + friction);
			entities.get(i).addForce(friction);
		}
	}

}
