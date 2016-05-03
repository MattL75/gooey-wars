package com.gooeywars.AI;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Geyser;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.game.GameBox;
import com.sun.corba.se.spi.activation._ActivatorImplBase;

public class Computer extends Component{
	private int playerId;
	private GameBox gamebox;
	private Array<Entity> entities;
	private Array<AiTask> tasks;
	
	private Array<Goo> ownedGoos;
	private Array<Goo> miningGoos;
	private Array<Goo> attackingGoos;
	private Array<Goo> defendingGoos;
	
	private Array<Goo> enemyGoos;
	private int totalMass;
	private float massPerGoo;
	private Goo largestGoo;
	
	private Array<Geyser> geysers;
	private Geyser homeGeyser;
	
	private int difficulty;
	public static final int EASY = 0;
	public static final int MEDIUM = 1;
	
	Executor executor;
	
	boolean alive;
	
	public Computer(GameBox gamebox, int difficulty){
		playerId = 1;
		this.gamebox = gamebox;
		entities = gamebox.getEntities();
		
		ownedGoos = new Array<Goo>();
		enemyGoos = new Array<Goo>();
		miningGoos = new Array<Goo>();
		attackingGoos = new Array<Goo>();
		defendingGoos = new Array<Goo>();
		
		tasks = new Array<AiTask>();
		this.difficulty = difficulty;
		geysers = new Array<Geyser>();
		largestGoo = new Goo(0,0,0);
		executor = Executors.newCachedThreadPool();
	}

	@Override
	public void create() {
		Thread thread = new Thread(new AiRunnable());
		alive = true;
		update();
		executor.execute(thread);
	}
	
	@Override
	public void update() {
		if(alive){
			
			for(int i = 0; i < tasks.size; i++){
				tasks.get(i).execute();
			}
		}
		
	}
	
	public void loseGame(){
		kill();
	}
	
	public void winGame(){
		kill();
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
				//if(end - begin > 500){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ownedGoos.clear();
					enemyGoos.clear();
					
					miningGoos.clear();
					attackingGoos.clear();
					defendingGoos.clear();
					
					totalMass = 0;
					
					
					for(int i = 0; i < entities.size; i++){
						if(entities.get(i) instanceof Goo){
							Goo goo = (Goo)entities.get(i);
							if(goo.getOwner() == playerId){
								totalMass += goo.getMass();
								ownedGoos.add(goo);
								
								if(goo.getMass() > largestGoo.getMass()){
									largestGoo = goo;
								}
							} else {
								enemyGoos.add(goo);
							}
						}	
					}
					
					if(enemyGoos.size == 0){
						winGame();
						return;
					}
					
					if(ownedGoos.size == 0){
						loseGame();
						return;
					}
					
					
					massPerGoo = totalMass/ownedGoos.size;
					
					calculatePriorities();
					populateLists();
					determineActions();
					
					/*System.out.println("Debugging...");
					System.out.println("Owned Goos " + ownedGoos.size);
					System.out.println("Resource " + resourcePriority);
					System.out.println("Attack " + attackPriority);
					System.out.println("Defence " + defencePriority);
					System.out.println("Resourcing Goos " + miningGoos.size);
					System.out.println("Attacking Goos " + attackingGoos.size);
					System.out.println("Defending Goos " + defendingGoos.size);*/
					
					begin = System.currentTimeMillis();
				//}
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
			
			if(geysers.size > 0){
				designateHome();
			}
		}
		
		private void calculatePriorities(){
			if(totalMass < 100){
				resourcePriority = 1;
				attackPriority = 0;
				defencePriority = 0;
			}
			
			if(totalMass >= 100 && totalMass < 500){
				resourcePriority = 0.5f;
				attackPriority = 0f;
				defencePriority = 0.5f;
			}
			
			/*if(ownedGoos.size > 10 && massPerGoo > 40){
				resourcePriority = 0.1f;
				attackPriority = 0.6f;
				defencePriority = 0.2f;
			}*/
		}
		
		private void determineActions(){
			//Displacement			
			
			
			for(int i = 0; i < miningGoos.size; i++){
				AiTask  task = new AiTask();
				
				Geyser geyser = findBestGeyser(miningGoos.get(i));
				task.move(miningGoos.get(i), new Vector2(geyser.getX() + geyser.getWidth()/2, geyser.getY() + geyser.getHeight()/2));
				tasks.add(task);
			}
			
			for(int i = 0; i < attackingGoos.size; i++){
				AiTask task = new AiTask();
				Goo enemyGoo = findBestOpponentAttack(attackingGoos.get(i));
				
				task.attack(attackingGoos.get(i), new Vector2(enemyGoo.getX() + enemyGoo.getWidth()/2,enemyGoo.getY() + enemyGoo.getHeight()/2));
				tasks.add(task);
			}
			
			for(int i = 0; i < defendingGoos.size; i++){
				AiTask  task = new AiTask();
				System.out.println("Defending");
				task.move(defendingGoos.get(i), new Vector2(homeGeyser.getX() + (float)(Math.random() * 10), homeGeyser.getY() + (float)(Math.random() * 10)));
				tasks.add(task);
			}
			System.out.println("This is the goos that are defending" + defendingGoos.size);
			
			//Splitting
			if(massPerGoo < 70){
				if(largestGoo.getMass() > 50){
					AiTask task = new AiTask();
					task.split(largestGoo);
					tasks.add(task);
				}
			} else {
				if(largestGoo.getMass() > massPerGoo){
					AiTask task = new AiTask();
					task.split(largestGoo);
					tasks.add(task);
				}
			}
			
		}
		
		private void designateHome(){
			float x = 0;
			float y = 0;
			for(int i = 0; i < ownedGoos.size; i++){
				x += ownedGoos.get(i).getX();
				y += ownedGoos.get(i).getY();
			}
			x = x/ownedGoos.size;
			y = y/ownedGoos.size;
			
			homeGeyser = findClosestGeyser(new Vector2(x,y));
		}
		
		private void populateLists(){
			int miningNumber = Math.max((int)Math.ceil(resourcePriority * ownedGoos.size), miningGoos.size);
			int attackingNumber = (int) (attackPriority * ownedGoos.size);
			int defendingNumber = ownedGoos.size - attackingNumber - miningNumber;
			
			Array<Goo> newMiningGoos = getOrderedMiningGoo();
			Array<Goo> newAttackingGoos = getOrderedAttackingGoos();
			Array<Goo> newDefendingGoos = getOrderedDefendingGoos();
			
			for(int i = 0; i < miningNumber; i++){
				
				miningGoos.add(newMiningGoos.first());
				removeId(newMiningGoos.first().getId(), newAttackingGoos);
				removeId(newMiningGoos.first().getId(), newDefendingGoos);
				newMiningGoos.removeIndex(0);
			}
			
			System.out.println("Attacking number" + attackingNumber);
			
			for(int i = 0; i < attackingNumber; i++){
				attackingGoos.add(newAttackingGoos.first());
				removeId(newAttackingGoos.first().getId(), newDefendingGoos);
				newAttackingGoos.removeIndex(0);
			}
			
			for(int i = 0; i < defendingNumber; i++){
				defendingGoos.add(newDefendingGoos.first());
				newDefendingGoos.removeIndex(0);
			}
			
			System.out.println("Defending Goos " + defendingGoos.size);
		}
		
		private void removeId(int id, Array<Goo> array){
			for(int i = 0; i < array.size; i++){
				if(array.get(i).getId() == id){
					array.removeIndex(i);
					break;
				}
			}
		}
		
		private Array<Goo> getOrderedMiningGoo(){
			Array<Goo> orderedMining = new Array<Goo>(ownedGoos);
			Array<Float> values = new Array<Float>();
			
			for(int i = 0; i < orderedMining.size; i++){
				values.add(calculateMiningValue(orderedMining.get(i)));
				System.out.println(values.get(i));
				/*if(values.get(i) == 0){
					orderedMining.removeIndex(i);
					values.removeIndex(i);
				}*/
			}
			
			int k;
			
			
			
			for (int m = values.size; m >= 0; m--) {
	            for (int i = 0; i < values.size - 1; i++) {
	                k = i + 1;
	                
	                if (values.get(i) < values.get(k)) {
	                    values.swap(i, k);
	                    orderedMining.swap(i, k);
	                }
	            }
	           
			}
			
			
			
			return orderedMining;
		}
		
		private Array<Goo> getOrderedAttackingGoos(){
			Array<Goo> orderedAttacking = new Array<Goo>(ownedGoos);
			Array<Float> values = new Array<Float>();
			
			for(int i = 0; i < ownedGoos.size; i++){
				values.add(calculateAttackValue(ownedGoos.get(i)));
			}
			
			int k;
			
			for (int m = values.size; m >= 0; m--) {
	            for (int i = 0; i < values.size - 1; i++) {
	                k = i + 1;
	                if (values.get(i) < values.get(k)) {
	                    values.swap(i, k);
	                    orderedAttacking.swap(i, k);
	                }
	            }
	           
			}
			
			return orderedAttacking;
		}

		private Array<Goo> getOrderedDefendingGoos(){
			Array<Goo> orderedDefending = new Array<Goo>(ownedGoos);
			Array<Float> values = new Array<Float>();
			
			for(int i = 0; i < ownedGoos.size; i++){
				values.add(calculateDefenceValue(ownedGoos.get(i)));
			}
			
			int k;
			
			for (int m = values.size; m >= 0; m--) {
	            for (int i = 0; i < values.size - 1; i++) {
	                k = i + 1;
	                if (values.get(i) < values.get(k)) {
	                    values.swap(i, k);
	                    orderedDefending.swap(i, k);
	                }
	            }
	           
			}
			return orderedDefending;
		}
		
		/*private Goo findBestMiningGoo(){
			float smallestValue = calculateValue(ownedGoos.first());
			int smallestInt = 0;
			
			for(int i = 0; i < ownedGoos.size; i++){
				Goo goo = ownedGoos.get(i);
				
				float value = calculateValue(goo);
				
				if(value < smallestValue){
					smallestValue = value;
					smallestInt = 0;
				}
			}
			return ownedGoos.get(smallestInt);
		}*/
		
		private float calculateMiningValue(Goo goo){
			Geyser temp = null;
			float miningValue = 0;
			
			miningValue += 100/findDistance(goo, homeGeyser);
			
			for(int i = 0; i < geysers.size; i++){
				if(!geysers.get(i).getIsMining()){
					temp = geysers.get(i);
					break;
				}
			}
			
			for(int j = 0; j < geysers.size; j++){
				if(!geysers.get(j).getIsMining()){
					if(new Vector2(goo.getX() - geysers.get(j).getX(),goo.getY() - geysers.get(j).getY()).len2() < new Vector2(goo.getX() - temp.getX(), goo.getY() - temp.getY()).len2()){
						temp = geysers.get(j);
					}
				}	
			}
			
			if(temp == null){
				System.out.println("NULL");
				miningValue = 0;
			} else {
				miningValue += 100/findDistance(temp, goo);
				if(miningValue < 0){
					miningValue = 0;
				}
			}
			
			return miningValue;
		}
		
		private float calculateAttackValue(Goo goo){
			float attackValue = 0;
			attackValue += goo.getMass();

			attackValue -= findClosestEnemyDistanceGoo(goo)/100;
			
			if(attackValue < 0){
				attackValue = 0;
			}
			
			return attackValue * goo.getProperty().getDamage();
		}
		
		private float findClosestEnemyDistanceGoo(Goo goo){
			float smallestDistance = new Vector2(goo.getX() - enemyGoos.first().getX(), goo.getY() - enemyGoos.first().getY()).len();
			float tempDistance = 0;
			for(int i = 0; i < enemyGoos.size; i++){
				tempDistance = new Vector2(goo.getX() - enemyGoos.get(i).getX(), goo.getY() - enemyGoos.get(i).getY()).len();
				if(tempDistance < smallestDistance){
					smallestDistance = tempDistance;
				}
			}
			return smallestDistance;
		}
		
		private float calculateDefenceValue(Goo goo){
			float defenceValue = 0;
			
			defenceValue += goo.getMass();
			
			defenceValue -= findDistance(goo, homeGeyser)/1000;
			
			if(defenceValue < 0){
				defenceValue = 0;
			}
			
			return defenceValue * goo.getProperty().getDefence();
		}
		
		private float findDistance(Entity ent1, Entity ent2){
			return new Vector2(ent1.getX() - ent2.getX(), ent1.getY() - ent2.getY()).len();
		}
		
		private Geyser findBestGeyser(Goo goo){
			Geyser closest = geysers.first();
			
			for(int i = 0; i < geysers.size; i++){
				if(new Vector2(closest.getX()-goo.getX(), closest.getY() - goo.getY()).len2() > new Vector2(geysers.get(i).getX()-goo.getX(), geysers.get(i).getY() - goo.getY()).len2()){
					closest = geysers.get(i);
				}
			}
			return closest;
		}
		
		private Geyser findClosestGeyser(Vector2 position){
			Geyser closest = geysers.first();
			
			for(int i = 0; i < geysers.size; i++){
				if(new Vector2(closest.getX() - position.x, closest.getY() - position.y).len2() > new Vector2(geysers.get(i).getX()-position.x, geysers.get(i).getY() - position.y).len2()){
					closest = geysers.get(i);
				}
			}
			return closest;
		}
		
		private Goo findBestOpponentAttack(Goo goo){
			Goo closest = enemyGoos.first();
			for(int i = 0; i < enemyGoos.size; i++){
				if(new Vector2(closest.getX()-goo.getX(), closest.getY() - goo.getY()).len2() > new Vector2(enemyGoos.get(i).getX()-goo.getX(), enemyGoos.get(i).getY() - goo.getY()).len2()){
					closest = enemyGoos.get(i);
				}
			}
			
			return closest;
		}
	}
}


