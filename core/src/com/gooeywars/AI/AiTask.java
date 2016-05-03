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
	
	boolean singularMoveTask;
	Goo movingGoo;
	
	boolean specialMoveTask;
	Array<Vector2> destinations;
	
	boolean attackTask;
	Array<Goo> attackingGoos;
	
	boolean singularAttackTask;
	Goo attackingGoo;
	
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
	
	public void move(Goo goo, Vector2 position){
		singularMoveTask = true;
		movingGoo = goo;
		destination = position;
	}
	
	public void attack(Array<Goo> goos, Vector2 position){
		attackTask = true;
		attackingGoos = goos;
		destination = position;
	}
	
	public void attack(Goo goo, Vector2 position){
		singularAttackTask = true;
		attackingGoo = goo;
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
			if(singularMoveTask){
				mover.move(movingGoo, destination);
			}
			if(attackTask){
				mover.attack(attackingGoos, destination);
			}
			if(singularAttackTask){
				mover.attack(attackingGoo, destination);
			}
			if(splitTask){
				splittingGoo.split(new Vector2().setToRandomDirection());
			}
		}
		
	}
}
