package com.gooeywars.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gooeywars.game.GooeyWars;
import com.gooeywars.gameState.GameState;

public class MainMenuUI implements Screen {

	Stage stage;
	Skin skin;
	SpriteBatch batch;
	
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

		skin = new Skin();
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(400, 200, Format.RGBA8888);
		pixmap.setColor(Color.PURPLE);
		pixmap.fill();

		skin.add("white", new Texture(pixmap));

		//Store the default libgdx font under the name "default"
		BitmapFont bfont = new BitmapFont();
		skin.add("default", bfont);
		bfont.getData().setScale(3);

		//Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		//When finger is up, AKA not on the button
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		//When finger is down AKA is clicking
		textButtonStyle.down = skin.newDrawable("white", Color.WHITE);
		//Has been clicked (can be switched off by clicking again)
		textButtonStyle.checked = skin.newDrawable("white", Color.BLACK);
		//Hovering over
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

		textButtonStyle.font = skin.getFont("default");

		skin.add("default", textButtonStyle);

		final TextButton textButton = new TextButton("START", textButtonStyle);
		textButton.setPosition(100, 200);
		stage.addActor(textButton);
		
		textButton.addListener(new ChangeListener() {
			
			public void changed (ChangeEvent event, Actor actor) {
				//System.out.println("Clicked! Is checked: " + textButton.isChecked());
				//textButton.setText("Starting new game");
				GooeyWars.setCurrentBox(GooeyWars.getGame());
				//GameState state = new GameState("save1.txt");
				//state.save();
				GameUI.setFocus();
			}
		});
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	
	public void setFocus() {
		Gdx.input.setInputProcessor(stage);
	}
}
