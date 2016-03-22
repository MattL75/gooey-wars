package com.gooeywars.gameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.game.GameBox;
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
			System.out.println("Error. File creation failed.");
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
			System.out.println("--SAVING--\n" +
					NumberEntity + "\n" +
					locRoot + "\n" +
					file.name() + "\n" +
					file.path() + "\n" +
					"--LOADING--");
		}
		
		//Begin writing to file (Not necessary as entities are split via split function)
		//file.writeString(String.valueOf(NumberEntity), false);
		
		//Loop writes data for every entity
		for (int i = 0; i < NumberEntity; i++) {
			String s = Entities.get(i).getSaveData();
			if (i == 0) {
				file.writeString("", false);			//Line overwrites the previous file
			} else {
				file.writeString("+", true);
			}
			file.writeString(s, true);
		}
	}
	
	//Method loads all information in file to gameBox
	public void load() {
		GameBox box = Main.findGameBox("game");
		box.clearEntities();
		
		String s = file.readString();
		String[] sArray = s.split("\\+");
		
		if (debug);
			System.out.println(s);
		
		//Convert to Array from libgdx
		Array<String> splitArray = new Array<String>();
		for (int i = 0; i < sArray.length; i++) {
			splitArray.add(sArray[i]);
		}
		
		if (debug);
			System.out.println(splitArray);
	}
	
	//Method sets the file to read
	public void setFile(FileHandle file) {
		this.file = file;
	}
	
	//Method gets the file to read
	public String getFileName() {
		return this.file.name();
	}
}
