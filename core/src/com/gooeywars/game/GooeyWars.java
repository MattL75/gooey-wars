package com.gooeywars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gooeywars.UI.GameUI;
import com.gooeywars.UI.MainMenuUI;
import com.gooeywars.components.Friction;
import com.gooeywars.components.GameKeyInput;
import com.gooeywars.components.Testing;


public class GooeyWars extends Main{
	private static GameBox game;
	private static GameBox menu;
	private static GameBox currentBox;
	
	private OrthographicCamera camera;
	
	private Viewport viewport;
	
	private static boolean isFullScreen = true;
	public static int resWidth = 600;
	public static int resHeight = 800;
	
	
	@Override
	public void create() {
		
		
		
		DisplayMode displayMode = Gdx.graphics.getDisplayMode();

		initializeScreen();
		
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
		
		game.addComponent(new GameKeyInput());
		game.addComponent(new Testing());
		game.addComponent(new Friction());
		currentBox = menu;
	}

	@Override
	public void render() {
		
		if(isFullScreen){
			viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		
		
		currentBox.update();
		
	}

	@Override

	public void resize(int x, int y) {	
		//camera.setToOrtho(false, x, y);
		viewport.update(x,y);
	}
	

	
	
	public static boolean getIsFullScreen(){
		return isFullScreen;
	}

	public static void setFullScreen(boolean fullScreenValue){
		isFullScreen = fullScreenValue;
		initializeScreen();

	}
	
	public static void initializeScreen(){
		if(isFullScreen){
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		} else {
			Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
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
