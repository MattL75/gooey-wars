package com.gooeywars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gooeywars.UI.GameUI;
import com.gooeywars.UI.MainMenuUI;
import com.gooeywars.components.Friction;
import com.gooeywars.components.Testing;
import com.gooeywars.entities.Entity;

public class GooeyWars extends Main{
	private GameBox game;
	private GameBox menu;
	
	private OrthographicCamera camera;
	
	@Override
	public void create() {
		MainMenuUI menuUI = new MainMenuUI();
		GameUI gameUI = new GameUI();
		
		menu = new GameBox(false, menuUI);
		game = new GameBox(true, gameUI);
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 400);
		
		batch = new SpriteBatch();
		
		game.addComponent(new Testing());
		game.addComponent(new Friction());
	}

	@Override
	public void render() {
		
		//System.out.println((Main.gameBoxes.size));
		camera.update();
		
		
		game.update();
		
	}

	@Override
	public void resize(int x, int y) {	
	}

	@Override
	public void dispose() {
	}

}
