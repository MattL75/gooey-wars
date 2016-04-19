package com.gooeywars.pathfinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Main;

public class MoveHandler {
	ExecutorService executor;
	Future<?> future;
	Array<moveCalc> runningThreads;
	Grid grid;
	public MoveHandler(){
		executor = Executors.newCachedThreadPool();
		runningThreads = new Array<moveCalc>();
		
	}
	
	public void move(Goo goo, Vector2 finalPos){
		moveCalc calc = new moveCalc(goo, finalPos, goo.getGrid());
		runningThreads.add(calc);
		future = executor.submit(calc);
	}
	
	
	
	
	public void cancel(Goo goo){
		for(int i = 0; i < runningThreads.size; i++){
			if(runningThreads.get(i).goo.getId() == goo.getId()){
				runningThreads.get(i).cancel();
			}
		}
	}
	
	public void merge(Array<Goo> mergingGoos){
		System.out.println(mergingGoos.size);
		float totalX = 0;
		float totalY = 0;
		for(int i = 0; i < mergingGoos.size; i++){
			mergingGoos.get(i).setMerging(true);
			totalX += mergingGoos.get(i).getX();
			totalY += mergingGoos.get(i).getY();	
		}
		
		Vector2 joinPoint = new Vector2(totalX/mergingGoos.size, totalY/mergingGoos.size);
		
		for(int i = 0; i < mergingGoos.size; i++){
			Main.findGameBox("game").getMover().cancel(mergingGoos.get(i));
			Main.findGameBox("game").getMover().move(mergingGoos.get(i), joinPoint);
		}
	}
}

class moveCalc implements Runnable  {
	Vector2 initialPos;
	Vector2 finalPos;
	Array<Node> pathNode;
	Array<Vector2> path;
	boolean isCanceled;
	
	public Goo goo;
	Pathfinder finder;
	
	public  moveCalc(Goo goo, Vector2 finalPos, Grid grid) {
		this.finalPos = finalPos;
		this.goo = goo;
		finder = new Pathfinder(grid);
	}
	
	@Override
	public void run() {
		initialPos = new Vector2(goo.getX(), goo.getY());
		pathNode = finder.findPath(initialPos, finalPos, goo.getGrid());
		
		//path = new Array<Vector2>();
	//	System.out.println(pathNode.size);
		
		Vector2 currentDestination;
		boolean destinationReached = false;
		//System.out.println("Moving...");
		for(int i = 0; i < pathNode.size - 1; i++){
			
			if(isCanceled){
				//System.out.println("Is canceling");
				break;
			}
			
			if(goo.getMass() < Goo.SMALLEST_MASS){
				break;
			}
			float radius = goo.getGrid().getNodeRadius() -1;
			
			currentDestination = new Vector2(pathNode.get(i+1).getWorldPos().x , pathNode.get(i+1).getWorldPos().y);
		//	System.out.println("Going to " + i + "...");
			while(!destinationReached){
				if(isCanceled){
					break;
				}
				goo.setVelocity(new Vector2(currentDestination.x - goo.getX(), currentDestination.y - goo.getY()).clamp(0, 20));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				if(Math.abs(goo.getX() - currentDestination.x) < 10 && Math.abs(goo.getY() - currentDestination.y) < 10){
					destinationReached = true;
				}
			}
			destinationReached = false;
			
			if(i == pathNode.size - 2){
		//		System.out.println("final node");
				while(!destinationReached){
					if(isCanceled){
						break;
					}
					goo.setVelocity(new Vector2(finalPos.x - (goo.getX()+goo.getWidth()/2), finalPos.y - (goo.getY()+goo.getHeight()/2)).clamp(0, 20));
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					if(Math.abs(goo.getX()+goo.getWidth()/2 - finalPos.x) < 1 && Math.abs(goo.getY() + goo.getHeight()/2 - finalPos.y) < 1){
						destinationReached = true;
					}
				}
				destinationReached = false;
			}
		}
		
	//	System.out.println("Destination reached!");
		
		goo.setMerging(false);
		
		//Do moveVector in the form of physics
	}
	
	public void cancel(){
		isCanceled = true;
	}
}
