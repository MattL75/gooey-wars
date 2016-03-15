package com.gooeywars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gooeywars.UI.GameUI;
import com.gooeywars.UI.MainMenuUI;
import com.gooeywars.components.Friction;
import com.gooeywars.components.Testing;


public class GooeyWars extends Main{
	private static GameBox game;
	private static GameBox menu;
	private static GameBox currentBox;
	
	private OrthographicCamera camera;
	
	private Viewport viewport;
	
	public static boolean isFullScreen = false;
	
	
	@Override
	public void create() {
		DisplayMode displayMode = Gdx.graphics.getDisplayMode();

		if(isFullScreen){
			Gdx.graphics.setFullscreenMode(displayMode);
		}
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new ScreenViewport(camera);
		
		MainMenuUI menuUI = new MainMenuUI(viewport);
		GameUI gameUI = new GameUI(viewport);
		
		menu = new GameBox(false, menuUI);
		menu.setTag("menu");
		game = new GameBox(true, gameUI);
		game.setTag("game");
		
		
		
		batch = new SpriteBatch();
		
		game.addComponent(new Testing());
		game.addComponent(new Friction());
		currentBox = menu;
	}

	@Override
	public void render() {
		
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		currentBox.update();
		
	}

	@Override
	public void resize(int x, int y) {	
		if(fullScreen){
			//Gdx.graphics.setFullscreenMode(new DisplayMode)
		}
		//Gdx.graphics.
		//viewport.update(x,y);
		if(!isFullScreen){
			Gdx.graphics.setWindowedMode(x, y);
		} else {
		}
		
		viewport.update(x,y);
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
