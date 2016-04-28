package com.gooeywars.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class GooProperty {
	public static final int BLACK = 0;
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	public static final int PURPLE = 4;
	public static final int PINK = 5;
	
	private boolean isRanged;
	private int range;
	
	private float velocityFactor;
	private float damage;
	private float defence;
	
	//Passive Properties
	private boolean solidified;
	private boolean flammable;
	private boolean explosive;
	private boolean radioactive;
	
	//Active Properties
	private boolean immobilized;
	public boolean onFire;
	private boolean radiated;
	
	private int propInt;
	
	public static final int EXPLODED = -1;
	public static final int DEFAULT_GOO = 0;
	public static final int STEEL = 1;
	public static final int FIRE_PROOF = 2;
	public static final int EXPLOSIVE = 3;
	public static final int NUCLEAR = 4;
	public static final int CALCIFIED_STEEL = 5;
	public static final int C4 = 6;
	public static final int NUCLEAR_BOMB = 7;
	
	private static Pixmap defaultPix;
	private static Pixmap steelPix;
	private static Pixmap fireProofPix;
	private static Pixmap explosivePix;
	private static Pixmap nuclearPix;
	private static Pixmap calcifiedSteelPix;
	private static Pixmap c4Pix;
	private static Pixmap nuclearBombPix;
	
	private Pixmap gooTypePix;
	
	//privat int 
	
	public GooProperty(){
		genGooProp(0);
	}
	
	public GooProperty(int prop){
		genGooProp(prop);
	}
	
	private void genGooProp(int prop){
		propInt = prop;
		
		switch(prop){
		case -1:break;
		case 0:
			//Default Goo
			velocityFactor = 1;
			damage = 1;
			defence = 1;
			solidified = false;
			flammable = true;
			explosive = false;
			radioactive = false;
			immobilized = false;
			gooTypePix = defaultPix;
			break;
		case 1:
			//Steel Goo
			velocityFactor = 1;
			damage = 1;
			defence = 2;
			solidified = true;
			flammable = true;
			explosive = false;
			radioactive = false;
			immobilized = true;
			gooTypePix = steelPix;
			break;
		case 2:
			//Fire proof
			velocityFactor = 1;
			damage = 1;
			defence = 1;
			solidified = false;
			flammable = false;
			explosive = false;
			radioactive = false;
			immobilized = false;
			gooTypePix = fireProofPix;
			break;
		case 3:
			//Explosive Goo
			velocityFactor = 1;
			damage = 1;
			defence = 1;
			solidified = false;
			flammable = true;
			explosive = true;
			radioactive = false;
			immobilized = false;
			gooTypePix = explosivePix;
			break;
		case 4:
			//Nuclear Goo
			velocityFactor = 1;
			damage = 1;
			defence = 1;
			solidified = false;
			flammable = true;
			explosive = false;
			radioactive = true;
			immobilized = false;
			gooTypePix = nuclearPix;
			break;
		case 5:
			//Calcified_Steel Goo
			velocityFactor = 1;
			damage = 1;
			defence = 2;
			solidified = true;
			flammable = false;
			explosive = false;
			radioactive = false;
			immobilized = true;
			gooTypePix = calcifiedSteelPix;
			break;
		case 6:
			//C4 Goo
			velocityFactor = 1;
			damage = 1;
			defence = 1;
			solidified = false;
			flammable = false;
			explosive = true;
			radioactive = false;
			immobilized = false;
			gooTypePix = c4Pix;
			break;
		case 7:
			//Nuclear Bomb Goo
			velocityFactor = 1;
			damage = 1;
			defence = 1;
			solidified = false;
			flammable = false;
			explosive = true;
			radioactive = true;
			immobilized = false;
			gooTypePix = nuclearBombPix;
			
			break;
		}
	}
	
	public void react(int element1, int element2){
		if(element1 == GeyserProperty.WATER || element2 == GeyserProperty.WATER){
			System.out.println("Default");
			
			genGooProp(GooProperty.EXPLODED);
		}
		
		if(element1 == GeyserProperty.CARBON && element2 == GeyserProperty.IRON || element1 == GeyserProperty.IRON && element2 == GeyserProperty.CARBON){
			System.out.println("Steel");
			genGooProp(GooProperty.STEEL);
		}
	}
	
	public static void loadTextures(){
		defaultPix = new Pixmap(0,0,Format.RGBA4444);
		steelPix = new Pixmap(Gdx.files.local("assets/textures/goo/type/steel_goo.png"));
		fireProofPix = new Pixmap(Gdx.files.local("assets/textures/goo/type/fire_proof_goo.png"));
		explosivePix = new Pixmap(Gdx.files.local("assets/textures/goo/type/explosive_goo.png"));
		nuclearPix = new Pixmap(Gdx.files.local("assets/textures/goo/type/nuclear_goo.png"));
		calcifiedSteelPix = new Pixmap(Gdx.files.local("assets/textures/goo/type/calcified_steel_goo.png"));
		c4Pix = new Pixmap(Gdx.files.local("assets/textures/goo/type/c4_goo.png"));
		nuclearBombPix = new Pixmap(Gdx.files.local("assets/textures/goo/type/nuclear_bomb_goo.png"));
	}
	
	public int getPropInt() {
		return propInt;
	}
	
	public float getVelocityFactor() {
		return velocityFactor;
	}

	public void setVelocityFactor(float velocityFactor) {
		this.velocityFactor = velocityFactor;
	}

	public float getDamage() {
		return damage;
	}

	public float getDefence() {
		return defence;
	}

	public boolean isSolidified() {
		return solidified;
	}

	public boolean isFlammable() {
		return flammable;
	}

	public boolean isExplosive() {
		return explosive;
	}

	public boolean isRadioactive() {
		return radioactive;
	}

	//Active Properties
	public boolean isImmobilized() {
		return immobilized;
	}

	public boolean isRanged() {
		return isRanged;
	}

	public void setRanged(boolean isRanged) {
		this.isRanged = isRanged;
	}

	

	public Pixmap getGooTypePix() {
		return gooTypePix;
	}
}
