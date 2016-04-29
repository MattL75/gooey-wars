package com.gooeywars.AI;

import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.game.GameBox;

public class Computer extends Component{
	private int playerId;
	private GameBox gamebox;
	private Array<Entity> entities;
	private Array<AiTask> tasks;
	private Array<Goo> ownedGoos;
	
	boolean alive;
	
	public Computer(GameBox gamebox){
		playerId = 1;
		this.gamebox = gamebox;
		entities = gamebox.getEntities();
		ownedGoos = new Array<Goo>();
		tasks = new Array<AiTask>();
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
		
		boolean initialized;
		
		public AiRunnable() {
		
		}
		
		@Override
		public void run() {
			while(alive){
				if(!initialized){
					initialize();
				}
				System.out.println(ownedGoos.size);
			}
		}
		
		private void initialize(){
			initialized = true;
			System.out.println("Initializing AI...");
			
		}
		
	}
}


