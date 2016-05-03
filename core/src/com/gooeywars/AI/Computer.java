package com.gooeywars.AI;

import java.security.KeyStore.LoadStoreParameter;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap.Values;
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
	}

	@Override
	public void create() {
		Thread thread = new Thread(new AiRunnable());
		alive = true;
		update();
		thread.start();
	}
	
	@Override
	public void update() {
		if(alive){
			ownedGoos.clear();
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
				if(end - begin > 1000){
					calculatePriorities();
					populateLists();
					determineActions();
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
			
			if(totalMass >= 100){
				resourcePriority = 0.333f;
				attackPriority = 0.333f;
				defencePriority = 0.333f;
			}
			
			if(ownedGoos.size > 10 && massPerGoo > 40){
				resourcePriority = 0.1f;
				attackPriority = 0.6f;
				defencePriority = 0.2f;
			}
		}
		
		private void determineActions(){
			//Displacement			
			if(resourcePriority > 0.95){
				Array<Vector2> destinations = new Array<Vector2>();
				Geyser closestGeyser;
				Array<Goo> moving = new Array<Goo>();
				for(int i = 0; i < ownedGoos.size; i++){
					closestGeyser = findBestGeyser(ownedGoos.get(i));
					destinations.add( new Vector2(closestGeyser.getX()+closestGeyser.getWidth()/2,closestGeyser.getY()+closestGeyser.getHeight()/2));
					moving.add(ownedGoos.get(i));
				}
				AiTask task = new AiTask();
				task.move(moving, destinations);
				tasks.add(task);
			}
			if(resourcePriority < 0.5){
				if(attackPriority > 0.3){
					System.out.println("Attacking");
					for(int i = 0; i < attackingGoos.size; i++){
						AiTask task = new AiTask();
						Goo enemyGoo = findBestOpponentAttack(attackingGoos.get(i));
						
						task.attack(attackingGoos.get(i), new Vector2(enemyGoo.getX(),enemyGoo.getY()));
						tasks.add(task);
					}
					
				}
			}
			
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
			int miningNumber = (int)Math.ceil(resourcePriority * ownedGoos.size);
			int attackingNumber = (int) attackPriority * ownedGoos.size;
			int defendingNumber = ownedGoos.size - attackingNumber - miningNumber;
			
			Array<Goo> newMiningGoos = getOrderedMiningGoo();
			Array<Goo> newAttackingGoos = new Array<Goo>();
			Array<Goo> newDefendingGoos = new Array<Goo>();
			
			System.out.println("Mining goo size " + newMiningGoos.size);
			System.out.println("Mining number " + miningNumber);
			for(int i = 0; i < miningNumber; i++){
				miningGoos.add(newMiningGoos.first());
				newMiningGoos.removeIndex(0);
			}
			
			attackingGoos = new Array<Goo>(newMiningGoos);
			System.out.println("Attacking Goos " + attackingGoos.size);
			
			for(int i = 0; i < attackingNumber; i++){
			
			}
			
			for(int i = 0; i < defendingNumber; i++){
				
			}
		}
		
		private Array<Goo> getOrderedMiningGoo(){
			System.out.println("Getting Ordered");
			Array<Goo> orderedMining = new Array<Goo>(ownedGoos);
			Array<Float> values = new Array<Float>();
			
			for(int i = 0; i < ownedGoos.size; i++){
				values.add(calculateMiningValue(ownedGoos.get(i)));
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
			
			for(int i = 0; i < orderedMining.size; i++){
			}
			
			return orderedMining;
		}
		
		private Array<Goo> getOrderedAttackingGoos(){
			Array<Goo> orderedAttacking = new Array<Goo>();
			
			return orderedAttacking;
		}

		private Array<Goo> getOrderedDefendingGoos(){
			Array<Goo> orderedDefending = new Array<Goo>();
			
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
			
			miningValue += 1000/findDistance(goo, homeGeyser);
			
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
			
			miningValue -= findDistance(temp, goo)/1000;
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
		
		private float calculateDefenseValue(Goo goo){
			float defenceValue = 0;
			
			defenceValue += goo.getMass();
			
			defenceValue -= findDistance(goo, homeGeyser)/100;
			
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


