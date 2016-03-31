package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Circle;
import com.gooeywars.util.shape.Polygon;

public class Goo extends Entity{
	private int radius;
	private int owner;
	private Color color; 
	private GooProperty property;
	private boolean isSelected;
	
	private int sideCount = 8;
	
	public Goo(){
		createGoo(0, 0, 100, new Vector2(), new Vector2(), new Vector2(), 0, -1, 0);
	}
	
	public Goo(float x, float y){
		createGoo(x, y, 100, new Vector2(), new Vector2(), new Vector2(), 0, -1, 0);
	}
	
	public Goo(float x, float y, int mass){
		createGoo(x, y, mass, new Vector2(), new Vector2(), new Vector2(), 0, -1, 0);
	}
	
	public Goo(float x, float y, int mass, int prop, int owner, int color){
		createGoo(x, y, mass, new Vector2(), new Vector2(), new Vector2(), prop, owner, color);
		
	}
	
	public Goo(float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		
		createGoo(x, y, mass, force, velocity, acceleration, 0, -1 ,0);
		
	}
	
	public Goo(float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration, int prop, int owner, int color){
		createGoo(x, y, mass, force, velocity, acceleration, prop, owner, color);
		
	}
	
	public void createGoo(float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration, int prop, int owner, int color){
		setPhysicsEnabled(true);
		
		setX(x);
		setY(y);
		radius = mass/10;
		setWidth((radius+1)*2.0f);
		setHeight((radius+1)*2.0f);
		
		setMass(mass);
		setForce(force);
		setVelocity(velocity);
		setAcceleration(acceleration);
		
		property = new GooProperty(prop);
		this.color = genColor(color);
		this.owner = owner;
		
		setType(1);
		
		createSprite();
		createColliders();
	}
	
	private void createSprite(){
		Pixmap pix = new Pixmap((int)getWidth(), (int)getWidth(), Format.RGBA8888);
		
		pix.setColor(Color.PINK);
		
		pix.fillCircle(radius, radius, radius);
		
		Texture texture = new Texture(pix);
		pix.dispose();
		
		Sprite sprite = new Sprite(texture);
		
		setSprite(sprite);
		
	}
	
	private void createColliders(){
		Polygon poly = new Polygon();
		for(int i = 0; i < sideCount; i++){
			float x = (float) (Math.cos(Math.PI *2* i/sideCount + Math.PI/sideCount))*radius+radius;
			float y = (float) (Math.sin(Math.PI *2* i/sideCount + Math.PI/sideCount))*radius+radius;
			poly.addVertice(x, y);
		}
		
		Collider coll = new Collider(poly);
		coll.setDrawable(true);
		Array<Collider> colls = new Array<Collider>();
		colls.add(coll);
		
		setColliders(colls);
	}
	
	private Color genColor(int c){
		switch(c){
		case 0: return Color.BLACK; 
		case 1: return Color.RED; 
		case 2: return Color.GREEN; 
		case 3: return Color.BLUE; 
		case 4: return Color.PURPLE; 
		case 5: return Color.PINK;
		default: return null;
		}
	}
	
	@Override
	public String getSaveData(){
		String data = super.getSaveData();
		return data;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
		createSprite();
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public GooProperty getProperty() {
		return property;
	}

	public void setProperty(GooProperty property) {
		this.property = property;
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	//TODO deal with color change when selected
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
