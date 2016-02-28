package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;

public class Testing extends Component{
	private Sprite sprite;
	private Entity ent;
	float mod = 0;
	@Override
	public void create() {
		Pixmap pix = new Pixmap(65,65, Format.RGBA8888);
		pix.setColor(1,0,0,1f);
		pix.fillCircle(32, 32, 32);
		
		Texture texture = new Texture(pix);
		
		sprite = new Sprite(texture);
		pix.dispose();
		
		ent = new Entity(sprite, 30, 30);
		Main.gameBoxes.get(1).addEntity(ent);
	}

	@Override
	public void update() {
		if(Gdx.input.isKeyPressed(Keys.D))
			ent.x+= 2;	
		if(Gdx.input.isKeyPressed(Keys.A))
			ent.x-= 2;
		if(Gdx.input.isKeyPressed(Keys.W))
			ent.y+= 2;
		if(Gdx.input.isKeyPressed(Keys.S))
			ent.y-= 2;
		if(Gdx.input.isKeyPressed(Keys.K)){
			Main.gameBoxes.get(1).addEntity(new Entity(sprite, (float)(Math.random() * 300),(float)(Math.random() * 300)));
		}
		
	}

}
