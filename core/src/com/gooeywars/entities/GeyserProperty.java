package com.gooeywars.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class GeyserProperty {
	public static final float WATER = 0;
	public static final float CARBON = 0;
	
	
	public boolean isCarbon;
	
	private Pixmap pix;
	
	private static Pixmap waterPix;
	
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
			isCarbon = true;
			pix = waterPix;
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
