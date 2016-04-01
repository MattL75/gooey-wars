package com.gooeywars.gameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.GameBox;
import com.gooeywars.game.Main;
import com.gooeywars.physics.Collider;

public class GameState {
	private FileHandle file;
	private String locRoot = Gdx.files.getLocalStoragePath();
	private String locSave = "saves/";
	
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
		Array<Entity> entities = box.getEntities();
		int NumberEntity = entities.size;
		
		//Condition for not saving if no entities
		if (NumberEntity == 0) {
			System.out.println("GameState saves entities. There are none.");
			return;
		}
		
		//Information for debugging
		if (Main.debug) {
			System.out.println("--SAVING--\n" +
					NumberEntity + "\n" +
					locRoot + "\n" +
					file.name() + "\n" +
					file.path());
		}
		
		//Loop writes data for every entity
		for (int i = 0; i < NumberEntity; i++) {
			String s = entities.get(i).getSaveData();
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
		
		//Reads file as one line and splits every '+' into array
		String s = file.readString();
		String[] mainStringArray = s.split("\\+");
		
		//Debugging
		if (Main.debug) {
			System.out.println("--LOADING--\n");
			System.out.println(s);
		}
		
		String[] secStringArray;
		float[] floatArray;
		
		//Goes through elements of mainStringArray, parses everything to floats
		for (int i = 0; i < mainStringArray.length; i++) {
			secStringArray = mainStringArray[i].split(",");
			floatArray = new float[secStringArray.length];
			
			//Loop parses string into float
			for (int j = 0; j < secStringArray.length; j++) {
				
				//If statement deals with boolean (true == 1, false == 0)
				if (secStringArray[j].equals("true")) {
					secStringArray[j] = String.valueOf(1);
				} else if (secStringArray[j].equals("false")) {
					secStringArray[j] = String.valueOf(0);
				}
				
				floatArray[j] = Float.parseFloat(secStringArray[j]);
			}
			
			//Build entity if type is 0
			if (floatArray[1] == 0) {
				Entity entTemp;
				if (floatArray[4] == 1) {
					Vector2 f = new Vector2(floatArray[5], floatArray[6]);
					Vector2 v = new Vector2(floatArray[7], floatArray[8]);
					Vector2 a = new Vector2(floatArray[9], floatArray[10]);
					entTemp = new Entity(new Sprite(), new Array<Collider>(), floatArray[2], floatArray[3], (int)floatArray[11], f, v, a);
					entTemp.setPhysicsEnabled(true);
				} else {
					entTemp = new Entity(new Sprite(), new Array<Collider>(), floatArray[2], floatArray[3], false);
				}
				box.addEntity(entTemp);
			}
			
			//Build goo if type is 1
			if (floatArray[1] == 1) {
				Vector2 f = new Vector2(floatArray[5], floatArray[6]);
				Vector2 v = new Vector2(floatArray[7], floatArray[8]);
				Vector2 a = new Vector2(floatArray[9], floatArray[10]);
				box.addEntity(new Goo(floatArray[2], floatArray[3], (int)floatArray[11], f, v, a, (int)floatArray[12], (int)floatArray[13], (int)floatArray[14]));
			}
		}
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
