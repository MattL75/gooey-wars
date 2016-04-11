package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;

public class GameKeyInput extends Component{
	Array<Entity> entities;
	static Entity currentEnt;
	
	
	boolean spaceReleased;
	
	public GameKeyInput() {
		
	}
	
	@Override
	public void create() {
		entities = Main.findGameBox("game").getEntities();
		currentEnt = entities.first();
	}

	@Override
	public void update() {
		if(currentEnt.getPhysicsEnabled()){
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				currentEnt.addForce(new Vector2(0, 1000));
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				currentEnt.addForce(new Vector2(0, -1000));
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				currentEnt.addForce(new Vector2(-1000, 0));
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				currentEnt.addForce(new Vector2(1000, 0));
			}
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				if(spaceReleased){
					if(currentEnt instanceof Goo){
						((Goo) currentEnt).split(new Vector2(1,0));
					}
					spaceReleased = false;
				}
			} else {
				spaceReleased = true;
			}
		}
		
	}

}
