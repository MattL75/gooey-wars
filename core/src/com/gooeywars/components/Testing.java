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
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Polygon;
import com.gooeywars.util.shape.Square;

public class Testing extends Component{
	private Sprite sprite;
	private Texture texture;
	private Entity ent;
	float mod = 0;
	
	private Array<Entity> entities;
	private GameBox game;
	
	@Override
	public void create() {
		Pixmap pix = new Pixmap(64,64, Format.RGBA8888);
		pix.setColor(1,0,0,1f);
		pix.fillCircle(32, 32, 32);
		
		ent = new Entity(pix, 10, 210);
	
		ent.setMass(2);
		ent.setPhysicsEnabled(true);
		
		Square poly = new Square(ent);
		
		
		Collider collider = new Collider(poly);
		collider.setDrawable(true);
		ent.addCollider(collider);
		
		
		entities = Main.gameBoxes.get(1).getEntities();
		
		game = Main.findGameBox("game");
		
		game.addEntity(ent);
		
		Pixmap pix2 = new Pixmap(64,64, Format.RGBA8888);
		pix.setColor(1,0,0,1f);
		pix.fillCircle(32, 32, 32);
		
		Entity collisionEnt = new Entity(pix2, 200, 200);
		collisionEnt.setMass(2);
		collisionEnt.setPhysicsEnabled(true);
		
		
		Square poly2 = new Square(collisionEnt);
		
		
		Collider coll2 = new Collider(poly2);
		coll2.setDrawable(true);
		collisionEnt.addCollider(coll2);
		
		game.addEntity(collisionEnt);
	}

	@Override
	public void update() {
		
		/*if(Gdx.input.isKeyPressed(Keys.D)){
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
		}*/
	}
}
