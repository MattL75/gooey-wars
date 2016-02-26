package com.gooeywars.game;

import com.gooeywars.UI.GameUI;
import com.gooeywars.UI.MainMenuUI;

public class GooeyWars extends Main{
	private GameBox game;
	private GameBox menu;
	
	@Override
	public void create() {
		MainMenuUI menuUI = new MainMenuUI();
		GameUI gameUI = new GameUI();
		
		menu = new GameBox(false, menuUI);
		game = new GameBox(true, gameUI);
	}

	@Override
	public void render() {
		game.update();
	}

	@Override
	public void resize(int x, int y) {	
	}

	@Override
	public void dispose() {
	}

}
