package com.gooeywars.util.shape;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Circle extends Polygon{
	private float r;
	private float x;
	private float y;
	
	public Circle(){
		r = 10;
		x = 0;
		y = 0;
	}
	
	public Circle(float r){
		this.r = Math.abs(r);
		x = 0;
		y = 0;
	}
	
	public Circle(float r, float x, float y){
		this.r = Math.abs(r);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean collide(Polygon other){
		if(other.getClass().getName().equals("Circle")){
			Circle otherCircle = (Circle) other;
			float distance = (float) Math.pow(Math.pow(other.getX() - x, 2) + Math.pow(other.getX() - x, 2), 0.5);
			
			if(distance < (otherCircle.getR() + r)){
				return true;
			}
		}
		
		if(other.getClass().getName().equals("Polygon")){
			Array<Vector2> vertices = other.getVertices();
			Vector2 center = new Vector2(x,y);
			float squareRadius = r * r;
			
			for(int i = 0; i < vertices.size; i++){
				Vector2 start = vertices.get(i);
				Vector2 end = vertices.get((i+1)%vertices.size);
				if(Intersector.intersectSegmentCircle(start, end, center, squareRadius)){
					return true;
				}
			}
		}
		
		
		return false;
	}
	
	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	
}
