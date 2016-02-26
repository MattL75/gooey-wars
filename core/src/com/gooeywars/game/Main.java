package com.gooeywars.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public static ArrayList<GameBox> gameBoxes = new ArrayList<GameBox>();
	public static boolean debug;
	
	@Override
	public abstract void create ();

	@Override
	public abstract void render ();
	
	@Override
	public abstract void resize (int x, int y);
	
	@Override
	public abstract void dispose ();
	
	
	
}
