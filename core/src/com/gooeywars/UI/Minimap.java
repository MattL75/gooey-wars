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
	Array<Entity> ent;
	Image minMap = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/minimap_no_border.png")));
	int numGeyser;
	boolean firstPass = false;
	Vector2 worldSize; //REPLACE ALL REFERENCES TO Gdx.graphics WITH THIS
	
	public Minimap(float width, float height) {
		setWidth(width);
		setHeight(height);
		addActor(minMap);
	}
	
	//TODO THIS IS BASED ON A WORLD SIZE OF 1920x1080! MUST BASE ON WORLD SIZE!
	public void update() {
		if (Main.findGameBox("game").getEntities() == null) {
			return;
		}
		clearMap();
		ent = Main.findGameBox("game").getEntities();
		
		if (!firstPass) {
			for (int i = 0; i < ent.size; i++) {
				if (ent.get(i).getType() == Entity.GEYSER) {
					Image temp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/geyser_x.png")));
					temp.setX(ent.get(i).getX() / (Gdx.graphics.getWidth() / getWidth()));
					temp.setY(ent.get(i).getY() / (Gdx.graphics.getHeight() / getHeight()));
					numGeyser++;
					addActor(temp);
				}
			}
			firstPass = true;
		}
		
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
			} 
		}
	}
	
	public void clearMap() {
		for (int i = numGeyser + 1; i < getChildren().size; i++) {
			getChildren().removeIndex(i);
		}
	}
}
