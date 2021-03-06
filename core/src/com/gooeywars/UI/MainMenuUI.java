package com.gooeywars.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gooeywars.game.GooeyWars;
import com.gooeywars.game.Main;
import com.gooeywars.gameState.GameState;

public class MainMenuUI implements Screen {

	static Stage stage;
	Skin skin;
	SpriteBatch batch = new SpriteBatch();
	BitmapFont bfont;
	Array<Image> clouds;
	Texture cloudTex = new Texture("assets/textures/interface/menu/cloud.png");
	Image background;
	TextButtonStyle textButtonStyle = new TextButtonStyle();
	TextFieldStyle textFieldStyle = new TextFieldStyle();
	boolean isAnim = false;
	int activeScreen = 0;
	
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
		activeScreen = 0;
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(stage);
		
		background = new Image(new Texture(Gdx.files.local("assets/textures/interface/menu/bg_w_logo.jpg")));
		//background.sizeBy(background.getWidth() - Gdx.graphics.getWidth(), background.getHeight() - Gdx.graphics.getHeight());
		background.setWidth(Gdx.graphics.getWidth());
		background.setHeight(Gdx.graphics.getHeight());
		background.setAlign(2);
		
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
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.GRAY); 			//When finger is up, AKA not on the button
		textButtonStyle.down = skin.newDrawable("white", Color.WHITE);			//When finger is down AKA is clicking
		textButtonStyle.checked = skin.newDrawable("white", Color.BLACK);		//Has been clicked (can be switched off by clicking again)
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);		//Hovering over
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = skin.getFont("default");
		textFieldStyle.fontColor = Color.BLACK;
		skin.add("default", textFieldStyle);
		
		//Initialize main menu
		mainScreen();
		
		if (isAnim) {
			cloudOverlay();
		}
	}
	
	public void cloudOverlay() {
		float scale = 0.5f;
		clouds = new Array<Image>();
		
		for (int i = 0; i < Gdx.graphics.getWidth() / (cloudTex.getWidth() * scale * scale); i++) {
			for (int j = 0; j < Gdx.graphics.getHeight() / (cloudTex.getHeight() * scale * scale); j++) {
				//Actor
				Image temp = new Image(cloudTex);
				temp.setScale(scale);
				if (i == 0) {
					temp.setX(-40);
				} else {
					temp.setX(i * temp.getWidth() * scale * scale);
				}
				temp.setY(j * temp.getHeight() * scale * scale);
				clouds.add(temp);
				stage.addActor(temp);
			}
		}
		
		Image image = new Image(new Texture("assets/textures/interface/menu/try.png"));
		image.setAlign(2);
		stage.addActor(image);
		
		//Wait for reading of message	
		MoveToAction imageDown = new MoveToAction();
		imageDown.setX(imageDown.getX());
		imageDown.setY(-700);
		imageDown.setDuration(2);
		image.addAction(imageDown);
		
		for (int i = 0; i < clouds.size; i++) {
			/*/if (clouds.get(i).getX() < (Gdx.graphics.getWidth() - clouds.get(i).getWidth()) / 2) {
				MoveToAction move = new MoveToAction();
				move.setX(-400);
				move.setY(clouds.get(i).getY());
				move.setDuration(3);
				clouds.get(i).addAction(move);
			} else {
				MoveToAction move = new MoveToAction();
				move.setX(Gdx.graphics.getWidth() + 300);
				move.setY(clouds.get(i).getY());
				move.setDuration(3);
				clouds.get(i).addAction(move);
			}/*/
			MoveToAction move = new MoveToAction();
			move.setX(clouds.get(i).getX());
			move.setY(-400);
			move.setDuration(3);
			clouds.get(i).addAction(move);
		}
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
	
	public void mainScreen() {
		stage.clear();
		stage.addActor(background);
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
		final TextButton gameStateButton = new TextButton("SAVE / LOAD", textButtonStyle);
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
		
		gameStateButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				saveLoad();
			}
		});
		
		/*/Event for game state debug
		gameStateButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				GameState state = new GameState("save1.txt");
				state.save();
				state.load();
				gameStateButton.setChecked(false);
			}
		});/*/
		
		//Event for exit button
		exitButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
	}
	
	public void saveLoad() {
		activeScreen = 1;
		stage.clear();
		stage.addActor(background);
		
		Table table = new Table();
		table.align(Align.center);
		
		float padding = 50.0f;
		
		final TextField saveField = new TextField("Enter a file", skin);
		saveField.setStyle(textFieldStyle);
		saveField.setPosition((Gdx.graphics.getWidth() - saveField.getWidth()) / 2.0f, (Gdx.graphics.getHeight() - saveField.getHeight()) / 2.0f + padding * 3.0f);
		final TextField loadField = new TextField("Enter a file", skin);
		saveField.setStyle(textFieldStyle);
		loadField.setPosition((Gdx.graphics.getWidth() - loadField.getWidth()) / 2.0f, (Gdx.graphics.getHeight() - loadField.getHeight()) / 2.0f - padding * 1.0f);
		
		final TextButton saveButton = new TextButton("SAVE", skin);
		saveButton.setPosition((Gdx.graphics.getWidth() - saveButton.getWidth()) / 2.0f, (Gdx.graphics.getHeight() - saveButton.getHeight()) / 2.0f + padding * 1.0f);
		saveButton.setStyle(textButtonStyle);
		saveButton.setProgrammaticChangeEvents(false);
		final TextButton loadButton = new TextButton("LOAD", skin);
		loadButton.setPosition((Gdx.graphics.getWidth() - loadButton.getWidth()) / 2.0f, (Gdx.graphics.getHeight() - loadButton.getHeight()) / 2.0f - padding * 3.0f);
		loadButton.setStyle(textButtonStyle);
		loadButton.setProgrammaticChangeEvents(false);
		
		final TextButton exitButton = new TextButton("BACK", textButtonStyle);
		exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth()) / 2.0f, (Gdx.graphics.getHeight() - exitButton.getHeight()) / 2.0f - padding * 7.0f);
		exitButton.setProgrammaticChangeEvents(false);
		stage.addActor(exitButton);
		
		stage.addActor(saveField);
		stage.addActor(saveButton);
		stage.addActor(loadField);
		stage.addActor(loadButton);
		
		saveButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if (!(saveField.getText().charAt(saveField.getText().length() - 1) == "t".charAt(0) && saveField.getText().charAt(saveField.getText().length() - 2) == "x".charAt(0) && saveField.getText().charAt(saveField.getText().length() - 3) == "t".charAt(0) && saveField.getText().charAt(saveField.getText().length() - 4) == ".".charAt(0))) {
					saveField.setText(saveField.getText() + ".txt");
				}
				GameState saveState = new GameState(saveField.getText());
				saveState.save();
				saveButton.setChecked(false);
			}
		});
		
		loadButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				try {
					GameState loadState = new GameState(loadField.getText());
					loadState.load();
				}
				catch (Exception ex) {
					System.out.println("Error. File loading failed.");
					return;
				} finally {
					loadButton.setChecked(false);
				}
			}
		});
		
		exitButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				mainScreen();
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
		if (activeScreen == 1) {
	        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
	        	mainScreen();
	        }
		}
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
		cloudTex.dispose();
	}
	
	public static void setFocus() {
		Gdx.input.setInputProcessor(stage);
	}
}
