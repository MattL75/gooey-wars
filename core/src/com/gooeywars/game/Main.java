package com.gooeywars.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public abstract class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public static Array<GameBox> gameBoxes = new Array<GameBox>();
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
