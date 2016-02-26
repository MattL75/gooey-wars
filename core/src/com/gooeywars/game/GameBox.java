package com.gooeywars.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.physics.PhysicsBox;

public class GameBox {
	private Array<Entity> entities;
	private Array<Component> components;
	
	private PhysicsBox physics;
	private boolean physicsEnabled;
	
	private Stage UI;
	
	public GameBox(){
		Main.gameBoxes.add(this);
		physicsEnabled = false;
		
		create();
	}
	
	public GameBox(boolean pE){
		Main.gameBoxes.add(this);
		physicsEnabled = pE;
		
		create();
	}
	
	public GameBox(boolean pE, Stage UI){
		physicsEnabled = pE;
		this.UI = UI;
		
		create();
	}
	
	public void create(){
		entities = new Array<Entity>();
		components = new Array<Component>();
		
		if(physicsEnabled){
			physics = new PhysicsBox();
		}
	}
	
	public void update(){
		for(int i = 0; i < components.size; i++){
			components.get(i).update();
		}
		
		if(physicsEnabled){
			physics.update();
		}
		
		draw();
	}
	
	public void draw(){
		
	}
	
	public void addEntity(Entity ent){
		entities.add(ent);
		physics.addEntity(ent);
	}
	
	public void removeEntity(int id){
		
	}
	
	public void addComponent(Component comp){
		components.add(comp);
	}
	
	public boolean getPhysicsEnabled(){
		return physicsEnabled;
	}
}
