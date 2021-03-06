package com.gooeywars.game.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gooeywars.game.GooeyWars;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		DisplayMode displayMode =  LwjglApplicationConfiguration.getDesktopDisplayMode();
		config.setFromDisplayMode(displayMode);
		
		if(GooeyWars.getIsFullScreen()){
			config.fullscreen = true;
		}
		config.foregroundFPS = 0;
		
		new LwjglApplication(new GooeyWars(), config);
	}
}
