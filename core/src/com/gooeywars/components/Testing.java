package com.gooeywars.components;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.game.GameBox;
import com.gooeywars.game.Main;
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Square;

public class Testing extends Component{
	private Sprite sprite;
	private Texture texture;
	private Entity ent;
	private Entity ent2;
	float mod = 0;
	
	private Array<Entity> entities;
	private GameBox game;
	
	@Override
	public void create() {
entities = Main.gameBoxes.get(1).getEntities();
		
		game = Main.findGameBox("game");
		
	
		
		
		Pixmap pix = new Pixmap(64,64, Format.RGBA8888);
		pix.setColor(1,0,0,1f);
		pix.fillCircle(32, 32, 32);
		
		
		Texture texture = new Texture(pix);
		pix.dispose();
		Sprite sprite = new Sprite(texture);
		
		
		ent = new Entity();
		ent.setSprite(sprite);
		ent.setX(10);
		ent.setY(210);
		
		
		ent.setMass(2);
		ent.setPhysicsEnabled(true);
		
		Square poly = new Square(ent);
		
		
		Collider collider = new Collider(poly);
		collider.setDrawable(true);
		ent.addCollider(collider);
		
		game.addEntity(ent);
		
		
		
		Pixmap pix2 = new Pixmap(64,64, Format.RGBA8888);
		pix2.setColor(1,0,0,1f);
		pix2.fillCircle(32, 32, 32);
		
		Texture texture2 = new Texture(pix2);
		pix2.dispose();
		Sprite sprite2 = new Sprite(texture2);
		
		ent2 = new Entity(sprite2, 200, 200);
		ent2.setMass(2);
		ent2.setPhysicsEnabled(true);
		
		
		Square poly2 = new Square(ent2);
		
		
		Collider coll2 = new Collider(poly2);
		coll2.setDrawable(true);
		ent2.addCollider(coll2);
		
		game.addEntity(ent2);
		
		Goo goo = new Goo();
		
		game.addEntity(goo);
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
