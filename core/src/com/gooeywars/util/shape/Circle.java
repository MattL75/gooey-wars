package com.gooeywars.util.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Circle extends Polygon{
	private float r;
	
	
	public Circle(){
		r = 10;
		setX(0);
		setY(0);
		genSprite();
	}
	
	public Circle(float r){
		this.r = Math.abs(r);
		setX(0);
		setY(0);
		genSprite();
	}
	
	public Circle(float r, float x, float y){
		this.r = Math.abs(r);
		setX(x);
		setY(y);
		genSprite();
	}
	
	//TODO implement
	@Override
	public Vector2 collide(Polygon other){
		if(other instanceof Circle){
			Circle circleOther = (Circle) other;
			float distance = (float) Math.pow((Math.pow(circleOther.getX() - getX(), 2) + Math.pow(circleOther.getY() - getY(), 2)), 0.5);
			if(distance < (circleOther.getR() + r)){
				return null;
			}
		}
		
		if(other instanceof Polygon){
			Array<Vector2> vertices = other.getVertices();
			Vector2 center = new Vector2(getX()+r,getY()+r);
			float squareRadius = r * r;
			
			for(int i = 0; i < vertices.size; i++){
				Vector2 start = vertices.get(i);
				Vector2 end = vertices.get((i+1)%vertices.size);
				
				if(Intersector.intersectSegmentCircle(start, end, center, squareRadius)){
					return null;
				}
			}
		}
		
		
		return null;
	}
	
	private void genSprite(){
		Pixmap pix = new Pixmap((int)(r*2+1), (int) (r*2+1), Format.RGBA8888);
		pix.setColor(Color.GREEN);
		pix.drawCircle((int)r,(int) r, (int) r);
		Texture texture = new Texture(pix);
		Sprite sprite = new Sprite(texture);
		sprite.setX(getX());
		sprite.setY(getY());
		setSprite(sprite);
		
	}
	
	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
		genSprite();
	}
}
