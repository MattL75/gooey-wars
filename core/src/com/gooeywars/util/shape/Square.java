package com.gooeywars.util.shape;

import com.gooeywars.entities.Entity;

public class Square extends Rectangle{
	public Square(Entity ent){
		

		float c = 0;
		
		if(ent.getWidth() > ent.getHeight()){
			c = ent.getWidth();
		}
		else{
			c = ent.getHeight();
		}
		
		super.setX(ent.getX());
		super.setY(ent.getY());
		genRectangle(ent.getX(), ent.getY(), c, c);
	}
	
	public Square(Entity ent, float c){
		super.setX(ent.getX());
		super.setY(ent.getY());
		genRectangle(ent.getX(), ent.getY(), c, c);
	}
}
