package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;
import com.gooeywars.util.shape.Square;

public class GameMouseInput extends Component{
	Array<Entity> entities;
	
	public GameMouseInput() {
		create();
	}
	
	@Override
	public void create() {
		entities = Main.findGameBox("game").getEntities();
	}

	@Override
	public void update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			//Loop checks if an entity is clicked
			for (int i = 0; i < entities.size; i++) {
				Entity x = entities.get(i);
				//Checks if clicked entity is goo or entity type
				if (x.getType() == 0 || x.getType() == 1) {
					//Checks for same position XY
					if(x.getColliders().size > 0){
						if (x.getColliders().get(0).getPolygon().collide(new Square(1, Gdx.input.getX(), Gdx.input.getX()))) {
							System.out.println("Colliding");
							Vector2 v = new Vector2(100000, 0);
							entities.get(i).addForce(v);
							entities.get(i).setX(300);
							break;
						}
					}
				}
			}
		}
		
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			
		}
		
	}

}
