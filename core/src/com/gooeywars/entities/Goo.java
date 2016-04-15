package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.game.Main;
import com.gooeywars.pathfinding.Grid;
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Polygon;

public class Goo extends Entity{
	private float radius;
	private int owner;
	private Color color; 
	private int colorInt;
	private GooProperty property;
	private int propInt;
	private boolean isSelected;
	private boolean isMerging;
	
	private Grid grid;
	
	private int element1;
	private int element2;
	
	private int sideCount = 8;
	
	public static final int SMALLEST_MASS = 5;
	
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
		radius = Math.round(mass)/2;
		setWidth((radius+1)*2.0f);
		setHeight((radius+1)*2.0f);
		
		
		setForce(force);
		setVelocity(velocity);
		setAcceleration(acceleration);
		
		property = new GooProperty(prop);
		this.color = genColor(color);
		this.owner = owner;
		super.setMass(mass);
		setType(Entity.GOO);
		
		setVelocityFactor(property.getVelocityFactor());
		
		createSprite();
		createColliders();
	}
	
	private void createSprite(){
		getSprite().getTexture().dispose();
		Pixmap pix = new Pixmap((int)getWidth(), (int)getWidth(), Format.RGBA8888);
		
		pix.setColor(color);
		
		pix.fillCircle((int)radius, (int)radius, (int)radius);
		
		Texture texture = new Texture(pix);
		pix.dispose();
		
		Sprite sprite = new Sprite(texture);
		setSprite(sprite);
		
	}
	
	private void createColliders(){
		clearColliders();
		Polygon poly = new Polygon();
		for(int i = 0; i < sideCount; i++){
			float x = (float) (Math.cos(Math.PI *2* i/sideCount + Math.PI/sideCount))*(radius)+radius;
			float y = (float) (Math.sin(Math.PI *2* i/sideCount + Math.PI/sideCount))*(radius)+radius;
			poly.addVertice(x, y);
		}
		
		Collider coll = new Collider(poly);
		coll.setDrawable(true);
		Array<Collider> colls = new Array<Collider>();
		colls.add(coll);
		
		setColliders(colls);
	}
	
	public void changeMass(int massVar){
		
		float radiusVar; 
		float initRad;
		
		super.setMass((getMass()+massVar));
		radiusVar = ((massVar))/2f;
		float width = (radius+radiusVar) * 2;
		float height = (radius+radiusVar) * 2;
		radius = (radius+radiusVar);
		
		genGrid();
		
		if(getMass() > SMALLEST_MASS){
			
			
			
			setWidth(width);
			setHeight(height);
			setX(getX()-radiusVar*1f);
			setY(getY()-radiusVar*1f);
			createSprite();
			createColliders();
		} else {
			destroy();
		}
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
					if(isMerging && goo.isMerging()){
						join(goo);
					} else {
						displacement = overlap.cpy();
					}
				}
				
			} else if(other instanceof Geyser) {
				Geyser geyser = (Geyser) other;
				mine(geyser);
				
			} else if(other instanceof Environment || other instanceof Obstacle){
				displacement = overlap.cpy();
			}
			
		} else if(other instanceof Geyser){			
			if(started){
				stopMining();
			}
			//System.out.println("stopping");
		}
		
		return displacement;
		//System.out.println("Goo");
		
	}
	
	public boolean split(Vector2 dirVect){
		if(getMass() > SMALLEST_MASS * 2){
			System.out.println(radius);
			System.out.println(getMass());
			Goo brother = new Goo(this.getX()+ getWidth(), this.getY()+getWidth()/4, this.getMass()/2, this.getForce().cpy(), this.getVelocity().cpy(), getAcceleration().cpy().add(dirVect.cpy().scl(-10000)), this.getOwner(), propInt, colorInt, element1, element2);
			brother.addForce(dirVect.cpy().scl(10000));
			Main.findGameBox("game").addEntity(brother);
			setMass(getMass()/2);
			addForce(dirVect.cpy().scl(-10000));
			return true;
		}
		return false;
	}
	
	public void merge(Array<Goo> mergingGoos){
		float totalX = 0;
		float totalY = 0;
		for(int i = 0; i < mergingGoos.size; i++){
			mergingGoos.get(i).setMerging(true);
			totalX += mergingGoos.get(i).getX();
			totalY += mergingGoos.get(i).getY();	
		}
		
		Vector2 joinPoint = new Vector2(totalX/mergingGoos.size, totalY/mergingGoos.size);
		
		for(int i = 0; i < mergingGoos.size; i++){
			Main.findGameBox("game").getMover().cancel(mergingGoos.get(i));
			Main.findGameBox("game").getMover().move(mergingGoos.get(i), joinPoint);
		}
	}
	
	public void build(Array<Vector2> path){
		
	}
	
	public void toggleAttackMode(){
		
	}
	
	public void join(Goo other){
		setMass(getMass() + other.getMass());
		other.destroy();
	}
	
	public void annihilate(float overlap){
		float len = overlap;
		float radiusVar;
		float initRad;
		
		if(len > 0){
			changeMass((int)(-1 * len));
		}
		
	}
	
	public void destroy(){
		Main.findGameBox("game").removeEntity(getId());
		getSprite().getTexture().dispose();
		clearColliders();
	}
	
	boolean started;
	boolean grabbed;
	Geyser miningGeyser;
	
	public void mine(Geyser geyser){
		System.out.println(started);
		if(!started){
			/*goo.setElement2(goo.getElement1());
			goo.setElement1(property.element);*/
			miningGeyser = geyser;
			geyser.mine(this);
			started = true;
		} else {
			float gCenterX = geyser.getX() + geyser.getWidth() / 2;
			float gCenterY = geyser.getY() + geyser.getHeight() / 2;
			float gooCenterX = getX() + getWidth() / 2;
			float gooCenterY = getY() + getHeight() / 2;
			Vector2 force = new Vector2();
			
			force = new Vector2(25 * (gCenterX - gooCenterX), 25 * (gCenterY - gooCenterY));
			
			
			if(!grabbed){
				System.out.println("not grabbed yet");
				if(Math.abs(getX() - geyser.getX()) < 10 && Math.abs(getY() - geyser.getY()) < 10){
					setX(geyser.getX()- getWidth()/2);
					setY(geyser.getY() - getHeight()/2);
					grabbed = true;
				} else {
					addForce(force);
				}
			}
		}
	}
	
	public void stopMining(){
		grabbed = false;
		started = false;
		miningGeyser.stopMining();
		
	}
	
	//super.getSaveData,owner,colorInt,propInt
	@Override
	public String getSaveData(){
		String data = super.getSaveData();
		data += "," + owner + "," + propInt + "," + colorInt +  "," + element1 + "," + element2;
		return data;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
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
	
	@Override
	public void setMass(int mass) {
		changeMass(mass - getMass());
	}

	public boolean isMerging() {
		return isMerging;
	}

	public void setMerging(boolean isMerging) {
		this.isMerging = isMerging;
	}

	public int getElement1() {
		return element1;
	}

	public void setElement1(int element1) {
		this.element1 = element1;
	}

	public int getElement2() {
		return element2;
	}

	public void setElement2(int element2) {
		this.element2 = element2;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	private void genGrid(){
		grid = new Grid(Main.findGameBox("game").size,radius);
	}
}
