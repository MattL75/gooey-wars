package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Goo extends Entity{
	protected int sideCount;
	
	public Goo(){
		
		
		setPhysicsEnabled(true);
		setMass(100);
		createGoo();
	}
	
	public void createGoo(){
		setType(1);
		sideCount = getMass()/10;
		//System.out.println(getMass());
		
		setWidth(getMass()/2.0f);
		setHeight(getMass()/2.0f);
		
		//System.out.println(getWidth());
		createSprite();
		createColliders();
		setX(300);
		setY(300);
		
		setPhysicsEnabled(true);
		setMass(2);
	}
	
	private void createSprite(){
		float width = getWidth();
		float height = getHeight();
		Pixmap pix = new Pixmap((int)width,(int)height,Format.RGBA8888);
		
		/*for(int i = 0; i< sideCount; i++){
			pix.drawLine((int)(Math.cos(Math.PI/sideCount)*width), (int)(Math.sin(Math.PI/(sideCount+1)%sideCount)*height), (int)(Math.cos(Math.PI/(sideCount+1)%sideCount)*width), (int)(Math.sin(Math.PI/sideCount)*height));
		}*/
		pix.fillCircle(10, 10, 10);
		pix.setColor(Color.PINK);
		Texture texture = new Texture(pix);
		pix.dispose();
		
		Sprite sprite = new Sprite(texture);
		setSprite(sprite);
		
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
