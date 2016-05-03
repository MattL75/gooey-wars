package com.gooeywars.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class GeyserProperty {
	public static final int NOTHING = -1;
	public static final int WATER = 0;
	public static final int CARBON = 1;
	public static final int IRON = 2;
	public static final int CALCIUM = 3;
	public static final int SILICON = 4;
	public static final int OXYGEN = 5;
	public static final int URANIUM = 6;

	int element;

	private Pixmap pix;

	public static Pixmap waterPix;
	public static Pixmap carbonPix;
	public static Pixmap ironPix;
	public static Pixmap calciumPix;
	public static Pixmap siliconPix;
	public static Pixmap oxygenPix;
	public static Pixmap uraniumPix;

	public static Pixmap nothingGooPix;
	public static Pixmap waterGooPix;
	public static Pixmap carbonGooPix;
	public static Pixmap ironGooPix;
	public static Pixmap calciumGooPix;
	public static Pixmap siliconGooPix;
	public static Pixmap oxygenGooPix;
	public static Pixmap uraniumGooPix;
	
	
	//private int propInt;
	
	public GeyserProperty(){
		genGeyserProp(0);
	}
	
	public GeyserProperty(int prop){
		genGeyserProp(prop);
	}
	
	private void genGeyserProp(int prop){
		element = prop;
		
		switch(prop){
		case WATER:
			pix = waterPix;
			break;
		case CARBON: 
			pix = carbonPix;
			break;
		case IRON: 
			pix = ironPix;
			break;
		case CALCIUM: 
			pix = calciumPix;
			break;
		case SILICON: 
			pix = siliconPix;
			break;
		case OXYGEN: 
			pix = oxygenPix;
			break;
		case URANIUM: 
			pix = uraniumPix;
			break;
		
		}
	}
	
	public static Pixmap getGooElementPix(int type){
		System.out.println("Getting type" + type);
		switch(type){
		case NOTHING: return nothingGooPix;
		case WATER: return waterGooPix;
		case CARBON: return carbonGooPix;
		case IRON: return ironGooPix;
		case CALCIUM: return calciumGooPix;
		case SILICON: return siliconGooPix;
		case OXYGEN: return oxygenGooPix;
		case URANIUM: return uraniumGooPix;
		}
		
		return null;
	}

	public static void loadTextures(){
		//Updated textures
		waterPix = new Pixmap(Gdx.files.local("assets/textures/geyser/geyser_water_big.png"));
		carbonPix = new Pixmap(Gdx.files.local("assets/textures/geyser/geyser_black_big.png"));
		ironPix = new Pixmap(Gdx.files.local("assets/textures/geyser/geyser_red_big.png"));
		
		//Not updated textures
		calciumPix = new Pixmap(Gdx.files.local("assets/textures/geyser/calcium_geyser.png"));
		siliconPix = new Pixmap(Gdx.files.local("assets/textures/geyser/silicon_geyser.png"));
		oxygenPix = new Pixmap(Gdx.files.local("assets/textures/geyser/oxygen_geyser.png")); 
		uraniumPix = new Pixmap(Gdx.files.local("assets/textures/geyser/uranium_geyser.png"));
		
		nothingGooPix = new Pixmap(0,0,Format.RGBA4444);
		waterGooPix = new Pixmap(Gdx.files.local("assets/textures/goo/element/water_goo.png"));
		carbonGooPix = new Pixmap(Gdx.files.local("assets/textures/goo/element/carbon_goo.png"));
		ironGooPix = new Pixmap(Gdx.files.local("assets/textures/goo/element/iron_goo.png"));
		/*calciumGooPix = new Pixmap(Gdx.files.local("assets/textures/goo/element/calcium_goo.png"));
		siliconGooPix = new Pixmap(Gdx.files.local("assets/textures/goo/element/silicon_goo.png"));
		oxygenGooPix = new Pixmap(Gdx.files.local("assets/textures/goo/element/oxygen_goo.png")); 
		uraniumGooPix = new Pixmap(Gdx.files.local("assets/textures/goo/element/uranium_goo.png"));*/
	}

	public Pixmap getPix() {
		return pix;
	}
}

