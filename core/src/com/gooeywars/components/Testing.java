package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Environment;
import com.gooeywars.entities.Geyser;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.game.GameBox;
import com.gooeywars.game.Main;

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
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				game.addEntity(new Goo(i*30,j*30,100));
			}
		}
		
		Geyser geyser = new Geyser(1000,1000);
		game.addEntity(geyser);
		
		Environment environment = new Environment(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),Color.LIGHT_GRAY);
		game.addEntity(environment);
	}

	@Override
	public void update() {
		
	}
}
