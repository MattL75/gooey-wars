package com.gooeywars.util.shape;

import com.gooeywars.entities.Entity;

public class Rectangle extends Polygon{
	
	public Rectangle(){
		
	}
	
	public Rectangle(Entity ent){
		float x = ent.getX();
		float y = ent.getY();
		float width = ent.getWidth();
		float height = ent.getHeight();
		
		super.setX(x);
		super.setY(y);
		
		genRectangle(x,y,width,height);
	}
	
	public Rectangle(Entity ent, float width, float height){
		float x = ent.getX();
		float y = ent.getY();
		
		super.setX(x);
		super.setY(y);
		
		genRectangle(x,y,width,height);
	}
	
	public void genRectangle(float x, float y,float width, float height){
		super.addVertice(x, y);
		super.addVertice(x, y + height/2);
		super.addVertice(x + width, y + height);
		super.addVertice(x + width, y);
	}
}
