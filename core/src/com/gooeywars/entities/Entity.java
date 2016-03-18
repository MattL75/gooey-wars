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
		type = 0;
		sprite = new Sprite();
		colliders = new Array<Collider>();
	}
	
	public Entity(Sprite sprite, String texturePath){
		this.texturePath = texturePath;
		type = 0;
		this.sprite = sprite;
		colliders = new Array<Collider>();
	}
	
	public Entity(Sprite sprite, String texturePath, float x, float y){
		this.texturePath = texturePath;
		type = 0;
		this.sprite = sprite;
		sprite.setX(x);
		sprite.setY(y);
		this.x = x;
		this.y = y;
		colliders = new Array<Collider>();
	}
	
	public Entity(){
		initSprite();
		initEntity(null,0,0);
	}
	
	public Entity(Pixmap pix){
		initSprite(pix);
		initEntity(null,0,0);
	}
	
	public Entity(String texturePath){
		initSprite(texturePath);		
		initEntity(null,0,0);
	}
	
	public Entity(Pixmap pix, Array<Collider> colliders){
		initSprite(pix);
		initEntity(colliders, 0, 0);
	}
	
	public Entity(String texturePath, Array<Collider> colliders){
		initSprite(texturePath);
		initEntity(colliders, 0, 0);
	}
	
	public Entity(Pixmap pix, Array<Collider> colliders, float x, float y){
		initSprite(pix);
		initEntity(colliders, x, y);
	}
	
	public Entity(String texturePath, Array<Collider> colliders, float x, float y){
		initSprite(texturePath);
		initEntity(colliders, x, y);
	}
	
	public Entity(String texturePath, Array<Collider> colliders, float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		initSprite(texturePath);
		initEntity(colliders, x, y);
		
		physicsEnabled = true;
		initPhysics(mass, force, velocity, acceleration);
	}
	
	private void initSprite(){
		sprite = null;
	}
	
	private void initSprite(Pixmap pix){
		textureType = 1;
		Texture texture = new Texture(pix);
		sprite = new Sprite(texture);
	}
	
	private void initSprite(String texturePath){
		textureType = 0;
		this.texturePath = texturePath;
		Texture texture = new Texture(texturePath);
		sprite = new Sprite(texture);
	}
	
	private void initEntity(Array<Collider> colliders, float x, float y){
		this.x = x;
		this.y = y;
		sprite.setX(x);
		sprite.setY(y);
	}
	
	private void initPhysics(){
		force = new Vector2();
		velocity = new Vector2();
		acceleration = new Vector2();
		mass = 1;
	}
	
	private void initPhysics(int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		
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
			initPhysics();
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
		Entity entClone = new Entity(spriteClone,texturePath, x, y);
		
		entClone.setPhysicsEnabled(physicsEnabled);
		entClone.setColliders(new Array<Collider>(colliders));
		
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
