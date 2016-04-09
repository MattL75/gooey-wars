package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.game.Main;
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Polygon;

public class Goo extends Entity{
	private int radius;
	private int owner;
	private Color color; 
	private int colorInt;
	private GooProperty property;
	private int propInt;
	private boolean isSelected;
	
	private int element1;
	private int element2;
	
	private int sideCount = 8;
	
	public Goo(){
		createGoo(0, 0, 50, new Vector2(), new Vector2(), new Vector2(), 0, -1, 0, 0, 0);
	}
	
	public Goo(float x, float y){
		createGoo(x, y, 50, new Vector2(), new Vector2(), new Vector2(), 0, -1, 0, 0, 0);
	}
	
	public Goo(float x, float y, int mass){
		createGoo(x, y, mass, new Vector2(), new Vector2(), new Vector2(), 0, -1, 0, 0, 0);
	}
	
	public Goo(float x, float y, int mass, int prop, int owner, int color){
		createGoo(x, y, mass, new Vector2(), new Vector2(), new Vector2(), prop, owner, color, 0, 0);
		
	}
	
	public Goo(float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		
		createGoo(x, y, mass, force, velocity, acceleration, 0, -1 , 0, 0, 0);
		
	}
	
	public Goo(float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration, int owner, int prop, int color, int element1, int element2){
		createGoo(x, y, mass, force, velocity, acceleration, prop, owner, color, element1, element2);
		
	}
	
	public void createGoo(float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration, int prop, int owner, int color, int element1, int element2){
		setPhysicsEnabled(true);
		colorInt = color;
		propInt = prop;
		
		this.element1 = element1;
		this.element2 = element2;
		
		setX(x);
		setY(y);
		radius = Math.round(mass);
		setWidth((radius+1)*2.0f);
		setHeight((radius+1)*2.0f);
		
		setMass(mass);
		setForce(force);
		setVelocity(velocity);
		setAcceleration(acceleration);
		
		property = new GooProperty(prop);
		this.color = genColor(color);
		this.owner = owner;
		
		setType(Entity.GOO);
		
		createSprite();
		createColliders();
	}
	
	private void createSprite(){
		getSprite().getTexture().dispose();
		Pixmap pix = new Pixmap((int)getWidth(), (int)getWidth(), Format.RGBA8888);
		pix.setColor(color);
		
		pix.fillCircle(radius, radius, radius);
		
		Texture texture = new Texture(pix);
		pix.dispose();
		
		Sprite sprite = new Sprite(texture);
		setSprite(sprite);
		
	}
	
	private void createColliders(){
		clearColliders();
		Polygon poly = new Polygon();
		for(int i = 0; i < sideCount; i++){
			float x = (float) (Math.cos(Math.PI *2* i/sideCount + Math.PI/sideCount))*(radius+1)+radius+1;
			float y = (float) (Math.sin(Math.PI *2* i/sideCount + Math.PI/sideCount))*(radius+1)+radius+1;
			poly.addVertice(x, y);
		}
		
		Collider coll = new Collider(poly);
		coll.setDrawable(false);
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
		default: return null;
		}
	}
	
	private Color genColorSelected(int c){
		switch(c){
		case 0: return Color.DARK_GRAY; 
		case 1: return Color.PINK; 
		case 2: return Color.LIME; 
		case 3: return Color.CYAN; 
		default: return null;
		}
	}
	
	@Override
	public Vector2 collide(Entity other){
		Vector2 displacement = new Vector2();
		
		Vector2 overlap = super.collide(other);
		float len2 = overlap.len2();
		float len = overlap.len();
		
		if(overlap.len2() > 0){
			if(other instanceof Goo){
				Goo goo = (Goo) other;
				
				if(owner != goo.getOwner()){
					if(len2 > (getMass() * getMass()) || len2 > (goo.getMass() * goo.getMass())){
						annihilate(goo.getMass());
						goo.annihilate(getMass());
					} else {
						
						annihilate(len);
						goo.annihilate(len);
					}
					
					
					
				} else {
					displacement = super.collide(other);
				}
				
			} else if(other instanceof Geyser) {
				Geyser geyser = (Geyser) other;
				if(!geyser.isOccupied()){
					float gCenterX = geyser.getX() + geyser.getWidth()/2;
					float gCenterY = geyser.getY() + geyser.getHeight()/2;
					float gooCenterX = getX() + getWidth()/2;
					float gooCenterY = getY() + getHeight()/2;
					Vector2 force = new Vector2(gCenterX -gooCenterX, gCenterY - gooCenterY);
					force.scl(20);
					addForce(force);
				} else {
					
				}
				
			} else if(other instanceof Environment || other instanceof Obstacle){
				displacement = super.collide(other);
			}
		}
		
		return displacement;
		//System.out.println("Goo");
		
	}
	
	public void split(Vector2 dirVect){
		
	}
	
	public void merge(Goo other){
		
	}
	
	public void build(Array<Vector2> path){
		
	}
	
	public void toggleAttackMode(){
		
	}
	
	public void annihilate(float overlap){
		float len = overlap;
		float radiusVar;
		float initRad;
		
		if(len > 0){
			if(getMass() > 4){
				setMass((int)(getMass() - len));
				initRad = radius;
				radius = Math.round(getMass());
				radiusVar = initRad - radius;
				setWidth(radius*2);
				setHeight(radius*2);
				setX(getX()+radiusVar);
				setY(getY()+radiusVar);
				createSprite();
				createColliders();
			} else {
				destroy();
			}
		}
		
	}
	
	public void destroy(){
		Main.findGameBox("game").removeEntity(getId());
		getSprite().getTexture().dispose();
		clearColliders();
	}
	
	//super.getSaveData,owner,colorInt,propInt
	@Override
	public String getSaveData(){
		String data = super.getSaveData();
		data += "," + owner + "," + propInt + "," + colorInt +  "," + element1 + "," + element2;
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
		createSprite();
	}

	public int getColorInt() {
		return colorInt;
	}

	public void setColorInt(int colorInt) {
		this.colorInt = colorInt;
		if(isSelected){
			color = genColorSelected(colorInt);
			createSprite();
		} else {
			color = genColor(colorInt);
			createSprite();
		}
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
		if(isSelected){
			color = genColorSelected(colorInt);
			createSprite();
		} else {
			color = genColor(colorInt);
			createSprite();
		}
	}
}
