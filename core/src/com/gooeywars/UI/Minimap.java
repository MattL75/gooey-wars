package com.gooeywars.UI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Main;

public class Minimap extends Group {
	Vector2 position;
	Array<Entity> ent;
	
	public Minimap(float width, float height) {
		setWidth(width);
		setHeight(height);
		setX(0);
		setY(0);
		update();	
	}
	
	public Minimap(float width, float height, Vector2 position) {
		setWidth(width);
		setHeight(height);
		setX(position.x);
		setY(position.y);
		update();
	}
	
	public void update() {
		ent = Main.findGameBox("game").getEntities();
		for (int i = 0; i < ent.size; i++) {
			Entity tempEnt = ent.get(i);
			if (tempEnt.getType() == Entity.GOO) {
				Image temp = new Image();
				temp.setDrawable((Drawable)ent.get(i).getSprite());
				temp.setX(tempEnt.getX());
				temp.setY(tempEnt.getY());
				temp.setWidth(tempEnt.getWidth());
				temp.setHeight(tempEnt.getHeight());
			}
		}
	}
}
