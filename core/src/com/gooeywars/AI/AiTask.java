package com.gooeywars.AI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.components.MoveHandler;
import com.gooeywars.entities.Goo;
import com.gooeywars.game.Main;

public class AiTask {
	Array<Goo> movingGoos;
	Vector2 destination;
	MoveHandler mover;
	
	boolean moveTask;
	boolean attackTask;
	
	public AiTask(){
		mover = Main.findGameBox("game").getMover();
	}
	
	public void move(Array<Goo> goos, Vector2 position){
		moveTask = true;
		this.movingGoos = goos;
		destination = position;
	}
	
	public void execute(){
		if(moveTask){
			mover.move(movingGoos, destination);
		}
		if(attackTask){
			
		}
	}
}
