package com.gooeywars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.exception.TagSameException;
import com.gooeywars.pathfinding.Grid;
import com.gooeywars.physics.Collider;
import com.gooeywars.physics.PhysicsBox;

public class GameBox {
	private String tag;
	
	private Array<Entity> entities;
	private Array<Component> components;
	
	private PhysicsBox physics;
	private boolean physicsEnabled;
	
	private OrthographicCamera camera;
	
	private Screen UI;
	
	private SpriteBatch batch;
	
	private Color background;
	
	private Grid grid;
	
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
	
	public GameBox(boolean pE, Screen UI, OrthographicCamera camera){
		Main.gameBoxes.add(this);
		this.camera = camera;
		physicsEnabled = pE;
		this.UI = UI;
		
		create();
	}
	
	private void create(){
		entities = new Array<Entity>();
		components = new Array<Component>();
		batch = new SpriteBatch();
		background = Color.WHITE;
		
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
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.enableBlending();
		batch.begin();
		
		
		for(int i = 0; i < entities.size; i++){
			Array<Entity> children = entities.get(i).getChildren();
			entities.get(i).draw(batch);
			
			for(int j = 0; j < children.size;j++){
				children.get(j).draw(batch);
			}
		}
		
		for(int i = 0; i < entities.size; i++){
			Array<Collider> coll = entities.get(i).getColliders();
			for(int j = 0; j < coll.size; j++){
				if(coll.get(j).isDrawable()){
					coll.get(j).draw(batch);
				}
			}
		}
		
		batch.end();
		if(UI != null){
			UI.render(0);
		}

	}
	
	public void init(){
		for(int i = 0; i < components.size; i++){
			components.get(i).create();
		}
	}
	
	public void addEntity(Entity ent){
		if(ent.isObstacle()){
			ent.setX(Main.findGameBox("game").genObstacleCoordX(ent.getX()));
			ent.setY(Main.findGameBox("game").genObstacleCoordY(ent.getY()));
		}
		entities.add(ent);
		
		if(physicsEnabled){
			physics.addEntity(ent);
		}
	}
	
	public void removeEntity(int id){
		entities.get(id).dispose();
		entities.removeIndex(id);
	}
	
	public void clearEntities(){
		for(int i = 0; i < entities.size; i++){
			entities.get(i).dispose();
		}
		entities.clear();
		physics.clearEntities();
	}
	
	public void addComponent(Component comp){
		components.add(comp);
	}
	
	public float genObstacleCoordX(float x){
		System.out.println("gen obstacleX");
		int xInt = (int) x;
		xInt = (int)(xInt/(grid.getNodeRadius()*2)) * (int)(grid.getNodeRadius()*2);
		return xInt;
	}
	
	public float genObstacleCoordY(float y){
		int yInt = (int) y;
		yInt = (int)(yInt/(grid.getNodeRadius()*2)) * (int)(grid.getNodeRadius()*2);
		return yInt;
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

	public Screen getUI() {
		return UI;
	}

	public void setUI(Screen uI) {
		UI = uI;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}
