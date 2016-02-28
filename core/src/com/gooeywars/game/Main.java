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
	
	public static GameBox findGameBox(String tag){
		
		for(int i = 0; i < gameBoxes.size; i++){
			if(gameBoxes.get(i).getTag().equals(tag)){
				return gameBoxes.get(i);
			}
		}
		return null;
	}
	
	public static boolean checkTags(String tag){
		for(int i = 0; i < gameBoxes.size; i++){
			if(tag.equals(gameBoxes.get(i).getTag())){
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public abstract void create ();

	@Override
	public abstract void render ();
	
	@Override
	public abstract void resize (int x, int y);
	
	@Override
	public abstract void dispose ();
	
	
	
}
