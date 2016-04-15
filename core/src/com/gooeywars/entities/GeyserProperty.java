package com.gooeywars.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

public class GeyserProperty {
	public static final float WATER = 0;
	public static final float CARBON = 1;
	public static final float IRON = 2;
	
	int element;
	
	public boolean isWater;
	public boolean isCarbon;
	public boolean isIron;
	
	private Pixmap pix;
	
	public static Pixmap waterPix;
	public  static Pixmap carbonPix;
	public static Pixmap ironPix;
	
	private int propInt;
	
	public GeyserProperty(){
		genGeyserProp(0);
	}
	
	public GeyserProperty(int prop){
		genGeyserProp(prop);
	}
	
	private void genGeyserProp(int prop){
		propInt = prop;
		
		switch(prop){
		case 0:
			isWater = true;
			isCarbon = false;
			isIron = false;
			pix = waterPix;
			element = 0;
			break;
		case 1: 
			isWater = false;
			isCarbon = true;
			isIron = false;
			pix = carbonPix;
			element = 1;
			break;
		case 2: 
			isWater = false;
			isCarbon = false;
			isIron = true;
			pix = ironPix;
			element = 2;
			break;
		}
	}
	

	public static void loadTextures(){
		waterPix = new Pixmap(Gdx.files.local("assets/textures/geyser/water_geyser.jpg"));
		carbonPix = new Pixmap(Gdx.files.local("assets/textures/geyser/carbon_geyser.png"));
		ironPix = new Pixmap(Gdx.files.local("assets/textures/geyser/iron_geyser.png"));
	}

	public Pixmap getPix() {
		return pix;
	}
}
