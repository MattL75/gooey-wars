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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.GooeyWars;
import com.gooeywars.game.Main;

public class GameUI implements Screen {
	Skin skin;
	static Stage stage;
	SpriteBatch batch;
	Table table;
	Label totalMass;
	Label massPerGoo;
	Label numGoo;
	BitmapFont ft;
	Minimap map;
	
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
		//table.debug();
		table.setFillParent(true);
		table.bottom();
		table.left();
		stage.addActor(table);
		
		Image minMap = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/minimap_no_border.png")));
		
		//Bottom right
		Image botBar = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/botBar.png")));
		botBar.setWidth(Gdx.graphics.getWidth() - minMap.getImageWidth());
		botBar.setHeight(60);
		botBar.setAlign(Align.bottomLeft);
		
		float size = 80.0f;
		//MergeButton
		Button btMerge = new Button(skin.newDrawable("white", Color.WHITE));
		Image mergeUp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/merge_button_regular.png")));
		Image mergeHover = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/merge_button_hover.png")));
		Image mergeDown = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/merge_button_click.png")));
		btMerge.setWidth(80);
		btMerge.setHeight(80);
		btMerge.setX(Gdx.graphics.getWidth() - 570);
		btMerge.setY(botBar.getImageHeight() + size - 10);
		btMerge.setProgrammaticChangeEvents(false);
		
		ButtonStyle mergeStyle = new ButtonStyle();
		mergeStyle.over = mergeHover.getDrawable();
		mergeStyle.up = mergeUp.getDrawable();
		mergeStyle.down = mergeDown.getDrawable();
		btMerge.setStyle(mergeStyle);
		
		//SplitButton
		Button btSplit = new Button(skin.newDrawable("white", Color.WHITE));
		Image splitUp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/split_button_regular.png")));
		Image splitHover = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/split_button_hover.png")));
		Image splitDown = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/split_button_click.png")));
		btSplit.setWidth(80);
		btSplit.setHeight(80);
		btSplit.setX(Gdx.graphics.getWidth() - 570 - size - 5);
		btSplit.setY(botBar.getImageHeight() + size - 10);
		btSplit.setProgrammaticChangeEvents(false);
		
		ButtonStyle splitStyle = new ButtonStyle();
		splitStyle.over = splitHover.getDrawable();
		splitStyle.up = splitUp.getDrawable();
		splitStyle.down = splitDown.getDrawable();
		btSplit.setStyle(splitStyle);
		
		//AttackButton
		Button btAttack = new Button(skin.newDrawable("white", Color.WHITE));
		Image attackUp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/attack_button_regular.png")));
		Image attackHover = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/attack_button_hover.png")));
		Image attackDown = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/attack_button_click.png")));
		btAttack.setWidth(80);
		btAttack.setHeight(80);
		btAttack.setX(Gdx.graphics.getWidth() - 570 - 2 * size - 10);
		btAttack.setY(botBar.getImageHeight() + size - 10);
		btAttack.setProgrammaticChangeEvents(false);
		
		ButtonStyle attackStyle = new ButtonStyle();
		attackStyle.over = attackHover.getDrawable();
		attackStyle.up = attackUp.getDrawable();
		attackStyle.down = attackDown.getDrawable();
		btAttack.setStyle(attackStyle);
		
		//BuildButton
		Button btBuild = new Button(skin.newDrawable("white", Color.WHITE));
		Image buildUp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/build_button_regular.png")));
		Image buildHover = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/build_button_hover.png")));
		Image buildDown = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/build_button_click.png")));
		btBuild.setWidth(80);
		btBuild.setHeight(80);
		btBuild.setX(Gdx.graphics.getWidth() - 570 - 3 * size - 15);
		btBuild.setY(botBar.getImageHeight() + size - 10);
		btBuild.setProgrammaticChangeEvents(false);
		
		ButtonStyle buildStyle = new ButtonStyle();
		buildStyle.over = buildHover.getDrawable();
		buildStyle.up = buildUp.getDrawable();
		buildStyle.down = buildDown.getDrawable();
		btBuild.setStyle(buildStyle);
		
		//React button
		Button btReact = new Button(skin.newDrawable("white", Color.WHITE));
		Image reactUp = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/react_button_regular.png")));
		Image reactHover = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/react_button_hover.png")));
		Image reactDown = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/react_button_click.png")));
		btReact.setWidth(80);
		btReact.setHeight(80);
		btReact.setX(220);
		btReact.setY(botBar.getImageHeight() + size - 10);
		btReact.setProgrammaticChangeEvents(false);
		
		ButtonStyle reactStyle = new ButtonStyle();
		reactStyle.over = reactHover.getDrawable();
		reactStyle.up = reactUp.getDrawable();
		reactStyle.down = reactDown.getDrawable();
		btReact.setStyle(reactStyle);
		
		//Inventory button 1
		Button btInv1 = new Button(skin.newDrawable("white", Color.WHITE));
		Image inv1Up = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/basicInventory.png")));
		btInv1.setWidth(80);
		btInv1.setHeight(80);
		btInv1.setX(20);
		btInv1.setY(botBar.getImageHeight() + size - 10);
		btInv1.setProgrammaticChangeEvents(false);
		
		ButtonStyle btInvStyle = new ButtonStyle();
		btInvStyle.up = inv1Up.getDrawable();
		btInv1.setStyle(btInvStyle);

		//Inventory button 2
		Button btInv2 = new Button(skin.newDrawable("white", Color.WHITE));
		Image inv2Up = new Image(new Texture(Gdx.files.local("assets/textures/interface/GameUI/basicInventory.png")));
		btInv2.setWidth(80);
		btInv2.setHeight(80);
		btInv2.setX(120);
		btInv2.setY(botBar.getImageHeight() + size - 10);
		btInv2.setProgrammaticChangeEvents(false);
		
		ButtonStyle btInv2Style = new ButtonStyle();
		btInv2Style.up = inv2Up.getDrawable();
		btInv2.setStyle(btInv2Style);
		
		//Table settings
		//table.add(group).align(Align.bottomLeft);
		//table.add(minMap).width(Gdx.graphics.getWidth() / 4).height(Gdx.graphics.getHeight() / 4).align(Align.bottomLeft);
		
		//Text for group of bot bar
		HorizontalGroup hz = new HorizontalGroup();
		
		BitmapFont ft = new BitmapFont();
		ft.getData().scale(1.5f);
		LabelStyle ls = new LabelStyle(ft, Color.BLACK);
		
		totalMass = new Label("Total mass: ", ls);
		massPerGoo = new Label("Mass per goo: ", ls);
		numGoo = new Label("Number of goo: ", ls);
		hz.addActor(totalMass);
		hz.addActor(numGoo);
		hz.addActor(massPerGoo);
		hz.setY(30);
		hz.setX(250);
		
		//Minimap
		map = new Minimap(480, 270);
		table.add(map);
		
		Group group = new Group();
		group.addActor(botBar);
		group.addActor(btMerge);
		group.addActor(btSplit);
		group.addActor(btAttack);
		group.addActor(btBuild);
		group.addActor(btReact);
		group.addActor(btInv1);
		group.addActor(btInv2);
		group.addActor(hz);
		
		table.add(group).width(Gdx.graphics.getWidth() - minMap.getImageWidth()).align(Align.bottomLeft);
		
		//Events for buttons
		btMerge.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Array<Entity> ent = Main.findGameBox("game").getEntities();
				Array<Goo> m = new Array<Goo>();
				for (int i = 0; i < ent.size; i++) {
					if (ent.get(i) instanceof Goo) {
						if (((Goo)ent.get(i)).isSelected()) {
							m.add((Goo)ent.get(i));
						}
					}
				}
				if (m.size == 0){
					return;
				}
				m.get(0).merge(m);
			}
		});
		
		btSplit.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Array<Entity> ent = Main.findGameBox("game").getEntities();
				for (int i = 0; i < ent.size; i++) {
					if (ent.get(i) instanceof Goo) {
						if (((Goo)ent.get(i)).isSelected()) {
							((Goo)ent.get(i)).split(new Vector2(1, 0));
						}
					}
				}
			}
		});
		
		btAttack.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {

			}
		});
		
		btBuild.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {

			}
		});
		
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

		//Minimap update
		map.update();
		
		//Total mass calculations
		String s1 = "Total mass: ";
		String s2 = "Number of goo: ";
		String s3 = "Mass per goo: ";
		
		int totalMassNum = 0;
		int numGooNum = 0;
		Array<Entity> ent = Main.findGameBox("game").getEntities();
		for (int i = 0; i < ent.size; i++) {
			if (ent.get(i).getType() == Entity.GOO) {
				if (((Goo) ent.get(i)).getOwner() == 0) {
					totalMassNum += ent.get(i).getMass();
					numGooNum++;
				}
			}
		}
		totalMass.setText(s1 + totalMassNum + "      ");
		numGoo.setText(s2 + numGooNum + "      ");
		if(numGooNum == 0){
			massPerGoo.setText(s3 + (totalMassNum / 1));
		} else {
			massPerGoo.setText(s3 + (totalMassNum / numGooNum));
		}
		
		
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
		ft.dispose();
	}

	public static void setFocus() {
		Gdx.input.setInputProcessor(stage);
	}
}
