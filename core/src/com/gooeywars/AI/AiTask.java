package com.gooeywars.AI;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.components.MoveHandler;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Main;

public class AiTask {
	MoveHandler mover;
	
	Vector2 destination;
	
	boolean moveTask;
	Array<Goo> movingGoos;
	
	
	boolean specialMoveTask;
	Array<Vector2> destinations;
	
	boolean attackTask;
	Array<Goo> attackingGoos;
	
	
	boolean splitTask;
	Goo splittingGoo;
	
	boolean executed;
	
	public AiTask(){
		mover = Main.findGameBox("game").getMover();
	}
	
	public void move(Array<Goo> goos, Vector2 position){
		moveTask = true;
		this.movingGoos = goos;
		destination = position;
	}
	
	public void move(Array<Goo> goos, Array<Vector2> positions){
		specialMoveTask = true;
		this.movingGoos = goos;
		destinations = positions;
	}
	
	public void attack(Array<Goo> goos, Vector2 position){
		attackTask = true;
		attackingGoos = goos;
		destination = position;
	}
	
	public void split(Goo goo){
		splitTask = true;
		splittingGoo = goo;
	}
	
	public void execute(){
		if(!executed){
			executed = true;
			if(moveTask){
				mover.move(movingGoos, destination);
			}
			if(specialMoveTask){
				mover.move(movingGoos, destinations);
			}
			if(attackTask){
				mover.attack(attackingGoos, destination);
			}
			if(splitTask){
				splittingGoo.split(new Vector2().setToRandomDirection());
			}
		}
		
	}
}
