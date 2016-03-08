package com.gooeywars.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.game.Main;
import com.gooeywars.physics.Collider;

public class Entity {
	private Sprite sprite;
	
	private float x;
	private float y;
	private float width;
	private float height;
	
	private boolean physicsEnabled;
	
	private Vector2 force;
	private Vector2 velocity;
	private Vector2 acceleration;
	private Array<Collider> colliders;
	private int mass;
	
	private int id;
	
	public Entity(){
		sprite = new Sprite();
		colliders = new Array<Collider>();
	}
	
	public Entity(Sprite sprite){
		this.sprite = sprite;
		colliders = new Array<Collider>();
	}
	
	public Entity(Sprite sprite, float x, float y){
		this.sprite = sprite;
		sprite.setX(x);
		sprite.setY(y);
		this.x = x;
		this.y = y;
		colliders = new Array<Collider>();
	}
	
	private void initPhysics(){
		force = new Vector2();
		velocity = new Vector2();
		acceleration = new Vector2();
		mass = 1;
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
		this.colliders = colliders;
	}

	public void addCollider(Collider collider){
		collider.setEntity(this);
		colliders.add(collider);
		
		
	}
	
	public Sprite getSprite(){
		return sprite;
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
		Entity entClone = new Entity(spriteClone, x, y);
		
		entClone.setPhysicsEnabled(physicsEnabled);
		entClone.setColliders(new Array<Collider>(colliders));
		
		return entClone;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	
}
