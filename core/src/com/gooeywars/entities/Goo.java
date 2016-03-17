package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class Goo extends Entity{
	protected String type = "goo";
	protected int sideCount;
	
	public Goo(){
		mass = 100;
		setPhysicsEnabled(true);	
	}
	
	public void createGoo(){
		sideCount = mass/sideCount;
		setWidth(mass/2);
		setHeight(mass/2);
		createSprite();
		createColliders();
	}
	
	private void createSprite(){
		Pixmap pix = new Pixmap((int)width,(int)height,Format.RGBA8888);
		
		for(int i = 0; i< sideCount; i++){
			pix.drawLine((int)(Math.cos(Math.PI/sideCount)*width), (int)(Math.sin(Math.PI/(sideCount+1)%sideCount)*height), (int)(Math.cos(Math.PI/(sideCount+1)%sideCount)*width), (int)(Math.sin(Math.PI/sideCount)*height));
		}
	}
	
	private void createColliders(){
		
	}
}
