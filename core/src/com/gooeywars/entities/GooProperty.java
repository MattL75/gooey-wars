package com.gooeywars.entities;

public class GooProperty {
	public static final int BLACK = 0;
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	public static final int PURPLE = 4;
	public static final int PINK = 5;
	
	private float damage;
	private float defence;
	
	private boolean solidified;
	private boolean flammable;
	private boolean explosive;
	private boolean radioactive;
	private boolean immobilized;
	
	private int propInt;
	
	public static final int DEFAULT_GOO = 0;
	public static final int STEEL = 1;
	public static final int FIRE_PROOF = 2;
	public static final int EXPLOSIVE = 3;
	public static final int NUCLEAR = 4;
	public static final int CALCIFIED_STEEL = 5;
	public static final int C4 = 6;
	public static final int NUCLEAR_BOMB = 7;
	
	public GooProperty(){
		genGooProp(0);
	}
	
	public GooProperty(int prop){
		
	}
	
	private void genGooProp(int prop){
		propInt = prop;
		
		switch(prop){
		case 0:
			solidified = false;
			flammable = true;
			explosive = false;
			radioactive = false;
			immobilized = false;
			damage = 1;
			defence = 1;
		case 1:
		case 2:
		case 3:
		case 4:
		}
	}
}
