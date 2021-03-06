package com.gooeywars.components;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.game.Main;
import com.gooeywars.pathfinding.Node;
import com.gooeywars.pathfinding.Pathfinder;
import com.gooeywars.pathfinding.PathfinderStatic;

public class MoveHandler extends Component{
	Array<Goo> movingGoos;
	Array<Array<Node>> paths;
	Pathfinder finder;
	Array<Integer> reached;
	
	Executor executor;
	
	
	@Override
	public void create() {
		movingGoos = new Array<Goo>();
		finder = new Pathfinder();
		paths = new Array<Array<Node>>();
		movingGoos = new Array<Goo>();
		reached = new Array<Integer>();
		executor = Executors.newCachedThreadPool();
		System.out.println("Executing");
		
	}

	@Override
	public void update() {
		System.out.println(paths.size);
		for(int i = 0; i < movingGoos.size; i++){
			displaceGoo(i);
		}
		//allReachedCancel();
	}
	
	public void displaceGoo(int index){
		
		Array<Node> path = paths.get(index);
		Goo goo = movingGoos.get(index);
		
		if(!goo.getProperty().isImmobilized()){
			if(path == null || path.size < 1){
				return;
			}
			
			Vector2 destination = new Vector2(path.first().getWorldPos().x, path.first().getWorldPos().y);
			
			Vector2 variation = new Vector2(destination.x-goo.getX(),destination.y-goo.getY());
			variation.clamp(5f, 5f);
			
			goo.setPosition(goo.getX() + variation.x, goo.getY() + variation.y);
			//goo.setVelocity(new Vector2(destination.x - goo.getX(), destination.y - goo.getY()));
			
			if(Math.abs(destination.x - goo.getX()) < 10 && Math.abs(destination.y - goo.getY()) < 10){
				if(path.size > 0){
					path.removeIndex(0);
					if(path.size < 1){
						destinationReached(index);
					}
				} else {
					destinationReached(index);
				}
				
			}
		}
		
		
	}
	
	public void destinationReached(int index){
		movingGoos.get(index).stopEntity();
		movingGoos.removeIndex(index);
		paths.removeIndex(index);
		
	}
	
	public void merge(Array<Goo> mergingGoos){

		float totalX = 0;
		float totalY = 0;
		for(int i = 0; i < mergingGoos.size; i++){
			mergingGoos.get(i).setMerging(true);
			totalX += mergingGoos.get(i).getX();
			totalY += mergingGoos.get(i).getY();	
		}
		
		Vector2 joinPoint = new Vector2(totalX/mergingGoos.size, totalY/mergingGoos.size);
		Goo finalGoo = new Goo(joinPoint.x, joinPoint.y,0);
		Main.findGameBox("game").addEntity(finalGoo);

		for(int i = 0; i < mergingGoos.size; i++){
			Goo goo = mergingGoos.get(i);
			
			move(goo, joinPoint);
		}
	}
	
	public void move(Goo goo, Vector2 finalPos){
		if(!goo.getProperty().isImmobilized()){
			Array<Node> path = PathfinderStatic.findPath(new Vector2(goo.getX(), goo.getY()), finalPos, goo.getGrid());
			cancel(goo);
			movingGoos.add(goo);
			paths.add(path);
		}
	}
	
	public void move(Array<Goo> goos, Vector2 finalPos){
		int size = (int)Math.ceil(Math.sqrt(goos.size));
		int count = 0;
		
		for(int i = 0; i < goos.size; i++){
			cancel(goos.get(i));
		}
		
		if(goos.size == 1){
			cancel(goos.get(count));
			executor.execute(new pathCalculationTask(goos.get(count), new Vector2(finalPos.x ,finalPos.y )));
		} else {
			for(int i = 0; i < size; i++){
				for(int j = 0; j < size; j++){
					if(count < goos.size){
						
						
						
						executor.execute(new pathCalculationTask(goos.get(count), new Vector2(finalPos.x + i * goos.get(count).getRadius() * 2 ,finalPos.y + j * goos.get(count).getRadius() * 2 )));
						//offSet = goos.get(count).getRadius();
						count++;
					}
				}
			}
		}
		
	}
	
	public void move(Array<Goo> goos, Array<Vector2> finalPositions){
		for(int i = 0; i < goos.size; i++){
			cancel(goos.get(i));
			executor.execute(new pathCalculationTask(goos.get(i), finalPositions.get(i)));
		}
	}
	
	public void attack(Goo goo, Vector2 finalPos){
		cancel(goo);
		executor.execute(new pathCalculationTask(goo, finalPos));
		
	}
	
	public void attack(Array<Goo> goos, Vector2 finalPos){
		
	}
	
	public void cancel(Goo goo){
		for(int i = 0; i < movingGoos.size; i++){
			if(movingGoos.get(i).getId() == goo.getId()){
				destinationReached(i);
				break;
			}
		}
	}
	
	class pathCalculationTask implements Runnable{
		Goo goo;
		Vector2 destination;
		
		public pathCalculationTask(Goo goo, Vector2 destination) {
			this.goo = goo;
			this.destination = destination;
		}
		
		@Override
		public void run() {
			paths.add(PathfinderStatic.findPath(new Vector2(goo.getX(), goo.getY()), destination, goo.getGrid()));
			movingGoos.add(goo);
		}
		
	}
}
