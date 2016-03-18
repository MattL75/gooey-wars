package com.gooeywars.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.util.shape.Polygon;

public class Collider {
	private Polygon polygon;
	private boolean isSolid;
	private float reboundFactor;
	private int id;
	private Entity entity;
	private int zIndex;
	private Array<Collider> colliders;
	
	private boolean drawable;
	
	public Collider(Polygon polygon){
		this.polygon = polygon;
		drawable = false;
	}
	
	public void draw(SpriteBatch batch){
		polygon.draw(batch);
	}
	
	public Polygon getPolygon(){
		return polygon;
	}
	
	public void setPolygon(Polygon polygon){
		this.polygon = polygon;
	}
	
	public void setEntity(Entity ent){
		entity = ent;
	}
	
	public void setX(float x){
		polygon.setX(x);
	}
	
	public void setY(float y){
		polygon.setY(y);
	}
	
	public float getX(){
		return polygon.getX();
	}
	
	public float getY(){
		return polygon.getY();
	}
	
	public void setPosition(float x, float y){
		polygon.setPosition(x, y);
	}

	public boolean isDrawable() {
		return drawable;
	}

	public void setDrawable(boolean drawable) {
		this.drawable = drawable;
	}
	
	public String getSaveData(){
		return "";
	}
}
