package com.gooeywars.util.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
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
	
	public Vector2 collide(Polygon other){
		
		if(other instanceof Circle){
			Circle circle = (Circle) other;
			
			return collideCircle(circle);
			
		} else {
			return collidePolygon(other);
			
		}
	}
	
	//TODO implement circle collision for displacement vector
	public Vector2 collideCircle(Circle other){
		
		Vector2 center = new Vector2(other.getX()+other.getR(),other.getY()+other.getR());
		float squareRadius = other.getR() * other.getR();
		
		for(int i = 0; i < vertices.size; i++){
			Vector2 start = vertices.get(i);
			Vector2 end = vertices.get((i+1)%vertices.size);
			if(Intersector.intersectSegmentCircle(start, end, center, squareRadius)){
				return new Vector2();
			}
		}
		return new Vector2();
	}
	
	public Vector2 collidePolygon(Polygon other) {
		int count = vertices.size;
		Vector2 disVec = new Vector2();
		
		Vector2 v0 = vertices.get(count-1);
		Vector2 v1 = vertices.get(0);
		Vector2 axis = new Vector2(0,0);
		
		
		
		axis.x = v1.x - v0.x; 
		axis.y = v1.y - v0.y; 
		axis.nor();	
		
		axis.rotate90(1); 
		
		//axis.setAngle(axis.angle()%180);
		
		disVec = separatedByAxis(axis, other);
		float tempOr = ((disVec.dot(axis) >= 0) ? 1: -1);
		disVec.scl(tempOr);
		Vector2 smallest = disVec.cpy();
		// test separation axes of current polygon
		for (int j = count -1 , i = 0; i < count; j = i, i++) {
			
			
			v0 = vertices.get(j);
			v1 = vertices.get(i);
			axis = new Vector2(0,0);
			
			
			
			axis.x = v1.x - v0.x; 
			axis.y = v1.y - v0.y; 
			axis.nor();	
			
			axis.rotate90(1); 
			
			
			disVec = separatedByAxis(axis, other);
			
			if(disVec.len2()==0){
				return new Vector2();
			}
			
			if(disVec.len2() <= smallest.len2()){
				smallest = disVec.cpy();
			}
		}
		
		count = other.getVertices().size;
		
		v0 = other.getVertices().get(count-1);
		v1 = other.getVertices().get(0);
		axis = new Vector2(0,0);
		
		
		axis.x = v1.x - v0.x;
		axis.y = v1.y - v0.y; 
		axis.nor();	
		
		axis.rotate90(1); 						
		//axis.setAngle(axis.angle()%180);
		
		disVec = separatedByAxis(axis, other);
		tempOr = ((disVec.dot(axis) >= 0) ? 1: -1);
		disVec.scl(tempOr);
		smallest = disVec.cpy();
		
		
		// test separation axes of other polygon
		for (int j = count -1, i = 0; i < count; j = i, i++) {
			v0 = other.getVertices().get(j);
			v1 = other.getVertices().get(i);
			axis = new Vector2(0,0);
			
			
			axis.x = v1.x - v0.x;
			axis.y = v1.y - v0.y; 
			axis.nor();	
			
			axis.rotate90(1);
			
			disVec = separatedByAxis(axis, other);
			
			
			if(disVec.len2()==0){
				return new Vector2();
			}
			
			if(disVec.len2() <= smallest.len2()){
				smallest = disVec.cpy();
			}
		}
		
		
		
		return smallest;
	}
	
	
	
	public Vector2 separatedByAxis(Vector2 axis, Polygon poly) {
		Vector2 proj;
		
		
		proj = projection(axis,vertices.get(0));
		
		Vector2 mina = proj.cpy();
		Vector2 maxa = proj.cpy();
		
		//Find maximum and minimum projection of points for this polygon
		for (int i = 1; i < vertices.size; i++) {
			
			proj = projection(axis,vertices.get(i));
			
			float projOr = ((proj.dot(axis) > 0) ? 1: -1);
			float minaOr = ((mina.dot(axis) > 0) ? 1: -1);
			float maxaOr = ((maxa.dot(axis) > 0) ? 1: -1);
			
			if (projOr * proj.len2() <= minaOr * mina.len2())
				mina = new Vector2(proj.x, proj.y);
			else if (projOr * proj.len2() > maxaOr * maxa.len2())
				maxa = new Vector2(proj.x, proj.y);
			
			
		}
		
		proj = projection(axis,poly.getVertices().get(0));
		
		Vector2 minb = proj.cpy();
		Vector2 maxb = proj.cpy();
		
		//Find maximum and minimum projection of points for other polygon
		for (int i = 1; i < poly.getVertices().size; i++) {
			
			proj = projection(axis,poly.getVertices().get(i));
			
			float projOr = ((proj.dot(axis) > 0) ? 1: -1);
			float minbOr = ((minb.dot(axis) > 0) ? 1: -1);
			float maxbOr = ((maxb.dot(axis) > 0) ? 1: -1);
			
			if (projOr * proj.len2() <= minbOr * minb.len2())
				minb = new Vector2(proj.x, proj.y);
			else if (projOr * proj.len2() > maxbOr * maxb.len2())
				maxb = new Vector2(proj.x, proj.y);
			
			
		}
		
		float minalen2 = mina.len2() * ((mina.dot(axis) > 0) ? 1: -1);
		float maxalen2 = maxa.len2() * ((maxa.dot(axis) > 0) ? 1: -1);
		float minblen2 = minb.len2() * ((minb.dot(axis) > 0) ? 1: -1);
		float maxblen2 = maxb.len2() * ((maxb.dot(axis) > 0) ? 1: -1);
		
		if(minalen2 < maxblen2 && maxalen2 > minblen2)
		{
			if(maxblen2 > maxalen2){
				Vector2 temp = new Vector2(minb.x - maxa.x, minb.y - maxa.y);
				return temp;
			}
			Vector2 temp = new Vector2(maxb.x - mina.x, maxb.y - mina.y);
			return temp;
		}
		
		return new Vector2();
	}
	
	public static Vector2 projection(Vector2 v1, Vector2 v2){
		float valDot = v1.dot(v2);
		float length = v1.len2();
		Vector2 point = new Vector2((valDot*v1.x)/length, (valDot*v1.y)/length);
		return point;
	}
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	
	private void genSprite(){
		Pixmap pix = new Pixmap(getWidth(), getHeight(), Format.RGBA8888);
		
		pix.setColor(Color.BLUE);
		
		for (int i = 0; i < vertices.size; i++) {
			Vector2 v1 = vertices.get(i);
			Vector2 v2 = vertices.get((i+1)%vertices.size);
			
			pix.drawLine((int) Math.round(v1.x-x), (int) Math.round(pix.getHeight() - v1.y+y-1), (int) Math.round(v2.x-x), (int) Math.round(pix.getHeight() - v2.y+y-1));
		}
		
		Texture texture = new Texture(pix);
		pix.dispose();
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

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	
	
}
