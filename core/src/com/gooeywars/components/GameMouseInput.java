package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Square;

public class GameMouseInput extends Component{
	Array<Entity> entities;
	
	public GameMouseInput() {
		create();
	}
	
	@Override
	public void create() {
		//entities = Main.findGameBox("game").getEntities();
	}

	@Override
	public void update() {
		entities = Main.findGameBox("game").getEntities();
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			//Loop checks if an entity is clicked
			for (int i = 0; i < entities.size; i++) {
				Entity x = entities.get(i);
				//Checks if clicked entity is goo or entity type
				if (x.getType() == 0 || x.getType() == 1) {
					//Checks for same position XY
					if(x.getColliders().size > 0){
						
						//Doesn't return a boolean anymore. We'll have to reimplement it.
						/*if (x.getColliders().get(0).collide(new Collider(new Square(100, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())))) {
							
							
							Vector2 v = new Vector2(1000, 0);
							entities.get(i).addForce(v);
							break;
						}*/
					}
				}
			}
		}
		
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			
		}
		
	}

}
