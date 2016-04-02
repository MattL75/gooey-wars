package com.gooeywars.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gooeywars.game.GooeyWars;
import com.gooeywars.gameState.GameState;

public class MainMenuUI implements Screen {

	static Stage stage;
	Skin skin;
	SpriteBatch batch = new SpriteBatch();
	BitmapFont bfont;
	
	public MainMenuUI() {
		GooeyWars.setCurrentBox(GooeyWars.getMenu());
		stage = new Stage();
		create();
	}
	
	public MainMenuUI(Viewport vp) {
		GooeyWars.setCurrentBox(GooeyWars.getMenu());
		stage = new Stage();
		stage.setViewport(vp);
		create();
	}
	
	public void create() {
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(stage);

		//Background
		Image background = new Image(new Texture(Gdx.files.local("assets/textures/interface/menu/bg_w_logo.jpg")));
		background.setAlign(2);
		stage.addActor(background);
		
		/*/Logo processing (NOW INCLUDED IN BACKGROUND)
		float scale = 0.8f;
		Image logoImage = new Image(new Texture(Gdx.files.local("assets/textures/interface/menu/main_logo.png")));
		logoImage.setScale(scale);
		logoImage.setPosition((Gdx.graphics.getWidth() - (logoImage.getWidth() * scale)) / 2.0f, (Gdx.graphics.getHeight() - (logoImage.getHeight() * scale)) / 2.0f + Gdx.graphics.getWidth() / 6);
		stage.addActor(logoImage);/*/
		
		skin = new Skin();
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(250, 70, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		skin.add("white", new Texture(pixmap));
		pixmap.dispose();
		
		//Store the default libgdx font under the name "default"
		bfont = new BitmapFont();
		skin.add("default", bfont);
		bfont.getData().setScale(2);

		//Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.GRAY); 			//When finger is up, AKA not on the button
		textButtonStyle.down = skin.newDrawable("white", Color.WHITE);			//When finger is down AKA is clicking
		textButtonStyle.checked = skin.newDrawable("white", Color.BLACK);		//Has been clicked (can be switched off by clicking again)
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);		//Hovering over

		textButtonStyle.font = skin.getFont("default");

		skin.add("default", textButtonStyle);

		float padding = 50.0f;
		
		//Config for start button
		final TextButton startButton = new TextButton("START", textButtonStyle);
		startButton.setPosition((Gdx.graphics.getWidth() - startButton.getWidth()) / 2.0f, (Gdx.graphics.getHeight() - startButton.getHeight()) / 2.0f + padding * 3.0f);
		stage.addActor(startButton);
		
		//Config for toggle full screen button
		final TextButton fullScreenButton = new TextButton("FULL SCREEN", textButtonStyle);
		fullScreenButton.setPosition((Gdx.graphics.getWidth() - fullScreenButton.getWidth()) / 2.0f, (Gdx.graphics.getHeight() - fullScreenButton.getHeight()) / 2.0f + padding * 1.0f);
		fullScreenButton.setProgrammaticChangeEvents(false);
		stage.addActor(fullScreenButton);
		
		//Config for GameState testing button
		final TextButton gameStateButton = new TextButton("GAME STATE", textButtonStyle);
		gameStateButton.setPosition((Gdx.graphics.getWidth() - gameStateButton.getWidth()) / 2.0f, (Gdx.graphics.getHeight() - gameStateButton.getHeight()) / 2.0f - padding * 1.0f);
		gameStateButton.setProgrammaticChangeEvents(false);
		stage.addActor(gameStateButton);
		
		//Config for exit button
		final TextButton exitButton = new TextButton("EXIT", textButtonStyle);
		exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth()) / 2.0f, (Gdx.graphics.getHeight() - exitButton.getHeight()) / 2.0f - padding * 3.0f);
		exitButton.setProgrammaticChangeEvents(false);
		stage.addActor(exitButton);
		
		//Event for start button
		startButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				GooeyWars.setCurrentBox(GooeyWars.getGame());
				resetButtons();
				GameUI.setFocus();
			}
		});
		
		//Event for toggle full screen
		fullScreenButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if (GooeyWars.getIsFullScreen()) {
					GooeyWars.setFullScreen(false);
				} else {
					GooeyWars.setFullScreen(true);
				}
				fullScreenButton.setChecked(false);
			}
		});
		
		//Event for game state debug
		gameStateButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				GameState state = new GameState("save1.txt");
				state.load();
				gameStateButton.setChecked(false);
			}
		});
		
		//Event for exit button
		exitButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
	}
	
	//Method resets the buttons (i.e. not checked)
	public void resetButtons() {
		Array<Actor> ar = stage.getActors();
		for (int i = 0; i < ar.size; i++) {
			if (ar.get(i) instanceof TextButton) {
				TextButton txt = (TextButton) ar.get(i);
				txt.setChecked(false);
			}
		}
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		bfont.dispose();
	}
	
	public static void setFocus() {
		Gdx.input.setInputProcessor(stage);
	}
}
