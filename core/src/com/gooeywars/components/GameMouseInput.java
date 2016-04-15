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
import com.gooeywars.util.shape.Rectangle;
import com.gooeywars.util.shape.Square;

public class GameMouseInput extends Component{
	Array<Entity> entities;
	Collider mouseTip;
	MoveHandler mover;
	Collider rectangleSelector;
	
	Array<Goo> selectedGoo;
	
	boolean rightClickedReleased;
	boolean rightClickedPressed;
	
	boolean leftClickedReleased;
	boolean leftClickedPressed;
	
	int xInitialLeft;
	int yInitialLeft;
	
	int xFinalLeft;
	int yFinalLeft;
	
	int xInitialRight;
	int yInitialRight;
	
	int xFinalRight;
	int yFinalRight;
	
	boolean onClickLeft;
	boolean onDownLeft;
	boolean onUpLeft;
	boolean onDraggedLeft;
	
	boolean onClickRight;
	boolean onDownRight;
	boolean onUpRight;
	boolean onDraggedRight;
	
	public GameMouseInput() {
		create();
	}
	
	@Override
	public void create() {
		//entities = Main.findGameBox("game").getEntities();
		mouseTip = new Collider(new Square(10,0,0));
		mover = Main.findGameBox("game").getMover();
		Rectangle rec = new Rectangle(0,0,0,0);
		rectangleSelector = new Collider(rec);
		selectedGoo = new Array<Goo>();
		
	}

	@Override
	public void update() {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			onUpLeft = false;
			onClickLeft = true;
			if(!leftClickedPressed){
				onDownLeft = true;
				xInitialLeft = Gdx.input.getX();
				yInitialLeft = Gdx.graphics.getHeight() - Gdx.input.getY();
			} else {
				onDownLeft = false;
				xFinalLeft = Gdx.input.getX();
				yFinalLeft = Gdx.graphics.getHeight() - Gdx.input.getY();
			}
			leftClickedPressed = true;
			
		} else {
			if(onClickLeft == true){
				onUpLeft = true;
			} else { 
				onUpLeft = false;
				xInitialLeft = 0;
				yInitialLeft = 0;
				xFinalLeft = 0;
				yFinalLeft = 0;
			}
			onClickLeft = false;
			leftClickedPressed = false;
		}
		
		
		if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
			onUpRight = false;
			onClickRight = true;
			if(!rightClickedPressed){
				onDownRight = true;
				xInitialRight = Gdx.input.getX();
				yInitialRight = Gdx.graphics.getHeight() - Gdx.input.getY();
			} else {
				onDownRight = false;
				xFinalRight = Gdx.input.getX();
				yFinalRight = Gdx.graphics.getHeight() - Gdx.input.getY();
			}
			rightClickedPressed = true;
			
		} else {
			if(onClickRight == true){
				onUpRight = true;
			} else { 
				onUpRight = false;
				xInitialRight = 0;
				yInitialRight = 0;
				xFinalRight = 0;
				yFinalRight = 0;
			}
			onClickRight = false;
			rightClickedPressed = false;
		}
		
		entities = Main.findGameBox("game").getEntities();
		
		if(onDownLeft){
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
		
		if(onDownRight){
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
		
		if(onUpLeft){
			Rectangle rec = new Rectangle(xInitialLeft,yInitialLeft,xFinalLeft-xInitialLeft,yFinalLeft-yInitialLeft);
			rectangleSelector = new Collider(rec);
			for (int i = 0; i < entities.size; i++) {
				Entity ent = entities.get(i);
				if (ent instanceof Goo) {
					Goo goo = (Goo) ent;
					
					if (goo.getColliders().get(0).collide(rectangleSelector).len2() > 0) {
						selectedGoo.add(goo);

						goo.setSelected(true);
						
					}
				}
			}
			
			for(int i = 0; i < entities.size; i++){
				for(int j = 0; j < selectedGoo.size; j++){
					System.out.println("J: " + j);
					if(entities.get(i).getId() == selectedGoo.get(j).getId()){
						System.out.println("Reaching end");
						break;
					}
					if(j == selectedGoo.size -1){
						
						if(entities.get(i) instanceof Goo){
							
							((Goo)entities.get(i)).setSelected(false);
						}
					}
				}
			}
		}
	}
	
	public Vector2 getOnDraggedLeft(){
		return new Vector2(xFinalLeft - xInitialLeft, yFinalLeft - yInitialLeft);
	}
	
	public boolean getOnDownLeft(){
		return onDownLeft;
	}
	
	public boolean getOnUpLeft(){
		return onUpLeft;
	}
	
	public boolean getOnClickedLeft(){
		return onClickLeft;
	}
	
	public Vector2 getOnDraggedRight(){
		return new Vector2(xFinalRight - xInitialRight, yFinalRight - yInitialRight);
	}
	
	public boolean getOnDownRight(){
		return onDownRight;
	}
	
	public boolean getOnUpRight(){
		return onUpRight;
	}
	
	public boolean getOnClickedRight(){
		return onClickRight;
	}
}