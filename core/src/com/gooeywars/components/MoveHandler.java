package com.gooeywars.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.pathfinding.Node;
import com.gooeywars.pathfinding.Pathfinder;
import com.gooeywars.pathfinding.PathfinderStatic;

public class MoveHandler extends Component{
	Array<Goo> movingGoos;
	Array<Array<Node>> paths;
	Pathfinder finder;
	Array<Integer> reached;
	
	
	@Override
	public void create() {
		movingGoos = new Array<Goo>();
		finder = new Pathfinder();
		paths = new Array<Array<Node>>();
		movingGoos = new Array<Goo>();
		reached = new Array<Integer>();
	}

	@Override
	public void update() {
		for(int i = 0; i < movingGoos.size; i++){
			displaceGoo(i);
		}
		//allReachedCancel();
	}
	
	public void displaceGoo(int index){
		
		Array<Node> path = paths.get(index);
		Goo goo = movingGoos.get(index);
		
		if(path == null || path.size < 1){
			return;
		}
		
		Vector2 destination = new Vector2(path.first().getWorldPos().x, path.first().getWorldPos().y);
		
		goo.setVelocity(new Vector2(destination.x - goo.getX(), destination.y - goo.getY()));
		
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
	
	public void allReachedCancel(){
		for(int i = 0; i < reached.size; i++){
			int index = reached.get(i);
			movingGoos.get(index).stopEntity();
			movingGoos.removeIndex(index);
			paths.removeIndex(index);
		}
		reached.clear();
	}
	
	public void destinationReached(int index){
		movingGoos.get(index).stopEntity();
		movingGoos.removeIndex(index);
		paths.removeIndex(index);
		//reached.add(index);
	}
	
	public void merge(Array<Goo> mergingGoos){
		
	}
	
	public void move(Goo goo, Vector2 finalPos){
		Array<Node> path = PathfinderStatic.findPath(new Vector2(goo.getX(), goo.getY()), finalPos, goo.getGrid());
		movingGoos.add(goo);
		paths.add(path);
	}
	
	public void cancel(Goo goo){
		System.out.println("Canceling goo " + goo.getId());
		for(int i = 0; i < movingGoos.size; i++){
			if(movingGoos.get(i).getId() == goo.getId()){
				destinationReached(i);
				break;
			}
		}
	}
}
