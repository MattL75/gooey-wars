package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gooeywars.game.Component;

public class GameKeyInput extends Component{
	private boolean isWPressed;
	private boolean isSPressed;
	private boolean isAPressed;
	private boolean isDPressed;
	
	public GameKeyInput() {
		
	}
	
	@Override
	public void create() {
		
	}

	@Override
	public void update() {
		isWPressed = Gdx.input.isKeyPressed(Input.Keys.W);
		isSPressed = Gdx.input.isKeyPressed(Input.Keys.S);
		isAPressed = Gdx.input.isKeyPressed(Input.Keys.A);
		isDPressed = Gdx.input.isKeyPressed(Input.Keys.D);
		
		if (isWPressed) {
			
		}
		
		if (isSPressed) {
			
		}
		
		if (isAPressed) {
			
		}
		
		if (isDPressed) {
			
		}
		
	}

}
