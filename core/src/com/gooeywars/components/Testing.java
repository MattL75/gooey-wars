package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Component;
import com.gooeywars.game.GameBox;
import com.gooeywars.game.Main;

public class Testing extends Component{
	private Sprite sprite;
	private Texture texture;
	private Entity ent;
	float mod = 0;
	
	private Array<Entity> entities;
	private GameBox game;
	
	@Override
	public void create() {
		Pixmap pix = new Pixmap(65,65, Format.RGBA8888);
		pix.setColor(1,0,0,1f);
		pix.fillCircle(32, 32, 32);
		
		texture = new Texture(pix);
		
		sprite = new Sprite(texture);
		pix.dispose();
		
		ent = new Entity(sprite, 30, 30);
		ent.setMass(1);
		ent.setPhysicsEnabled(true);
		
		entities = Main.gameBoxes.get(1).getEntities();
		
		game = Main.findGameBox("game");
		
		game.addEntity(ent);
	}

	@Override
	public void update() {
		if(Gdx.input.isKeyPressed(Keys.D)){
			entities.get(0).addForce(new Vector2(1000, 0));
		}
		
		if(Gdx.input.isKeyPressed(Keys.A)){
			entities.get(0).addForce(new Vector2(-1000, 0));
		}
		
		if(Gdx.input.isKeyPressed(Keys.W)){
			entities.get(0).addForce(new Vector2(0, 1000));
		}
		
		if(Gdx.input.isKeyPressed(Keys.S)){
			entities.get(0).addForce(new Vector2(0, -1000));
		}
		
		if(Gdx.input.isKeyPressed(Keys.K)){
			entities.get(0).setPosition(100, 100);
		}
	}
}
