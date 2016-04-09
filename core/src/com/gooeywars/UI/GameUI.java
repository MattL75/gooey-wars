package com.gooeywars.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gooeywars.game.GooeyWars;

public class GameUI implements Screen {
	Skin skin;
	static Stage stage;
	SpriteBatch batch;
	Table table;
	
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
		pixmap.dispose();
		//Font setting
		skin.add("font", new BitmapFont());
		
		//Table creation
		table = new Table();
		table.debug();
		table.setFillParent(true);
		table.bottom();
		table.left();
		stage.addActor(table);
		
		//Group stuff
		Group group = new Group();
		Image minMap = new Image(skin.newDrawable("white", Color.WHITE));
		minMap.setWidth(Gdx.graphics.getWidth() / 6);
		minMap.setHeight(Gdx.graphics.getHeight() / 5);
		minMap.setAlign(Align.bottomLeft);
		group.addActor(minMap);
		Image botBar = new Image(skin.newDrawable("white", Color.WHITE));
		botBar.setWidth(1000);
		botBar.setHeight(100);
		botBar.setAlign(Align.bottom);
		group.addActor(botBar);
		
		table.add(group);
		
		//table.add(new Image(skin.newDrawable("white", Color.WHITE))).prefWidth(Gdx.graphics.getWidth() / 6).prefHeight(Gdx.graphics.getHeight() / 5);
		//table.add(new Image(skin.newDrawable("white", Color.WHITE))).prefWidth(1000).prefHeight(100).align(Align.bottom);
		
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		
		//Event for escape key
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
        	GooeyWars.setCurrentBox(GooeyWars.getMenu());
        	MainMenuUI.setFocus();
        }
		
		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
		
		//TODO fix this table resizing thing
		table.setSize(width, height);
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
