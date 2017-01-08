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
	Image minMapSquare = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/map_square.png")));
	int numGeyser;
	boolean firstPass = false;
	Vector2 worldSize;
	
	public Minimap(float width, float height) {
		setWidth(width);
		setHeight(height);
		addActor(minMap);
		addActor(minMapSquare);
	}
	
	public void update() {
		if (Main.findGameBox("game").getEntities() == null) {
			return;
		}
		clearMap();
		ent = Main.findGameBox("game").getEntities();
		this.worldSize = Main.findGameBox("game").size;
		
		//Stuff to execute on first pass
		if (!firstPass) {
			for (int i = 0; i < ent.size; i++) {
				if (ent.get(i).getType() == Entity.GEYSER) {
					Image temp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/geyser_x.png")));
					temp.setX(ent.get(i).getX() / (worldSize.x / getWidth()));
					temp.setY(ent.get(i).getY() / (worldSize.y / getHeight()));
					numGeyser++;
					addActor(temp);
				}
			}
			minMapSquare.setWidth(minMap.getWidth() / (worldSize.x / Gdx.graphics.getWidth()));
			minMapSquare.setHeight(minMap.getHeight() / (worldSize.y / Gdx.graphics.getHeight()));
			firstPass = true;
		}
		
		//Moving camera WITH BORDERS
		if (!((Main.findGameBox("game").getCamera().position.x) / (worldSize.x / getWidth()) + minMapSquare.getWidth() / 2 >= getWidth()) && !((Main.findGameBox("game").getCamera().position.x) / (worldSize.x / getWidth()) - minMapSquare.getWidth() / 2 <= minMap.getX())) {
			minMapSquare.setX((Main.findGameBox("game").getCamera().position.x) / (worldSize.x / getWidth()) - minMapSquare.getWidth() / 2);
		}
		if (!((Main.findGameBox("game").getCamera().position.y) / (worldSize.y / getHeight()) + minMapSquare.getHeight() / 2 >= getHeight()) && !((Main.findGameBox("game").getCamera().position.y) / (worldSize.y / getHeight()) - minMapSquare.getHeight() / 2 <= minMap.getY())) {
			minMapSquare.setY((Main.findGameBox("game").getCamera().position.y) / (worldSize.y / getHeight()) - minMapSquare.getHeight() / 2);
		}
		
		for (int i = 0; i < ent.size; i++) {
			Entity tempEnt = ent.get(i);
			if (tempEnt.getType() == Entity.GOO) {
				Image temp = new Image();
				Sprite sprite = new Sprite(ent.get(i).getSprite());
				temp.setDrawable(new SpriteDrawable(sprite));
				temp.setX(tempEnt.getX() / (worldSize.x / getWidth()));
				temp.setY(tempEnt.getY() / (worldSize.y / getHeight()));
				temp.setWidth(tempEnt.getWidth() / (worldSize.x / getWidth()));
				temp.setHeight(tempEnt.getHeight() / (worldSize.y / getHeight()));
				addActor(temp);
			} else if (tempEnt.getType() == Entity.ENVIRONMENT) {
				for (int j = 0; j < tempEnt.getChildren().size; j++) {
					if (tempEnt.getChildren().get(j).getType() == Entity.OBSTACLE) {
						Image temp = new Image();
						Sprite sprite = new Sprite(tempEnt.getChildren().get(j).getSprite());
						temp.setDrawable(new SpriteDrawable(sprite));
						temp.setX(tempEnt.getChildren().get(j).getX() / (worldSize.x / getWidth()));
						temp.setY(tempEnt.getChildren().get(j).getY() / (worldSize.y / getHeight()));
						temp.setWidth(tempEnt.getChildren().get(j).getWidth() / (worldSize.x / getWidth()));
						temp.setHeight(tempEnt.getChildren().get(j).getHeight() / (worldSize.y / getHeight()));
						addActor(temp);
					}
				}
			} 
		}
	}
	
	public void clearMap() {
		for (int i = numGeyser + 2; i < getChildren().size; i++) {
			getChildren().removeIndex(i);
		}
	}
}
