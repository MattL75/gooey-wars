package com.gooeywars.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GeyserProperty {
	public static final float WATER = 0;
	public static final float CARBON = 0;
	
	
	public boolean isCarbon;
	
	private Texture texture;
	
	private static Texture waterTexture;
	
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
			texture = waterTexture;
		}
	}
	

	public static void loadTextures(){
		waterTexture = new Texture(Gdx.files.local("assets/textures/geyser/water_geyser.jpg"));
	}

	public static Texture getWaterTexture() {
		return waterTexture;

	}

	public static void setWaterTexture(Texture waterTexture) {
		GeyserProperty.waterTexture = waterTexture;
	}

	public Texture getTexture() {
		return texture;
	}

	
}
