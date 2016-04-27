package com.gooeywars.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.Main;

public class Minimap extends Group {
	Vector2 position;
	
	public Minimap(float width, float height) {
		setWidth(width);
		setHeight(height);
		setX(0);
		setY(0);
		Image minMap = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/minimap_no_border.png")));
		addActor(minMap);
	}
	
	public Minimap(float width, float height, Vector2 position) {
		setWidth(width);
		setHeight(height);
		setX(position.x);
		setY(position.y);
		Image minMap = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/minimap_no_border.png")));
		addActor(minMap);
	}
	
	//TODO THIS IS BASED ON A WORLD SIZE OF 1920x1080! MUST BASE ON WORLD SIZE!
	public void update() {
		if (Main.findGameBox("game").getEntities() == null) {
			return;
		}
		clearMap();
		Array<Entity> ent = Main.findGameBox("game").getEntities();
		for (int i = 0; i < ent.size; i++) {
			Entity tempEnt = ent.get(i);
			if (tempEnt.getType() == Entity.GOO) {
				Image temp = new Image();
				Sprite sprite = new Sprite(ent.get(i).getSprite());
				temp.setDrawable(new SpriteDrawable(sprite));
				temp.setX(tempEnt.getX() / (Gdx.graphics.getWidth() / getWidth()));
				temp.setY(tempEnt.getY() / (Gdx.graphics.getHeight() / getHeight()));
				temp.setWidth(tempEnt.getWidth() / (Gdx.graphics.getWidth() / getWidth()));
				temp.setHeight(tempEnt.getHeight() / (Gdx.graphics.getHeight() / getHeight()));
				addActor(temp);
			} else if (tempEnt.getType() == Entity.ENVIRONMENT) {
				for (int j = 0; j < tempEnt.getChildren().size; j++) {
					if (tempEnt.getChildren().get(j).getType() == Entity.OBSTACLE) {
						Image temp = new Image();
						Sprite sprite = new Sprite(tempEnt.getChildren().get(j).getSprite());
						temp.setDrawable(new SpriteDrawable(sprite));
						temp.setX(tempEnt.getChildren().get(j).getX() / (Gdx.graphics.getWidth() / getWidth()));
						temp.setY(tempEnt.getChildren().get(j).getY() / (Gdx.graphics.getHeight() / getHeight()));
						temp.setWidth(tempEnt.getChildren().get(j).getWidth() / (Gdx.graphics.getWidth() / getWidth()));
						temp.setHeight(tempEnt.getChildren().get(j).getHeight() / (Gdx.graphics.getHeight() / getHeight()));
						addActor(temp);
					}
				}
			} else if (tempEnt.getType() == Entity.GEYSER) {
				Image temp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/geyser_x.png")));
				temp.setX(tempEnt.getX() / (Gdx.graphics.getWidth() / getWidth()));
				temp.setY(tempEnt.getY() / (Gdx.graphics.getHeight() / getHeight()));
				addActor(temp);
			}
		}
	}
	
	public void clearMap() {
		clear();
		Image minMap = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/minimap_no_border.png")));
		addActor(minMap);
	}
}
