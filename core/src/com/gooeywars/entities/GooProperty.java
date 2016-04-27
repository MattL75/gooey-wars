package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Pixmap;

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
	private boolean onFire;
	private boolean radiated;
	
	private int propInt;
	
	public static final int DEFAULT_GOO = 0;
	public static final int STEEL = 1;
	public static final int FIRE_PROOF = 2;
	public static final int EXPLOSIVE = 3;
	public static final int NUCLEAR = 4;
	public static final int CALCIFIED_STEEL = 5;
	public static final int C4 = 6;
	public static final int NUCLEAR_BOMB = 7;
	
	private Pixmap element1Pix;
	private Pixmap element2Pix;
	private Pixmap gooTypeTPix;
	
	public GooProperty(){
		genGooProp(0);
	}
	
	public GooProperty(int prop){
		genGooProp(prop);
	}
	
	private void genGooProp(int prop){
		propInt = prop;
		
		switch(prop){
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
			break;
		}
	}
	
	public void react(int element1, int element2){
		if(element1 == GeyserProperty.WATER || element2 == GeyserProperty.WATER){
			System.out.println("Default");
			genGooProp(GooProperty.DEFAULT_GOO);
		}
		
		if(element1 == GeyserProperty.CARBON && element2 == GeyserProperty.IRON){
			System.out.println("Steel");
			genGooProp(GooProperty.STEEL);
		}
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
}
