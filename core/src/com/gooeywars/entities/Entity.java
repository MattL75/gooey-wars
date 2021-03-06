package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.game.Main;
import com.gooeywars.physics.Collider;

public class Entity {
	private int type;
	public static final int ENTITY = 0;
	public static final int GOO = 1;
	public static final int GEYSER = 2;
	public static final int ENVIRONMENT = 3;
	public static final int OBSTACLE = 4;
	
	private float velocityFactor;
	
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
	private Array<Entity> children;
	
	public static int entityCount;
	
	private boolean isObstacle;
	
	public boolean isToBeDestroyed;
	
	public Entity(){
		initEntity(new Sprite(new Texture(new Pixmap(1,1,Format.RGBA8888))), new Array<Collider>(), 0, 0, false, 0, null, null, null);
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
		velocityFactor = 1f;
		id = entityCount++;
		this.x = x;
		this.y = y;
		this.colliders = colliders;
		this.physicsEnabled = physicsEnabled;
		children = new Array<Entity>();
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
	
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
	
	public void update(){
		checkDestroy();
	}
	
	public void checkDestroy(){
		if(isToBeDestroyed){
			Main.findGameBox("game").removeEntity(getId());
			getSprite().getTexture().dispose();
			clearColliders();
		}
	}
	
	public void dispose(){
		sprite.getTexture().dispose();
		for(int i = 0; i < colliders.size; i++){
			colliders.get(i).dispose();
			
		}
		colliders.clear();
	}
	
	public void stopEntity(){
		force.setZero();
		velocity.setZero();
		acceleration.setZero();
	}
	
	public Vector2 collide(Entity other){
		Vector2 displacement = new Vector2();
		Vector2 temp = new Vector2();
		Array<Collider> otherColls = other.getColliders();
		
		/*if(otherColls != null){
			if(otherColls.size > 0){*/
				for(int i = 0; i < colliders.size; i++){
					for(int j = 0; j < otherColls.size; j++){
						temp = colliders.get(i).collide(other.getColliders().get(j));
						if(temp.len2() > displacement.len2()){
							displacement = temp.cpy();
						}
					}
				}
			/*}
		}*/
		return displacement;
	}
	
	public void clearColliders(){
		for(int i = 0; i < colliders.size; i++){
			colliders.get(i).dispose();
		}
		colliders.clear();
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
		sprite.setX(x);
		sprite.setY(y);
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
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setForce(Vector2 force) {
		this.force = force;
	}
	
	public Array<Entity> getChildren() {
		return children;
	}

	public void setChildren(Array<Entity> children) {
		this.children = children;
	}
	
	public boolean isObstacle() {
		return isObstacle;
	}

	public void setIsObstacle(boolean isObstacle) {
		this.isObstacle = isObstacle;
	}

	public int getId() {
		return id;
	}

	
	
	public float getVelocityFactor() {
		return velocityFactor;
	}

	public void setVelocityFactor(float velocityFactor) {
		this.velocityFactor = velocityFactor;
	}

	//id,type,x,y,physicsEnabled,force.x,force.y,velocity.x,velocity.y,acceleration.x,acceleration.y,mass
	//The textureType is 0 when the texture has a file path. It is 1 when the texture is automatically generated.
	public String getSaveData(){
		String data = "";

		if(physicsEnabled){
			data = id + "," + type + "," + x + "," + y + "," + physicsEnabled + "," + force.x + "," + force.y + "," + velocity.x + "," + velocity.y + "," + acceleration.x + "," + acceleration.y + "," + mass;

		} else {
			data = id + "," + type + "," + x + "," + y + "," + physicsEnabled + "," + mass;

		}
		
		return data;
	}

	public void removeChild(int idChild) {
		children.get(idChild).getSprite().getTexture().dispose();
		children.removeIndex(idChild);
	}
	
	
}
