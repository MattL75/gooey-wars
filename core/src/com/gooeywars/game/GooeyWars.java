package com.gooeywars.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gooeywars.UI.GameUI;
import com.gooeywars.UI.MainMenuUI;
import com.gooeywars.components.Friction;
import com.gooeywars.components.Testing;

public class GooeyWars extends Main{
	private static GameBox game;
	private static GameBox menu;
	private static GameBox currentBox;
	
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
		
		
<<<<<<< HEAD
		menu.update();
=======
		currentBox.update();
>>>>>>> a489186ed6e6700e7776c662370ef296d4061e3d
		
	}

	@Override
	public void resize(int x, int y) {	
	}

	@Override
	public void dispose() {
	}

	public static GameBox getCurrentBox() {
		return currentBox;
	}

	public static void setCurrentBox(GameBox currentBox1) {
		currentBox = currentBox1;
	}

	public static GameBox getGame() {
		return game;
	}

	public static void setGame(GameBox game1) {
		game = game1;
	}

	public static GameBox getMenu() {
		return menu;
	}

	public static void setMenu(GameBox menu1) {
		menu = menu1;
	}
	
	
	
	
}
