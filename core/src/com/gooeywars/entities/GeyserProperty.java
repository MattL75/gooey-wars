package com.gooeywars.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GeyserProperty {
	public static final float WATER = 0;
	public static final float CARBON = 1;
	public static final float IRON = 2;
	
	
	public boolean isWater;
	public boolean isCarbon;
	public boolean isIron;
	
	private Pixmap pix;
	
	private static Pixmap waterPix;
	private static Pixmap carbonPix;
	private static Pixmap ironPix;
	
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
			break;
		case 1: 
			isWater = false;
			isCarbon = true;
			isIron = false;
			pix = carbonPix;
			break;
		case 2: 
			isWater = false;
			isCarbon = false;
			isIron = true;
			pix = ironPix;
			break;
		}
	}
	

	public static void loadTextures(){
		waterPix = new Pixmap(Gdx.files.local("assets/textures/geyser/water_geyser.jpg"));
		
	}

	public static Pixmap getWaterPix() {
		return waterPix;
	}

	public static void setWaterPix(Pixmap waterPix) {
		GeyserProperty.waterPix = waterPix;
	}

	public Pixmap getPix() {
		return pix;
	}
}
