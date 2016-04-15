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
	public MoveHandler(Grid grid){
		executor = Executors.newCachedThreadPool();
		runningThreads = new Array<moveCalc>();
		this.grid = grid;
	}
	
	public void move(Goo goo, Vector2 finalPos){
		moveCalc calc = new moveCalc(goo, finalPos, grid);
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
		
		pathNode = finder.findPath(initialPos, finalPos, new Grid(new Vector2(), 10));
		path = new Array<Vector2>();
		System.out.println(pathNode.size);
		
		Vector2 currentDestination;
		boolean destinationReached = false;
		System.out.println("Moving...");
		for(int i = 0; i < pathNode.size - 1; i++){
			
			if(isCanceled){
				System.out.println("Is canceling");
				break;
			}
			
			if(goo.getMass() < Goo.SMALLEST_MASS){
				break;
			}
			currentDestination = pathNode.get(i+1).getWorldPos();
			System.out.println("Going to " + i + "...");
			while(!destinationReached){
				if(isCanceled){
					break;
				}
				goo.setVelocity(new Vector2(currentDestination.x - goo.getX(), currentDestination.y - goo.getY()));
				try {
					Thread.sleep(100);
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
					if(isCanceled){
						break;
					}
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
	
	public void cancel(){
		isCanceled = true;
	}
}
