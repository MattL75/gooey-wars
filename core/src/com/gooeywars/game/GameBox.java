package com.gooeywars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	
	private SpriteBatch batch;
	
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
		batch = new SpriteBatch();
		
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
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		for(int i = 0; i < entities.size; i++){
			
			batch.draw(entities.get(i).getSprite().getTexture(), entities.get(i).x, entities.get(i).y);
		}
		batch.end();
	}
	
	public void addEntity(Entity ent){
		entities.add(ent);
		physics.addEntity(ent);
	}
	
	public void removeEntity(int id){
		
	}
	
	public void addComponent(Component comp){
		comp.create();
		components.add(comp);
	}
	
	public boolean getPhysicsEnabled(){
		return physicsEnabled;
	}
	
	public void setPhysicsEnabled(boolean pE){
		physicsEnabled = pE;
	}
	
	
}
