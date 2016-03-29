package com.gooeywars.entities;

public class Geyser extends Entity{
	private GeyserProperty property;
	
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
		setX(x);
		setY(y);
		property = new GeyserProperty(prop);
	}
	
	/*public genSprite(){
		property.
	}*/
}
