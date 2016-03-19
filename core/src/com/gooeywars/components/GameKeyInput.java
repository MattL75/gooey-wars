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
	
	public GameKeyInput() {
		create();
	}
	
	@Override
	public void create() {
		entities = Main.gameBoxes.get(1).getEntities();
	}

	@Override
	public void update() {
		
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			entities.get(0).addForce(new Vector2(0, 1000));
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			entities.get(0).addForce(new Vector2(0, -1000));
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			entities.get(0).addForce(new Vector2(-1000, 0));
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			entities.get(0).addForce(new Vector2(1000, 0));
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.K)) {
			entities.get(0).setPosition(100, 100);
		}
		
	}

}
