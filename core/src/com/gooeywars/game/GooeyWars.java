package com.gooeywars.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gooeywars.UI.GameUI;
import com.gooeywars.UI.MainMenuUI;
import com.gooeywars.components.Friction;
import com.gooeywars.components.Testing;

public class GooeyWars extends Main{
	private GameBox game;
	private GameBox menu;
	private GameBox currentBox;
	
	private OrthographicCamera camera;
	
	@Override
	public void create() {
		MainMenuUI menuUI = new MainMenuUI();
		GameUI gameUI = new GameUI();
		
		menu = new GameBox(false, menuUI);
		menu.setTag("menu");
		game = new GameBox(true, gameUI);
		game.setTag("game");
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 400);
		
		batch = new SpriteBatch();
		
		game.addComponent(new Testing());
		game.addComponent(new Friction());
		currentBox = game;
	}

	@Override
	public void render() {
		
		camera.update();
		
		
		currentBox.update();
		
	}

	@Override
	public void resize(int x, int y) {	
	}

	@Override
	public void dispose() {
	}

	public GameBox getCurrentBox() {
		return currentBox;
	}

	public void setCurrentBox(GameBox currentBox) {
		this.currentBox = currentBox;
	}

	public GameBox getGame() {
		return game;
	}

	public void setGame(GameBox game) {
		this.game = game;
	}

	public GameBox getMenu() {
		return menu;
	}

	public void setMenu(GameBox menu) {
		this.menu = menu;
	}
	
	
	
	
}
