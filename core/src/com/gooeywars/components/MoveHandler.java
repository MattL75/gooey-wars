package com.gooeywars.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Component;
import com.gooeywars.pathfinding.Node;
import com.gooeywars.pathfinding.Pathfinder;

public class MoveHandler extends Component{
	Array<Goo> movingGoos;
	Array<Array<Node>> paths;
	Pathfinder finder;
	
	
	@Override
	public void create() {
		movingGoos = new Array<Goo>();
		finder = new Pathfinder();
		paths = new Array<Array<Node>>();
		movingGoos = new Array<Goo>();
	}

	@Override
	public void update() {
		for(int i = 0; i < movingGoos.size; i++){
			displaceGoo(i);
		}
	}
	
	public void displaceGoo(int index){
		
		Array<Node> path = paths.get(index);
		Goo goo = movingGoos.get(index);
		
		Vector2 destination = new Vector2(path.first().getWorldPos().x, path.first().getWorldPos().y);
		
		goo.setVelocity(new Vector2(destination.x - goo.getX(), destination.y - goo.getY()));
		
		if(Math.abs(destination.x - goo.getX()) < 10 && Math.abs(destination.x - goo.getX()) < 10){
			if(path.size > 0){
				path.removeIndex(0);
				path.shrink();
			} else {
				destinationReached(index);
			}
			
		}
	}
	
	public void destinationReached(int index){
		movingGoos.removeIndex(index);
		paths.removeIndex(index);
	}
	
	public void merge(Array<Goo> mergingGoos){
		
	}
	
	public void move(Goo goo, Vector2 finalPos){
		System.out.println(finder == null);
		Array<Node> path = finder.findPath(new Vector2(goo.getX(), goo.getY()), finalPos, goo.getGrid());
		movingGoos.add(goo);
		paths.add(path);
	}
	
	public void cancel(Goo goo){
		
	}
}
