package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Goo extends Entity{
	protected int sideCount;
	
	public Goo(){
		
		
		setPhysicsEnabled(true);
		setMass(200);
		createGoo();
	}
	
	public Goo(float x, float y){
		
	}
	
	public Goo(float x, float y, int mass){
		
	}
	
	public Goo(float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		setPhysicsEnabled(true);
	}
	
	public void createGoo(){
		setType(1);
		sideCount = getMass()/10;
		//sideCount = 4;
		//System.out.println(getMass());
		
		setWidth(getMass()/2.0f);
		setHeight(getMass()/2.0f);
		
		//System.out.println(getWidth());
		createSprite();
		createColliders();
		setX(300);
		setY(300);
	}
	
	private void createSprite(){
		float width = getWidth();
		float height = getHeight();
		Pixmap pix = new Pixmap((int)width,(int)height,Format.RGBA8888);
		
		pix.setColor(Color.PINK);
		
		for(int i = 0; i< sideCount; i++){
			int indi = i;
			int indf = (i+1)%sideCount;
			double twoPi = 2*Math.PI;
			double hWidth = width/2.0;
			double hHeight = height/2.0;
			
			
			pix.drawLine((int)Math.ceil(Math.cos(2*Math.PI*i/sideCount)*width/2 + width/2), (int)Math.ceil(Math.sin(2 * Math.PI*i/sideCount)*height/2 + height/2), (int)Math.ceil(Math.cos(2 * Math.PI*((i+1) %sideCount)/sideCount)*width/2 + width/2), (int)Math.ceil(Math.sin(2 * Math.PI*((i+1) %sideCount)/sideCount)*height/2 + height/2));
		}
		
		Texture texture = new Texture(pix);
		pix.dispose();
		
		Sprite sprite = new Sprite(texture);
		setSprite(sprite);
		setX(500);
		setY(500);
	}
	
	private void createColliders(){
		
	}
	
	//
	@Override
	public String getSaveData(){
		String data = super.getSaveData();
		return data;
	}
}
