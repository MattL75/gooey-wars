package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Square;

public class GameMouseInput extends Component{
	Array<Entity> entities;
	Collider mouseTip;
	public GameMouseInput() {
		create();
	}
	
	@Override
	public void create() {
		//entities = Main.findGameBox("game").getEntities();
		mouseTip = new Collider(new Square(10,0,0));
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
						mouseTip.setX(Gdx.input.getX());
						mouseTip.setY(Gdx.graphics.getHeight() - Gdx.input.getY());
						//Doesn't return a boolean anymore. We'll have to reimplement it.
						if (x.getColliders().get(0).collide(mouseTip).len2() > 0) {
							GameKeyInput.currentEnt = x;
							if(x instanceof Goo){
								Goo goo = (Goo) x;
								goo.setSelected(true);
							}
							break;
						}
					}
				}
			}
		}
		
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			Array<Entity> ar = Main.findGameBox("game").getEntities();
			for (int i = 0; i < ar.size; i++) {
				if (ar.get(i).getType() == Entity.GOO) {
					Goo goo = (Goo) ar.get(i);
					if (goo.isSelected()) {
						//do stuff
					}
				}
			}
		}
	}
}
