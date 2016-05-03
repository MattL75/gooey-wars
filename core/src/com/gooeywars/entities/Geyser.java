package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.gooeywars.physics.Collider;
import com.gooeywars.util.shape.Square;

public class Geyser extends Entity{
	private GeyserProperty property;
	private int propInt;
	private boolean occupied;
	private Timer timer;
	
	private Goo miningGoo;
	private boolean grabbedGoo;
	private boolean started;
	
	private boolean isMining;
	
	public Geyser(){
		genGeyser(0,0,0);
	}
	
	public Geyser(int prop){
		genGeyser(0,0,prop);
	}
	
	public Geyser(float x, float y){
		genGeyser(x,y,0);
	}
	
	public Geyser(float x, float y, int prop){
		genGeyser(x,y,prop);
	}
	
	private void genGeyser(float x, float y, int prop){
		timer = new Timer();
		setType(Entity.GEYSER);
		propInt = prop;
		setPhysicsEnabled(false);
		setX(x);
		setY(y);
		setWidth(50);
		setHeight(50);
		property = new GeyserProperty(prop);
		setSprite(new Sprite(new Texture(property.getPix())));
		getSprite().setSize(50, 50);
		setType(Entity.GEYSER);
		genCollider();
	}
	
	public void genCollider(){
		Square square = new Square((int)getWidth(), (int)getX(), (int)getY());
		Collider coll = new Collider(square);
		coll.setDrawable(true);
		Array<Collider> colls = new Array<Collider>();
		colls.add(coll);
		setColliders(colls);
	}
	
	public void mine(Goo goo){
		if(!isMining){
			if(property.element != goo.getElement1() && property.element != goo.getElement2()){
				goo.setElement2(goo.getElement1());
				goo.setElement1(property.element);
			}
			isMining = true;
			miningGoo = goo;
		
			timer.scheduleTask(new mineTask(goo), 0f, 0.1f);
			timer.start();
		}
		
	}
	
	public void stopMining(Goo goo){
		if(miningGoo != null){
			if(goo.getId() == miningGoo.getId()){
				isMining = false;
				miningGoo = null;
				timer.stop();
				timer.clear();
			}	
		}
		
	}
	
	public String getSaveData(){
		String data = super.getSaveData();
		data+= "," + propInt;
		return data;
	}
	
	public boolean getIsMining(){
		return isMining;
	}
	
}

class mineTask extends Task{
	Goo goo;
	
	public mineTask(Goo goo){
		this.goo = goo;
	}
	
	@Override
	public void run() {
		goo.setMass(goo.getMass()+1);
	}
	
}
