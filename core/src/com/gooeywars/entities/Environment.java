package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.game.Main;
import com.gooeywars.pathfinding.Grid;
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Rectangle;


public class Environment extends Entity{
	private Rectangle leftRec;
	private Rectangle upRec;
	private Rectangle rightRec;
	private Rectangle downRec;
	private Grid grid;
	
	
	public Environment(){
		genEnvironment(500,500, new Array<Entity>(), null, Color.WHITE);
	}
	
	public Environment(Color background){
		genEnvironment(500,500, new Array<Entity>(), null, background);
	}
	
	public Environment(float x, float y, Color background){
		genEnvironment(x, y, new Array<Entity>(), null, background);
	}
	
	public Environment(Array<Entity> children){
		genEnvironment(500, 500, children, null, Color.WHITE);
	}
	
	public Environment(float width, float height){
		genEnvironment(width ,height, new Array<Entity>(), null, Color.WHITE);
	}
	
	public Environment(float width, float height, Array<Entity> children){
		genEnvironment(width ,height, children, null, Color.WHITE);
	}
	
	public Environment(float width, float height, Array<Entity> children, Texture terrainTexture){
		genEnvironment(width ,height, children, terrainTexture, Color.WHITE);
	}
	
	public Environment(float width, float height, Array<Entity> children, Texture terrainTexture, Color background){
		genEnvironment(width ,height, children, terrainTexture, background);
	}
	
	public void genEnvironment(float width, float height, Array<Entity> children, Texture terrainTexture, Color background){
		setType(Entity.ENVIRONMENT);
		Texture texture = null;
		
		if(terrainTexture == null){
			Pixmap pix = new Pixmap(100,100, Format.RGBA8888);
			pix.setColor(Color.LIGHT_GRAY);
			pix.fillRectangle(0, 0, 100, 100);
			
			texture = new Texture(pix);
			pix.dispose();
		} else {
			texture = terrainTexture;
		}
		
		setType(Entity.ENVIRONMENT);
		setPhysicsEnabled(false);
		//Texture
		//texture.
		//setSprite
		
		Main.findGameBox("game").setBackground(background);
		setWidth(width);
		setHeight(height);
		
		leftRec  = new Rectangle(-100,0,100,height);
		Collider coll1 = new Collider(leftRec);
		coll1.setDrawable(true);
		addCollider(coll1);
		
		upRec = new Rectangle(0,height,width,100);
		Collider coll2 = new Collider(upRec);
		coll2.setDrawable(true);
		addCollider(coll2);
		
		rightRec = new Rectangle(width, 0, 100, height);
		Collider coll3 = new Collider(rightRec);
		coll3.setDrawable(true);
		addCollider(coll3);
		
		downRec = new Rectangle(0,-100,width,100);
		Collider coll4 = new Collider(downRec);
		coll4.setDrawable(true);
		addCollider(coll4);
		
		//grid = new Grid(new Vector2(width,height), 10);
		setChildren(children);
	}
	
	public void addChild(Entity child){
		getChildren().add(child);
	}
	
	@Override
	public Vector2 collide(Entity other){
		Vector2 displacement = new Vector2();
		return displacement;
	}
	
	@Override
	public void setWidth(float width){
		super.setWidth(width);
	}
	
	@Override
	public void setHeight(float height){
		super.setHeight(height);
	}
	
	@Override
	public void setX(float x){
		
	}
	
	@Override
	public void setY(float y){
		
	}
	
	public String getSaveData(){
		String data = super.getSaveData();
		for(int i = 0; i < getChildren().size; i++){
			data += "," + getChildren().get(i).getSaveData();
		}
		return data;
	}
}
