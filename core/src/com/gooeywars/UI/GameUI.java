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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gooeywars.game.GooeyWars;
import com.gooeywars.game.Main;

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
		
		//Minimap
		Image minMap = new Image(skin.newDrawable("white", Color.WHITE));
		minMap.setWidth(Gdx.graphics.getWidth() / 4);
		minMap.setHeight(Gdx.graphics.getHeight() / 4);
		minMap.setAlign(Align.bottomLeft);
		group.addActor(minMap);
		
		/*/Bottom right
		Image botBar = new Image(skin.newDrawable("white", Color.WHITE));
		botBar.setWidth(Gdx.graphics.getWidth() - minMap.getImageWidth());
		botBar.setHeight(60);
		botBar.setAlign(Align.bottomLeft);
		group.addActor(botBar);/*/
		
		//Bottom right bar and buttons
		VerticalGroup vert = new VerticalGroup();
		HorizontalGroup botButtons = new HorizontalGroup();
		HorizontalGroup botBar = new HorizontalGroup();
		
		float size = 40.0f;
		//MergeButton
		Button btMerge = new Button(skin.newDrawable("white", Color.WHITE));
		Image mergeUp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/merge_button_regular.png")));
		Image mergeHover = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/merge_button_hover.png")));
		Image mergeDown = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/merge_button_click.png")));
		mergeUp.setSize(size, size);
		mergeHover.setSize(size, size);
		mergeDown.setSize(size, size);
		
		ButtonStyle mergeStyle = new ButtonStyle();
		mergeStyle.over = mergeHover.getDrawable();
		mergeStyle.up = mergeUp.getDrawable();
		mergeStyle.down = mergeDown.getDrawable();
		btMerge.setStyle(mergeStyle);
		botButtons.addActor(btMerge);
		
		//SplitButton
		Button btSplit = new Button(skin.newDrawable("white", Color.WHITE));
		Image splitUp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/split_button_regular.png")));
		Image splitHover = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/split_button_hover.png")));
		Image splitDown = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/split_button_click.png")));
		splitUp.setSize(size, size);
		splitHover.setSize(size, size);
		splitDown.setSize(size, size);
		
		ButtonStyle splitStyle = new ButtonStyle();
		splitStyle.over = splitHover.getDrawable();
		splitStyle.up = splitUp.getDrawable();
		splitStyle.down = splitDown.getDrawable();
		btSplit.setStyle(splitStyle);
		botButtons.addActor(btSplit);
		
		//AttackButton
		Button btAttack = new Button(skin.newDrawable("white", Color.WHITE));
		Image attackUp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/attack_button_regular.png")));
		Image attackHover = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/attack_button_hover.png")));
		Image attackDown = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/attack_button_click.png")));
		attackUp.setSize(size, size);
		attackHover.setSize(size, size);
		attackDown.setSize(size, size);
		
		ButtonStyle attackStyle = new ButtonStyle();
		attackStyle.over = attackHover.getDrawable();
		attackStyle.up = attackUp.getDrawable();
		attackStyle.down = attackDown.getDrawable();
		btAttack.setStyle(attackStyle);
		botButtons.addActor(btAttack);
		
		//BuildButton
		Button btBuild = new Button(skin.newDrawable("white", Color.WHITE));
		Image buildUp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/build_button_regular.png")));
		Image buildHover = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/build_button_hover.png")));
		Image buildDown = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/build_button_click.png")));
		buildUp.setSize(size, size);
		buildHover.setSize(size, size);
		buildDown.setSize(size, size);
		
		ButtonStyle buildStyle = new ButtonStyle();
		buildStyle.over = buildHover.getDrawable();
		buildStyle.up = buildUp.getDrawable();
		buildStyle.down = buildDown.getDrawable();
		btBuild.setStyle(buildStyle);
		botButtons.addActor(btBuild);
		
		botButtons.setX(400);
		botButtons.setY(300);
		stage.addActor(botButtons);
		
		table.add(group).expand().fill();
		table.add(btMerge).width(80).height(80).align(Align.bottomRight);
		table.add(btSplit).width(80).height(80).align(Align.bottomRight);
		table.add(btAttack).width(80).height(80).align(Align.bottomRight);
		table.add(btBuild).width(80).height(80).align(Align.bottomRight);

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
        	Main.findGameBox("game").getCamera().position.x = 0 + Gdx.graphics.getWidth() / 2;
        	Main.findGameBox("game").getCamera().position.y = 0 + Gdx.graphics.getHeight() / 2;
        	Main.findGameBox("game").getCamera().position.z = 0;
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
