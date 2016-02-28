package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Entity {
	private Texture texture;
	private Animation animation;
	
	private Sprite sprite;
	
	public float x;
	public float y;
	
	public Entity(){
		sprite = new Sprite();
	}
	
	public Entity(Sprite sprite){
		this.sprite = sprite;
	}
	
	public Entity(Sprite sprite, float x, float y){
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}
	
	public void update(){
	}
	
	public void dispose(){
		
	}
	
	public Sprite getSprite(){
		return sprite;
	}
}
