package com.gooeywars.AI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Geyser;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.game.GameBox;

public class Computer extends Component{
	private int playerId;
	private GameBox gamebox;
	private Array<Entity> entities;
	private Array<AiTask> tasks;
	
	private Array<Goo> ownedGoos;
	private int totalMass;
	
	private Array<Geyser> geysers;
	
	private int difficulty;
	public static final int EASY = 0;
	public static final int MEDIUM = 1;
	
	boolean alive;
	
	public Computer(GameBox gamebox, int difficulty){
		playerId = 1;
		this.gamebox = gamebox;
		entities = gamebox.getEntities();
		ownedGoos = new Array<Goo>();
		tasks = new Array<AiTask>();
		this.difficulty = difficulty;
		geysers = new Array<Geyser>();
	}

	@Override
	public void create() {
		Thread thread = new Thread(new AiRunnable());
		alive = true;
		thread.start();
	}
	
	@Override
	public void update() {
		ownedGoos.clear();
		for(int i = 0; i < entities.size; i++){
			if(entities.get(i) instanceof Goo){
				Goo goo = (Goo)entities.get(i);
				if(goo.getOwner() == playerId){
					totalMass += goo.getMass();
					ownedGoos.add(goo);
				}
			}
			
			
		}
		for(int i = 0; i < tasks.size; i++){
			tasks.get(i).execute();
		}
	}
	
	public void kill(){
		alive = false;
	}
	
	class AiRunnable implements Runnable{
		float resourcePriority;
		float defencePriority;
		float attackPriority;
		long begin;
		long end;
		
		boolean initialized;
		
		public AiRunnable() {
		
		}
		
		@Override
		public void run() {
			while(alive){
				if(!initialized){
					initialize();
				}
				end = System.currentTimeMillis();
				if(end - begin > 1000){
					calculatePriorities();
					determineActions();
					System.out.println("Computer.java Geyser Size" + geysers.size);
					begin = System.currentTimeMillis();
				}
			}
		}
		
		private void initialize(){
			initialized = true;
			System.out.println("Initializing AI...");
			for(int i = 0; i < entities.size; i++){
				if(entities.get(i) instanceof Geyser){
					geysers.add((Geyser)entities.get(i));
				}
			}
		}
		
		private void calculatePriorities(){
			if(totalMass < 100){
				resourcePriority = 1;
				attackPriority = 0;
				defencePriority = 0;
			}
		}
		
		private void determineActions(){
			if(resourcePriority > 0.95){
				for(int i = 0; i < ownedGoos.size; i++){
					findClosestGeyser(ownedGoos.get(i));
				}
			}
		}
		
		private Geyser findClosestGeyser(Goo goo){
			Geyser closest = geysers.first();
			
			for(int i = 0; i < geysers.size; i++){
				if(new Vector2(closest.getX()-goo.getX(), closest.getY() - goo.getY()).len2() > new Vector2(geysers.get(i).getX()-goo.getX(), geysers.get(i).getY() - goo.getY()).len2()){
					closest = geysers.get(i);
				}
			}
			return closest;
		}
	}
}


