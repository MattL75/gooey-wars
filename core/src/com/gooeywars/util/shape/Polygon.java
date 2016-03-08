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
	private Sprite sprite;
	
	public Polygon(){
		vertices = new Array<Vector2>();
	}
	
	public Polygon(Array<Vector2> vertices){
		this.vertices = vertices;
		genSprite();
	}
	
	
	public boolean collide(Polygon other) {
		int count = vertices.size;
		
		// test separation axes of current polygon
		for (int j = count - 1, i = 0; i < count; j = i, i++) {
			Vector2 v0 = vertices.get(j);
			Vector2 v1 = vertices.get(i);
			Vector2 axis = new Vector2(0,0);
			
			
			
			axis.x = v1.x - v0.x; 
			axis.y = v1.y - v0.y; 
			axis.nor();	

			axis.rotate90(1); 			
			if (separatedByAxis(axis, other))
				return false;
		}
		count = other.getVertices().size;
		
		// test separation axes of other polygon
		for (int j = count - 1, i = 0; i < count; j = i, i++) {
			Vector2 v0 = other.getVertices().get(j);
			Vector2 v1 = other.getVertices().get(i);
			Vector2 axis = new Vector2(0,0);
			
			
			axis.x = v1.x - v0.x;
			axis.y = v1.y - v0.y; 
			axis.nor();	

			axis.rotate90(1); 
			axis.nor();							

			if (separatedByAxis(axis, other))
				return false;
		}
		return true;
	}
	
	public boolean separatedByAxis(Vector2 axis, Polygon poly) {
		float mina = 0;
		float maxa = 0;
		
		mina = maxa = (float) axis.dot(vertices.get(0));
		
		for (int i = 1; i < vertices.size; i++) {
			float d = (float) axis.dot(vertices.get(i));
			if (d < mina)
				 mina = d;
			else if (d > maxa)
				maxa = d;
		}
		
		float minb = 0;
		float maxb = 0;
		
		minb = maxb = (float) axis.dot(poly.getVertices().get(0));
		
		for (int i = 1; i < poly.getVertices().size; i++) {
			float d = (float) axis.dot(poly.getVertices().get(i));
			if (d < minb)
				minb = d;
			else if (d > maxb)
				maxb = d;
		}
		
		return (mina > maxb) || (minb > maxa);
	}
	
	public void genSquare(Entity ent, float c){
		genSquare(ent.getX(), ent.getY(), c);
	}

	public void genSquare(float x, float y, float c){
		setPosition(x, y);
		addVertice(x, y);
		addVertice(x+c, y);
		addVertice(x+c, y+c/2);
		addVertice(x, y+c);
	}
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	
	private void genSprite(){
		Pixmap pix = new Pixmap(getHeight(), getWidth(), Format.RGBA8888);
		
		pix.setColor(Color.GREEN);
		
		for (int i = 0; i < vertices.size; i++) {
			Vector2 v1 = vertices.get(i);
			Vector2 v2 = vertices.get((i+1)%vertices.size);
			
			pix.drawLine((int) Math.round(v1.x-x), (int) Math.round(pix.getHeight() - v1.y+y-1), (int) Math.round(v2.x-x), (int) Math.round(pix.getHeight() - v2.y+y-1));
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
		addVertice(new Vector2(x, y));
	}
	
	public void addVertice(Vector2 v){
		vertices.add(v);
		genSprite();
	}
	
	public Array<Vector2> getVertices(){
		return vertices;
	}
	
	public void setVertices(Array<Vector2> vertices){
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
