package com.gooeywars.util.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;

public class Polygon{
	private Array<Vector2> vertices;
	
	private float x;
	private float y;
	
	public int count;
	
	public float min;
	public float max;
	
	private float mina;
	private float maxa;
	private float minb;
	private float maxb;
	
	private Sprite sprite;
	
	public Polygon(){
		vertices = new Array<Vector2>();
		
		
	}
	
	public Polygon(Array<Vector2> vertices){
		this.vertices = vertices;
		count = vertices.size;
		genSprite();
	}
	
	
	public boolean collide(Polygon other) {
		count = vertices.size;
		for(int i = 0; i < vertices.size; i++){
			System.out.println(vertices.get(i));
		}
		// test separation axes of current polygon
		for (int j = count - 1, i = 0; i < count; j = i, i++) {
			Vector2 v0 = vertices.get(j);
			Vector2 v1 = vertices.get(i);
			Vector2 edge = new Vector2(0, 0);
			edge.x = v1.x - v0.x; // edge
			edge.y = v1.y - v0.y; // edge

			Vector2 axis = edge.rotate90(1); // Separate axis is perpendicular
												// to the edge
			if (separatedByAxis(axis, other))
				return false;
		}
		other.count = other.getVertices().size;
		
		// test separation axes of other polygon
		for (int j = other.count - 1, i = 0; i < other.count; j = i, i++) {
			Vector2 v0 = other.getVertices().get(j);
			Vector2 v1 = other.getVertices().get(i);
			Vector2 axis = new Vector2(0,0);
			
			
			
			axis.x = v1.x - v0.x; // edge
			axis.y = v1.y - v0.y; // edge
			axis.rotate90(1); // Separate axis is perpendicular
			
			axis.nor();							// to the edge
			if (separatedByAxis(axis, other))
				return false;
		}
		return true;
	}

	public void calculateInterval(Vector2 axis) {
		this.min = this.max = (float) vertices.get(0).dot(axis);
		
		for (int i = 1; i < count; i++) {
			float d = (float) axis.dot(vertices.get(i));
			if (d < this.min)
				this.min = d;
			else if (d > this.max)
				this.max = d;
		}
	}

	public boolean intervalsSeparated(float mina, float maxa, float minb, float maxb) {
		return (mina > maxb) || (minb > maxa);
	}

	public boolean separatedByAxis(Vector2 axis, Polygon poly) {
		calculateInterval(axis);
		mina = min;
		maxa = max;
		
		
		
		poly.calculateInterval(axis);
		minb = poly.min;
		maxb = poly.max;
		
		return intervalsSeparated(mina, maxa, minb, maxb);
	}
	
	public void genSquare(Entity ent, float c){
		genSquare(ent.getX(), ent.getY(), c);
	}
	
	public void genSquare(float x, float y, float c){
		setPosition(x, y);
		addVertice(x, y);
		addVertice(x+c, y);
		addVertice(x+c/2, y+c);
		addVertice(x, y+c);
		for(int i = 0; i < vertices.size; i++){
			//System.out.println(vertices.get(i));
		}
		//System.out.println("Position: " + x + ", " + y);
	}
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	
	private void genSprite(){
		Pixmap pix = new Pixmap(getHeight(), getWidth(), Format.RGBA8888);
		
		pix.setColor(Color.CORAL);
		
		for (int i = 0; i < vertices.size; i++) {
			
			Vector2 v1 = vertices.get(i);
			
			Vector2 v2 = vertices.get((i+1)%vertices.size);
			
			
			pix.drawLine((int) Math.round(v1.x-x), (int) Math.round(v1.y-y), (int) Math.round(v2.x-x), (int) Math.round(v2.y-y));
		}
		
		
		Texture texture = new Texture(pix);
		
		sprite = new Sprite(texture);
		sprite.setX(x);
		sprite.setY(y);
	}
	
	private int getHeight(){
		float maxHeight = 0;
		float minHeight = vertices.first().y;
		float yH = 0;
		for(int i = 0; i < vertices.size; i++){
			yH = vertices.get(i).y;
			if(yH > maxHeight){
				maxHeight = yH;
			}
			
			if(yH < minHeight){
				minHeight = yH;
			}
		}
		
		return (int) Math.ceil(maxHeight - minHeight) + 1;
	}
	
	private int getWidth(){
		float maxWidth = 0;
		float minWidth = vertices.first().x;
		float xW = 0;
		for(int i = 0; i < vertices.size; i++){
			xW = vertices.get(i).x;
			if(xW > maxWidth){
				maxWidth = xW;
			}
			
			if(xW < minWidth){
				minWidth = xW;
			}
		}
		
		return (int) Math.floor(maxWidth - minWidth) + 1;
	}
	
	public void addVertice(float x, float y){
		count = vertices.size;
		vertices.add(new Vector2(x, y));
		genSprite();
	}
	
	public Array<Vector2> getVertices(){
		return vertices;
	}
	
	public void setVertices(Array<Vector2> vertices){
		count = vertices.size;
		this.vertices = vertices;
		genSprite();
	}
	
	public void setX(float x){
		setPosition(x, this.y);
		
	}
	
	public void setY(float y){
		setPosition(this.x, y);
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public void setPosition(float x, float y){
		Vector2 posVar = new Vector2(x - this.x, y - this.y);
		this.x = x;
		this.y = y;
		
		for(int i = 0; i < vertices.size; i++){
			vertices.get(i).add(posVar);
		}
		if(sprite != null){
			sprite.setX(x);
			sprite.setY(y);
		}
	}
	
}
