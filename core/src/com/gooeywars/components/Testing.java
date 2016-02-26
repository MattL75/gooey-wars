package com.gooeywars.components;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;

public class Testing extends Component{
	private Sprite sprite;
	private Entity ent;
	@Override
	public void create() {
		Pixmap pix = new Pixmap(64,64, Format.RGBA8888);
		pix.setColor(0,1,0,1f);
		pix.fillCircle(32, 32, 32);
		
		Texture texture = new Texture(pix);
		
		sprite = new Sprite(texture);
		pix.dispose();
		
		ent = new Entity(sprite, 30, 30);
		Main.gameBoxes.get(0).addEntity(ent);
	}

	@Override
	public void update() {
		ent.x += 1;
		ent.y += 1;
	}

}
