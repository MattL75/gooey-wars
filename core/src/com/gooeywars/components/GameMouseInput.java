package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;
import com.gooeywars.pathfinding.MoveHandler;
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Square;

public class GameMouseInput extends Component{
	Array<Entity> entities;
	Collider mouseTip;
	MoveHandler mover;
	
	Array<Goo> selectedGoo;
	
	boolean rightClickedReleased;
	boolean rightClickedPressed;
	
	boolean leftClickedReleased;
	boolean leftClickedPressed;
	
	public GameMouseInput() {
		create();
	}
	
	@Override
	public void create() {
		//entities = Main.findGameBox("game").getEntities();
		mouseTip = new Collider(new Square(10,0,0));
		mover = Main.findGameBox("game").getMover();
	}

	@Override
	public void update() {
		entities = Main.findGameBox("game").getEntities();
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			//Loop checks if an entity is clicked
			if (leftClickedReleased) {
				for (int i = 0; i < entities.size; i++) {
					Entity ent = entities.get(i);
					if (ent instanceof Goo) {
						Goo goo = (Goo) ent;
						mouseTip.setX(Gdx.input.getX());
						mouseTip.setY(Gdx.graphics.getHeight() - Gdx.input.getY());
						if (goo.getColliders().get(0).collide(mouseTip).len2() > 0) {
							GameKeyInput.currentEnt = goo;

							goo.setSelected(true);
							for (int j = 0; j < entities.size; j++) {
								if (entities.get(j) instanceof Goo) {
									if (j != i) {
										((Goo) entities.get(j)).setSelected(false);
									}
								}
							}
							break;
						}
					}
				}
			}
			leftClickedReleased = false;
		} else { 
			leftClickedReleased = true;
		}
		
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			if(rightClickedReleased){
				Array<Entity> ar = Main.findGameBox("game").getEntities();
				for (int i = 0; i < ar.size; i++) {
					if (ar.get(i).getType() == Entity.GOO) {
						Goo goo = (Goo) ar.get(i);
						if (goo.isSelected()) {
							mover.cancel(goo);
							mover.move(goo, new Vector2(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY()));	
						}
					}
				}
			}
			rightClickedReleased = false;
			
		} else {
			rightClickedReleased = true;
		}
	}
}