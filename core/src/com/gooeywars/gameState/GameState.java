package com.gooeywars.gameState;

import java.io.IOException;
import java.io.Writer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.GameBox;
import com.gooeywars.game.GooeyWars;
import com.gooeywars.game.Main;

public class GameState {
	private FileHandle file;
	private String locRoot = Gdx.files.getLocalStoragePath();
	private String locSave = "saves/";
	private boolean debug = true;
	
	public GameState() {
		
	}
	
	public GameState(String fileName) {
		try {
			file = Gdx.files.local(locSave + fileName);
		}
		catch (Exception ex) {
			System.out.println("Error.");
			return;
		}
	}
	
	//Method saves all information to file 
	public void save() {
		GameBox box = Main.findGameBox("game");
		Array<Entity> Entities = box.getEntities();
		int NumberEntity = Entities.size;
		
		//Condition for not saving if no entities
		if (NumberEntity == 0) {
			System.out.println("GameState saves entities. There are none.");
			return;
		}
		
		//Information for debugging
		if (debug) {
			System.out.println(NumberEntity);
			System.out.println(locRoot);
			System.out.println(file.name());
			System.out.println(file.path());
		}
		
		//Begin writing to file
		file.writeString(String.valueOf(NumberEntity), false);
		
		//Loop writes data for every entity
		for (int i = 0; i < NumberEntity; i++) {
			Entities.get(i);
		}
	}
	
	//Method loads all information in file to gameBox
	public void load() {
		GameBox box = Main.findGameBox("game");
		box.clearEntities();
		
	}
	
	//Method sets the file to read
	public void setFile(FileHandle file) {
		this.file = file;
	}
	
	//Method gets the file to read
	public String getFile() {
		return this.file.name();
	}
}
