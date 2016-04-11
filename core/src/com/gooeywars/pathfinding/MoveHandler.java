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
	Array<Future<?>> runningThreads;
	
	public MoveHandler(){
		executor = Executors.newCachedThreadPool();
		runningThreads = new Array<Future<?>>();
	}
	
	public void move(Goo goo, Vector2 finalPos){
		Runnable calc = new moveCalc(goo, finalPos);
		
		future = executor.submit(calc);
	}
	
}

class moveCalc implements Runnable {
	Vector2 initialPos;
	Vector2 finalPos;
	Array<Node> pathNode;
	Array<Vector2> path;
	
	Goo goo;
	Pathfinder finder;
	
	public  moveCalc(Goo goo, Vector2 finalPos) {
		this.finalPos = finalPos;
		this.goo = goo;
		finder = new Pathfinder(Main.findGameBox("game").getGrid());
	}
	
	@Override
	public void run() {
		initialPos = new Vector2(goo.getX(), goo.getY());
		
		pathNode = finder.findPath(initialPos, finalPos);
		path = new Array<Vector2>();
		System.out.println(pathNode.size);
		
		Vector2 currentDestination;
		boolean destinationReached = false;
		System.out.println("Moving...");
		for(int i = 0; i < pathNode.size - 1; i++){
			if(goo.getMass() < Goo.SMALLEST_MASS){
				break;
			}
			currentDestination = pathNode.get(i+1).getWorldPos();
			System.out.println("Going to " + i + "...");
			while(!destinationReached){
				
				goo.setVelocity(new Vector2(currentDestination.x - goo.getX(), currentDestination.y - goo.getY()));
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				if(Math.abs(goo.getX() - currentDestination.x) < 5 && Math.abs(goo.getY() - currentDestination.y) < 5){
					destinationReached = true;
				}
			}
			destinationReached = false;
			
			if(i == pathNode.size - 2){
				System.out.println("final node");
				while(!destinationReached){
					
					goo.setVelocity(new Vector2(finalPos.x - (goo.getX()), finalPos.y - (goo.getY())));
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					if(Math.abs(goo.getX() - finalPos.x) < 1 && Math.abs(goo.getY() - finalPos.y) < 1){
						destinationReached = true;
					}
				}
				destinationReached = false;
			}
		}
		System.out.println("Destination reached!");
		
		
		
		//Do moveVector in the form of physics
	}
}
