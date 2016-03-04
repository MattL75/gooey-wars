package com.gooeywars.entities;

import com.badlogic.gdx.utils.Array;
import com.gooeywars.game.Main;

public class Environment {
	Array<Entity> children;
	
	public void addChild(Entity child){
		children.add(child);
		Main.findGameBox("game").addEntity(child);
	}
}
