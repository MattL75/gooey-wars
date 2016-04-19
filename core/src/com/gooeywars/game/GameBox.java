package com.gooeywars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Goo;
import com.gooeywars.exception.TagSameException;
import com.gooeywars.pathfinding.Grid;
import com.gooeywars.pathfinding.MoveHandler;
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
	
	private MoveHandler mover;
	
	public Vector2 size;
	
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
		mover = new MoveHandler();
		entities = new Array<Entity>();
		components = new Array<Component>();
		batch = new SpriteBatch();
		background = Color.WHITE;
		
		size = new Vector2();
		
		size.x = Gdx.graphics.getWidth();
		size.y = Gdx.graphics.getHeight();
		
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
			if(entities.get(i) instanceof Goo){
				Grid grid = ((Goo) entities.get(i)).getGrid();
				Pixmap pix = new Pixmap((int)grid.nodeRadius * 2, (int)grid.nodeRadius * 2, Format.RGBA4444);
				pix.setColor(Color.GREEN);
				pix.drawRectangle(0, 0, pix.getWidth(),pix.getHeight());
				Texture open = new Texture(pix);
				pix.dispose();
				
				Pixmap pix2 = new Pixmap((int)grid.nodeRadius * 2, (int)grid.nodeRadius * 2, Format.RGBA4444);
				pix2.setColor(Color.RED);
				pix2.drawRectangle(0, 0, pix.getWidth(),pix.getHeight());
				Texture closed = new Texture(pix2);
				pix2.dispose();
				
				for(int j = 0; j < grid.nodeGrid.size; j++){
					for(int k = 0; k < grid.nodeGrid.get(j).size; k++){
						if(grid.nodeGrid.get(j).get(k).isWalkable()){
							Sprite sprite = new Sprite(open);
							sprite.setX(j*2*grid.nodeRadius);
							sprite.setY(k*2*grid.nodeRadius);
							sprite.draw(batch);
						} else {
							Sprite sprite = new Sprite(closed);
							sprite.setX(j*2*grid.nodeRadius);
							sprite.setY(k*2*grid.nodeRadius);
							sprite.draw(batch);
						}
						
					}
				}
				open.dispose();
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
		
		
		physics.addEntity(ent);
		
	}
	
	public void removeEntity(int id){
		for(int i = 0; i < entities.size; i++){
			if(entities.get(i).getId() == id){
				entities.get(i).dispose();
				entities.removeIndex(i);
				entities.shrink();
				return;
			}
			for(int j = 0; j < entities.get(i).getChildren().size; j++){
				if(entities.get(i).getChildren().get(j).getId() == id){
					entities.get(i).removeChild(j);
				}
			}
		}
		/*entities.get(id).dispose();
		entities.removeIndex(id);*/
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
		//mover = new MoveHandler();
	}

	public MoveHandler getMover() {
		return mover;
	}

	public void setMover(MoveHandler mover) {
		this.mover = mover;
	}
}
