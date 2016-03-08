package com.gooeywars.util.shape;

import com.gooeywars.entities.Entity;

public class Rectangle extends Polygon{
	
	public Rectangle(Entity ent){
		float x = ent.getX();
		float y = ent.getY();
		float width = ent.getWidth();
		float height = ent.getHeight();
		
		super.setX(x);
		super.setY(y);
		
		super.addVertice(x, y);
		super.addVertice(x, y + height);
		super.addVertice(x + width, y + height);
		super.addVertice(x + width, y);
	}
	
	public float max(){
		return 0;
	}
}
