package com.gooeywars.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameUI implements Screen {
	Skin skin;
	static Stage stage;
	SpriteBatch batch;
	
	public GameUI() {
		stage = new Stage();
		create();
	}
	
	public GameUI(Viewport vp) {
		stage = new Stage();
		stage.setViewport(vp);
		create();
	}
	
	public void create() {
		batch = new SpriteBatch();
		//Gdx.input.setInputProcessor(stage);
		
		skin = new Skin();
		
		//Generate texture
		Pixmap pixmap = new Pixmap(1,1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		
		//Font setting
		skin.add("font", new BitmapFont());
		
		//Text button style settings
		TextButtonStyle txtStyle = new TextButtonStyle();
		txtStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		txtStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		txtStyle.checked = skin.newDrawable("white", Color.BLUE);
		txtStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		txtStyle.font = skin.getFont("font");
		skin.add("style", txtStyle);
		
		//Table creation
		Table table = new Table();
		table.setFillParent(true);
		table.bottom();
		table.left();
		stage.addActor(table);
		
		//Button creation
		final TextButton btCheck = new TextButton("Check", skin, "style");
		table.add(btCheck);
		
		table.add(new Image(skin.newDrawable("white", Color.RED))).prefWidth(250).prefHeight(200);
		table.add(new Image(skin.newDrawable("white", Color.RED))).size(100);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
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
	}

	public static void setFocus() {
		Gdx.input.setInputProcessor(stage);
	}
}
