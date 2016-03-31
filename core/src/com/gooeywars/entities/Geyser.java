package com.gooeywars.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Geyser extends Entity{
	private GeyserProperty property;
	private int propInt;
	
	public Geyser(){
		genGeyser(0,0,0);
	}
	
	public Geyser(int prop){
		genGeyser(0,0,prop);
	}
	
	public Geyser(float x, float y){
		genGeyser(x,y,0);
	}
	
	public Geyser(float x, float y, int prop){
		genGeyser(x,y,prop);
	}
	
	private void genGeyser(float x, float y, int prop){
		propInt = prop;
		setPhysicsEnabled(false);
		setX(x);
		setY(y);
		property = new GeyserProperty(prop);
		setSprite(new Sprite(property.getTexture()));
		setType(Entity.GEYSER);
	}
	
	
	public String getSaveData(){
		String data = super.getSaveData();
		data+= "," + propInt;
		return data;
	}
}
