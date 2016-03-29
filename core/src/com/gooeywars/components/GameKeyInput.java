package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;

public class GameKeyInput extends Component{
	Array<Entity> entities;
	static Entity currentEnt;
	
	public GameKeyInput() {
		create();
	}
	
	@Override
	public void create() {
		entities = Main.gameBoxes.get(1).getEntities();
		currentEnt = entities.first();
	}

	@Override
	public void update() {
		if(currentEnt.getPhysicsEnabled()){
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				currentEnt.addForce(new Vector2(0, 10));
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				currentEnt.addForce(new Vector2(0, -10));
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				currentEnt.addForce(new Vector2(-10, 0));
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				currentEnt.addForce(new Vector2(10, 0));
			}
			
		}
		
	}

}
