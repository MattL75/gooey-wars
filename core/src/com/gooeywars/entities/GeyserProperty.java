package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Texture;

public class GeyserProperty {
	public static final float WATER = 0;
	public static final float CARBON = 0;
	
	
	public boolean isCarbon;
	
	private Texture texture;
	
	public GeyserProperty(){
		genGeyser(0);
	}
	
	public GeyserProperty(int prop){
		genGeyser(prop);
	}
	
	private void genGeyser(int prop){
		switch(prop){
		case 0: 
			isCarbon = true;
			
		
		}
	}
	
	private void loadTexture(){
		
	}
}
