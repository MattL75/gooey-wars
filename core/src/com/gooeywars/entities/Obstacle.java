package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Square;

public class Obstacle extends Entity{
	private Array<Vector2> vertices;
	private Color color;
	private int colorInt;
	private int typeObstacle;
	
	
	public Obstacle(){
		createObstacle(0,0,0,0,50,50);
	}
	
	public Obstacle(int x, int y){
		createObstacle(x,y,0,0,50,50);
	}
	
	public Obstacle(int x, int y, int color){
		createObstacle(x,y,color,0,50,50);
	}
	
	public Obstacle(int x, int y, int color, int type){
		createObstacle(x,y,color,type,50,50);
	}
	
	public Obstacle(int x, int y, int color, int type, float width, float height){
		createObstacle(x,y,color,type,width,height);
	}
	
	public void createObstacle(int x, int y, int color, int type, float width, float height){
		setIsObstacle(true);
		setType(Entity.OBSTACLE);
		vertices = new Array<Vector2>();
		this.color = Color.PURPLE;
		colorInt = color;
		
		switch(type){
		case 0:createSquare(Math.max(width, height));break;
		}
		
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
		
	}
	
	public void createSquare(float size){
		int sizeInt = (int) size;
		/*vertices.add(new Vector2(0,0));
		vertices.add(new Vector2(0,sizeInt));
		vertices.add(new Vector2(sizeInt,sizeInt));
		vertices.add(new Vector2(sizeInt,0));*/
		
		Pixmap pix = new Pixmap((int) sizeInt, (int) sizeInt, Format.RGBA4444);
		pix.setColor(color);
		pix.fillRectangle(0, 0, sizeInt, sizeInt);
		
		Texture tex = new Texture(pix);
		pix.dispose();
		setSprite(new Sprite(tex));
		
		Square square = new Square(sizeInt,0,0);
		
		Collider collider = new Collider(square);
		
		getColliders().add(collider);
	}
	
	@Override
	public String getSaveData(){
		String data  = super.getSaveData() + "," + colorInt + "," + typeObstacle;
		return data;
	}
}
