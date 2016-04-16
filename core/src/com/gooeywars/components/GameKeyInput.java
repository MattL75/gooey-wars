package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;

public class GameKeyInput extends Component{
	Array<Entity> entities;
	static Entity currentEnt;
	float camSpeed;
	
	
	boolean spaceReleased;
	
	public GameKeyInput() {
		
	}
	
	@Override
	public void create() {
		entities = Main.findGameBox("game").getEntities();
		currentEnt = entities.first();
		camSpeed = 5f;
	}

	@Override
	public void update() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			Main.findGameBox("game").getCamera().position.add(new Vector3(0, camSpeed, 0));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			Main.findGameBox("game").getCamera().position.add(new Vector3(0, -camSpeed, 0));

		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			Main.findGameBox("game").getCamera().position.add(new Vector3(-camSpeed, 0, 0));

		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			Main.findGameBox("game").getCamera().position.add(new Vector3(camSpeed, 0, 0));

		}
		if(currentEnt instanceof Goo){
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				if (spaceReleased) {
					if (currentEnt instanceof Goo) {
						((Goo) currentEnt).split(new Vector2(1, 0));
					}
					spaceReleased = false;
				}
			} else {
				spaceReleased = true;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
			if(GameMouseInput.selectedGoo.size > 0){
				GameMouseInput.selectedGoo.first().merge(GameMouseInput.selectedGoo);
			}
		}
	}

}
