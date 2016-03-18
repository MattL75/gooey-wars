package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.physics.Collider;

public class Entity {
	protected int type;
	protected static final int ENTITY = 0;
	protected static final int GOO = 1;
	protected static final int GEYSER = 2;
	protected static final int ENVIRONMENT = 3;
	protected static final int OBSTACLE = 4;
	
	protected Sprite sprite;
	protected String texturePath;
	protected int textureType;
	protected Pixmap pix;
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	
	protected boolean physicsEnabled;
	
	protected Vector2 force;
	protected Vector2 velocity;
	protected Vector2 acceleration;
	protected Array<Collider> colliders;
	protected int mass;
	
	protected int id;

	public Entity(){
		initEntity(false, new Array<Collider>(), 0, 0, false, 0, null, null, null);
	}
	
	public Entity(Pixmap pix){
		initEntity(pix, new Array<Collider>(), 0, 0, false, 0, null, null, null);
	}
	
	public Entity(String texturePath){	
		initEntity(texturePath, new Array<Collider>(), 0, 0, false, 0, null, null, null);
	}
	
	public Entity(Pixmap pix, float x, float y){
		initEntity(pix, new Array<Collider>(), x, y, false, 0, null, null, null);
	}
	
	public Entity(String texturePath, float x, float y){
		initEntity(texturePath, new Array<Collider>(), x, y, false, 0, null, null, null);
	}
	
	public Entity(Pixmap pix, Array<Collider> colliders){
		initEntity(pix, colliders, 0, 0, false, 0, null, null, null);
	}
	
	public Entity(String texturePath, Array<Collider> colliders){
		initEntity(texturePath, colliders, 0, 0, false, 0, null, null, null);
	}
	
	public Entity(Pixmap pix, Array<Collider> colliders, float x, float y){
		initEntity(pix, colliders, x, y, false, 0, null, null, null);
	}
	
	public Entity(String texturePath, Array<Collider> colliders, float x, float y){
		initEntity(texturePath, colliders, x, y, false, 0, null, null, null);
	}
	
	public Entity(Pixmap pix, Array<Collider> colliders, float x, float y, boolean physicsEnabled){
		if(physicsEnabled){
			initEntity(pix, colliders, x, y, physicsEnabled, 0, new Vector2(), new Vector2(), new Vector2());
		} else {
			initEntity(pix, colliders, x, y, physicsEnabled, 1, null,null, null);
		}
	}
	
	public Entity(String texturePath, Array<Collider> colliders, float x, float y, boolean physicsEnabled){
		if(physicsEnabled){
			initEntity(texturePath, colliders, x, y, physicsEnabled, 0, new Vector2(), new Vector2(), new Vector2());
		} else {
			initEntity(texturePath, colliders, x, y, physicsEnabled, 1, null,null, null);
		}
		
	}
	
	public Entity(Pixmap pix, Array<Collider> colliders, float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		initEntity(pix, colliders, x, y, true, mass, force, velocity, acceleration);
	}
	
	public Entity(boolean isTextured, Array<Collider> colliders, float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		initEntity(false, colliders, x, y, true, mass, force, velocity, acceleration);
	}
	
	public Entity(String texturePath, Array<Collider> colliders, float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		initEntity(texturePath, colliders, x, y, true, mass, force, velocity, acceleration);
	}
	
	private void initEntity(boolean isTextured, Array<Collider> colliders, float x, float y, boolean physicsEnabled, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		this.x = x;
		this.y = y;
		this.colliders = colliders;
		this.physicsEnabled = physicsEnabled;
		initSprite();
		if(physicsEnabled){
			initPhysics(mass, force, velocity, acceleration);
		}
	}
	
	private void initEntity(String texturePath, Array<Collider> colliders, float x, float y, boolean physicsEnabled, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		this.x = x;
		this.y = y;
		if(texturePath != null){
			initSprite(texturePath);
		} else {
			initSprite();
		}
		this.colliders = colliders;
		this.physicsEnabled = physicsEnabled;
		if(physicsEnabled){
			initPhysics(mass, force, velocity, acceleration);
		}
	}
	
	private void initEntity(Pixmap pix, Array<Collider> colliders, float x, float y, boolean physicsEnabled, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		this.x = x;
		this.y = y;
		if(pix != null){
			initSprite(pix);
		} else {
			initSprite();
		}
		
		this.colliders = colliders;
		this.physicsEnabled = physicsEnabled;
		if(physicsEnabled){
			initPhysics(mass, force, velocity, acceleration);
		}
	}
	
	
	private void initSprite(){
		textureType = -1;
		sprite = null;
	}
	
	private void initSprite(Pixmap pix){
		if(pix == null){
			initSprite();
		} else {
			sprite = null;
			this.pix = pix;
			textureType = 1;
			Texture texture = new Texture(pix);
			sprite = new Sprite(texture);
			sprite.setX(x);
			sprite.setY(y);
		}
		
	}
	
	private void initSprite(String texturePath){
		if(texturePath == null){
			initSprite();
		} else {
			sprite = null;
			textureType = 0;
			this.texturePath = texturePath;
			Texture texture = new Texture(texturePath);
			sprite = new Sprite(texture);
			sprite.setX(x);
			sprite.setY(y);
		}
		
	}
	
	private void initPhysics(int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		this.force = force;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;
	}
	
	public void update(){
	}
	
	public void dispose(){
		
	}
	
	public void stopEntity(){
		force.setZero();
		velocity.setZero();
		acceleration.setZero();
	}
	
	public void setX(float x){
		this.x = x;
		sprite.setX(x);
		for(int i = 0; i < colliders.size; i++){
			colliders.get(i).setX(x);
		}
	}
	
	public float getX(){
		return x;
	}
	
	public void setY(float y){
		this.y = y;
		sprite.setY(y);
		
		for(int i = 0; i < colliders.size; i++){
			colliders.get(i).setY(y);
		}
	}
	
	public float getY(){
		return y;
		
	}
	
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
		sprite.setX(x);
		sprite.setY(y);
		
		for(int i = 0; i < colliders.size; i++){
			colliders.get(i).setPosition(x, y);
		}
	}
	
	public boolean getPhysicsEnabled(){
		return physicsEnabled;
	}
	
	public void setPhysicsEnabled(boolean physicsEnabled) {
		this.physicsEnabled = physicsEnabled;
		
		if(physicsEnabled){
			initPhysics(1, new Vector2(), new Vector2(), new Vector2());
		}
	}
	
	public Array<Collider> getColliders() {
		return colliders;
	}

	public void setColliders(Array<Collider> colliders) {
		for(int i = 0; i < colliders.size; i++){
			colliders.get(i).setEntity(this);
		}
		this.colliders = colliders;
	}

	public void addCollider(Collider collider){
		collider.setEntity(this);
		colliders.add(collider);
		
		
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public void setSprite(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void addForce(Vector2 force){
		this.force.add(force);
	}
	
	public void nullifyForce(){
		force.setZero();
	}
	
	public Vector2 getForce(){
		return force;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Vector2 getAcceleration(){
		return acceleration;
	}
	
	public void setAcceleration(Vector2 acceleration){
		this.acceleration = acceleration;
	}
	
	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	
	public Entity clone(){
		Sprite spriteClone = new Sprite(sprite.getTexture());
		spriteClone.setX(x);
		spriteClone.setY(y);
		Entity entClone = null;
		entClone = new Entity();
		if(textureType == 0){
			entClone.setTexturePath(texturePath);
		} else if(textureType == 1){
			entClone.setPix(pix);			
		} else if(textureType == -1){
		}
		
		entClone.setPhysicsEnabled(physicsEnabled);
		if(physicsEnabled){
			entClone.setForce(new Vector2(force.x, force.y));
			entClone.setVelocity(new Vector2(velocity.x, velocity.y));
			entClone.setAcceleration(new Vector2(acceleration.x, acceleration.y));
			entClone.setMass(mass);
		}
		
		
		entClone.setPhysicsEnabled(physicsEnabled);
		
		
		return entClone;
	}

	public float getWidth() {
		width = sprite.getWidth();
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		height = sprite.getHeight();
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTexturePath() {
		return texturePath;
	}

	public void setTexturePath(String texturePath) {
		initSprite(texturePath);
	}

	public Pixmap getPix() {
		return pix;
	}

	public void setPix(Pixmap pix) {
		initSprite(pix);
	}

	public void setForce(Vector2 force) {
		this.force = force;
	}

	//id,type,textureType,texturePathfile,x,y,physicsEnabled,force.x,force.y,velocity.x,velocity.y,acceleration.x,acceleration.y,mass,colliders.get(0),...,colliders.get(size-1)
	//The textureType is 0 when the texture has a file path. It is 1 when the texture is automatically generated.
	public String getSaveData(){
		String data = id + "," + type + "," + textureType + "," + texturePath + "," + x + "," + y + "," + physicsEnabled + "," + force.x + "," + force.y + "," + velocity.x + "," + velocity.y + "," + acceleration.x + "," + acceleration.y + "," + mass + ",";
		
		for(int i = 0; i < colliders.size; i++){
			data += colliders.get(i).getSaveData();
		}
		
		return data;
	}
}
