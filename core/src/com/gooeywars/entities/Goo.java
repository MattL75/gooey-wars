package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.gooeywars.components.MoveHandler;
import com.gooeywars.game.GameBox;
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
	
	private MoveHandler mover;
	
	private Sprite element1Sprite;
	private Sprite element2Sprite;
	private Sprite gooTypeSprite;
	
	private final int modSize = 20;
	
	Timer lifespan;
	Timer onFireTimer;
	
	public Goo(){
		createGoo(0, 0, 50, new Vector2(), new Vector2(), new Vector2(), 0, -1, 0, -1, -1);
	}
	
	public Goo(float x, float y){
		createGoo(x, y, 50, new Vector2(), new Vector2(), new Vector2(), 0, -1, 0, -1, -1);
	}
	
	public Goo(float x, float y, int mass){
		createGoo(x, y, mass, new Vector2(), new Vector2(), new Vector2(), 0, -1, 0, -1, -1);
	}
	
	public Goo(float x, float y, int mass, int prop, int owner, int color){
		createGoo(x, y, mass, new Vector2(), new Vector2(), new Vector2(), prop, owner, color, -1, -1);
		
	}
	
	public Goo(float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		createGoo(x, y, mass, force, velocity, acceleration, 0, -1 , 0, -1, -1);
	}
	
	public Goo(float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration, int owner, int prop, int color, int element1, int element2){
		createGoo(x, y, mass, force, velocity, acceleration, prop, owner, color, element1, element2);
		
	}
	
	public void createGoo(float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration, int prop, int owner, int color, int element1, int element2){
		setPhysicsEnabled(true);
		colorInt = color;
		propInt = prop;
		
		onFireTimer = new Timer();
		onFireTimer.scheduleTask(new onFireTask(), 1, 1);
		onFireTimer.stop();
		
		element1Sprite = new Sprite(new Texture(GeyserProperty.getGooElementPix(element1)));
		element2Sprite = new Sprite(new Texture(GeyserProperty.getGooElementPix(element2)));
		
		setElement1(element1);
		setElement2(element2);
		
		
		mover = Main.findGameBox("game").getMover();
		
		setForce(force);
		setVelocity(velocity);
		setAcceleration(acceleration);
		
		property = new GooProperty(prop);
		this.color = genColor(color);
		this.owner = owner;
		
		setType(Entity.GOO);
		
		setVelocityFactor(property.getVelocityFactor());
		
		createSprite();
		
		setX(x);
		setY(y);
		setMass(mass);
		
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
		
		Sprite actualSprite = getSprite();
		
		
		
		gooTypeSprite = new Sprite(new Texture(property.getGooTypePix()));
		gooTypeSprite.setX(actualSprite.getX());
		gooTypeSprite.setY(actualSprite.getY() + actualSprite.getHeight() - gooTypeSprite.getHeight());
		if(property.getPropInt() == 0){
			gooTypeSprite.setSize(0, 0);
		} else {
			gooTypeSprite.setSize(modSize, modSize);
		}
	}
	
	@Override
	public void draw(SpriteBatch batch){
		//System.out.println("Element 1: " + element1);
		//System.out.println("Element 2: " + element2);
		super.draw(batch);
		element1Sprite.draw(batch);
		element2Sprite.draw(batch);
		gooTypeSprite.draw(batch);
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
						
						annihilate(goo.getProperty().getDamage() * len/property.getDefence());
						goo.annihilate(property.getDamage() * len/ goo.getProperty().getDefence());
					}
					
					
					
				} else {
					if(isMerging && goo.isMerging()){
						join(goo);
					} else {
						//displacement = overlap.cpy();
					}
				}
				
			} else if(other instanceof Geyser) {
				Geyser geyser = (Geyser) other;
				geyser.mine(this);
				
			} else if(other instanceof Environment || other instanceof Obstacle){
				displacement = overlap.cpy();
			}
			
		} else if(other instanceof Geyser){			
			((Geyser) other).stopMining(this);
		}
		
		return displacement;
		
	}
	
	public void move(float x, float y){
		System.out.println("Moving");
		mover.cancel(this);
		mover.move(this, new Vector2(x, y));
	}
	
	public void attack(float x, float y){
		if(property.isRanged()){
			
			
		} else {
			mover.cancel(this);
			mover.move(this, new Vector2(x, y));
			
		}
	}
	
	public boolean split(Vector2 dirVect){
		System.out.println("Splitting");
		if(getMass() > SMALLEST_MASS * 2){
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
		/*float totalX = 0;
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
		}*/
	}
	
	public void build(Array<Vector2> path){
		
	}
	
	public void toggleAttackMode(){
		
	}
	
	public void join(Goo other){
		setMass(getMass() + other.getMass());
		other.destroy();
	}
	
	public void react(){
		property.react(element1, element2);
		if(property.getPropInt() == -1){
			explode();
		} else {
			gooTypeSprite = new Sprite(new Texture(property.getGooTypePix()));
			gooTypeSprite.setX(getSprite().getX());
			gooTypeSprite.setY(getSprite().getY() + getSprite().getHeight() - gooTypeSprite.getHeight());
			if(property.getPropInt() == 0){
				gooTypeSprite.setSize(0, 0);
			} else {
				gooTypeSprite.setSize(modSize, modSize);
			}
		}
		
	}
	
	public void setLifeSpan(float seconds){
		lifespan = new Timer();
		lifespan.scheduleTask(new lifeSpanTask(), seconds);
	}
	
	public void explode(){
		GameBox game = Main.findGameBox("game");
		int mass = getMass();
		int numberCreated = mass/10;
		for(int i = 0; i < numberCreated; i++){
			Goo goo = new Goo(getX() + getWidth()/2,getY() + getHeight()/2,(int)(2*mass/numberCreated * Math.random()),0,getOwner(),getColorInt());
			goo.setLifeSpan(1);
			Vector2 force = new Vector2(1,1);
			force.setToRandomDirection();
			force.setLength(10);
			goo.setVelocity(force);
			game.addEntity(goo);
		}
		
		destroy();
	}
	
	public void setOnFire(){
		if(property.isFlammable()){
			property.onFire = true;
			onFireTimer.start();
		}
	}
	
	public void extinguish(){
		property.onFire = false;
		onFireTimer.stop();
	}
	
	public void annihilate(float overlap){
		float len = overlap;
		if(len > 0){
			changeMass((int)(-1 * len));
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
	
	@Override
	public void setX(float x){
		super.setX(x);
		element1Sprite.setX(x + getSprite().getWidth() - element1Sprite.getWidth());
		element2Sprite.setX(x + getSprite().getWidth() - element1Sprite.getWidth() - element2Sprite.getWidth());
		gooTypeSprite.setX(x);
	}
	
	@Override
	public void setY(float y){
		super.setY(y);
		element1Sprite.setY(y);
		element2Sprite.setY(y);
		
		gooTypeSprite.setY(y + getSprite().getHeight() - gooTypeSprite.getHeight());
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
		element1Sprite.getTexture().dispose();
		element1Sprite.getTexture().dispose();
		element1Sprite = new Sprite(new Texture(GeyserProperty.getGooElementPix(element1)));
		Sprite actualSprite = getSprite();
		element1Sprite.setX(actualSprite.getX() + actualSprite.getWidth() - element1Sprite.getWidth());
		element1Sprite.setY(actualSprite.getY());
		if(element1 == -1){
			element1Sprite.setSize(0, 0);
		} else {
			element1Sprite.setSize(modSize, modSize);
		}
		
	}

	public int getElement2() {
		return element2;
	}

	public void setElement2(int element2) {
		this.element2 = element2;
		element2Sprite.getTexture().dispose();
		element2Sprite = new Sprite(new Texture(GeyserProperty.getGooElementPix(element2)));
		Sprite actualSprite = getSprite();
		element2Sprite.setX(actualSprite.getX() + actualSprite.getWidth() - element2Sprite.getWidth());
		element2Sprite.setY(actualSprite.getY());
		if(element2 == -1){
			element2Sprite.setSize(0, 0);
		} else {
			element2Sprite.setSize(modSize, modSize);
		}
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	private void genGrid(){
		if(radius < 10){
			grid = new Grid(Main.findGameBox("game").size, 10);
		} else {
			grid = new Grid(Main.findGameBox("game").size, radius);
		}
		
	}
	
	class lifeSpanTask extends Task{

		@Override
		public void run() {
			destroy();
		}
		
	}
	
	class onFireTask extends Task{

		@Override
		public void run() {
			if(getMass() < SMALLEST_MASS){
				destroy();
			} else {
				changeMass(-1);
			}
		}
		
	}
}


