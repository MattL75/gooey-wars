package com.gooeywars.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	
	private int minedById;
	
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
		System.out.println("Mine");
		minedById = goo.getId();
		timer.clear();
		timer.scheduleTask(new mineTask(goo), 0f, 0.1f);
		timer.start();
		
		
	}
	
	public void stopMining(Goo goo){
		if(goo.getId() == minedById){
			timer.stop();
			timer.clear();
		}
		
	}
	
	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public String getSaveData(){
		String data = super.getSaveData();
		data+= "," + propInt;
		return data;
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
		System.out.println("mining");
		System.out.println(goo.getMass());
	}
	
}
