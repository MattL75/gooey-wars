package com.gooeywars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.exception.TagSameException;
import com.gooeywars.physics.PhysicsBox;

public class GameBox {
	private String tag;
	
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
		Main.gameBoxes.add(this);
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
			physics.update(Gdx.graphics.getDeltaTime());
		}
	
		draw();
	}
	
	//Draws to the screen the sprites of the entities. 
	public void draw(){
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		for(int i = 0; i < entities.size; i++){
			entities.get(i).getSprite().draw(batch);
		}
		batch.end();
	}
	
	public void addEntity(Entity ent){
		entities.add(ent);
		if(physicsEnabled){
			physics.addEntity(ent);
		}
	}
	
	public void removeEntity(int id){
		
	}
	
	public void addComponent(Component comp){
		comp.create();
		components.add(comp);
	}
	
	public Array<Entity> getEntities(){
		return entities;
	}
	
	public void setEntities(Array<Entity> entities){
		this.entities = entities;
	}
	
	public boolean getPhysicsEnabled(){
		return physicsEnabled;
	}
	
	public void setPhysicsEnabled(boolean pE){
		physicsEnabled = pE;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag){
		if(Main.checkTags(tag)){
			this.tag = tag;
		}
		else{
			throw new TagSameException("Tag already used by another GameBox");
		}
	}
	
	
}
