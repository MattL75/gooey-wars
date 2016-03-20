package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.physics.Collider;
import com.sun.java_cup.internal.runtime.Symbol;

public class Entity {
	protected int type;
	protected static final int ENTITY = 0;
	protected static final int GOO = 1;
	protected static final int GEYSER = 2;
	protected static final int ENVIRONMENT = 3;
	protected static final int OBSTACLE = 4;
	
	private Sprite sprite;
	
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
		initEntity(new Sprite(), new Array<Collider>(), 0, 0, false, 0, null, null, null);
	}
	
	public Entity(Entity ent){
		initEntity(new Sprite(ent.sprite), new Array<Collider>(ent.getColliders()), ent.getX(), ent.getY(), ent.getPhysicsEnabled(), ent.getMass(), new Vector2(ent.getForce()), new Vector2(ent.getVelocity()), new Vector2(ent.getAcceleration()));
	}
	
	public Entity(Sprite sprite){
		initEntity(sprite, new Array<Collider>(), 0, 0, false, 0, null, null, null);
	}
	
	public Entity(Sprite sprite, float x, float y){
		initEntity(sprite, new Array<Collider>(), x, y, false, 0, null, null, null);
	}
	
	public Entity(Sprite sprite, Array<Collider> colliders){
		initEntity(sprite, colliders, 0, 0, false, 0, null, null, null);
	}
	
	public Entity(Sprite sprite, Array<Collider> colliders, float x, float y){
		initEntity(sprite, colliders, x, y, false, 0, null, null, null);
	}
	
	public Entity(Sprite sprite, Array<Collider> colliders, float x, float y, boolean physicsEnabled){
		if(physicsEnabled){
			initEntity(sprite, colliders, x, y, physicsEnabled, 0, new Vector2(), new Vector2(), new Vector2());
		} else {
			initEntity(sprite, colliders, x, y, physicsEnabled, 1, null,null, null);
		}
	}
	
	public Entity(Sprite sprite, Array<Collider> colliders, float x, float y, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		initEntity(sprite, colliders, x, y, true, mass, force, velocity, acceleration);
	}
	
	private void initEntity(Sprite sprite, Array<Collider> colliders, float x, float y, boolean physicsEnabled, int mass, Vector2 force, Vector2 velocity, Vector2 acceleration){
		this.x = x;
		this.y = y;
		this.colliders = colliders;
		this.physicsEnabled = physicsEnabled;
		initSprite(sprite);
		if(physicsEnabled){
			initPhysics(mass, force, velocity, acceleration);
		}
	}
	
	private void initSprite(Sprite sprite){
		this.sprite = sprite;
		sprite.setX(x);
		sprite.setY(y);
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
		Entity entClone = new Entity(this);
		
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

	public void setForce(Vector2 force) {
		this.force = force;
	}

	//id,type,textureType,texturePathfile,x,y,physicsEnabled,force.x,force.y,velocity.x,velocity.y,acceleration.x,acceleration.y,mass,colliders.get(0),...,colliders.get(size-1)
	//The textureType is 0 when the texture has a file path. It is 1 when the texture is automatically generated.
	public String getSaveData(){
		String data = id + "," + type + "," + x + "," + y + "," + physicsEnabled + "," + force.x + "," + force.y + "," + velocity.x + "," + velocity.y + "," + acceleration.x + "," + acceleration.y + "," + mass + ",";
		
		for(int i = 0; i < colliders.size; i++){
			data += colliders.get(i).getSaveData();
		}
		
		return data;
	}
}
