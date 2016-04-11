package com.gooeywars.pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.gooeywars.entities.Goo;

public class MoveHandler {

	public MoveHandler(Vector2 finalPos, Goo goo) {
		Runnable calc = new moveCalc(finalPos, goo);
		Thread thread = new Thread(calc);
		
		thread.start();
	}
}

class moveCalc implements Runnable {
	Vector2 finalPos;
	Goo goo;
	
	public  moveCalc(Vector2 finalPos, Goo goo) {
		this.finalPos = finalPos;
		this.goo = goo;
	}
	
	@Override
	public void run() {
		Vector2 initialPos = new Vector2(goo.getX(), goo.getY());
		Vector2 moveVector = new Vector2(finalPos.x - initialPos.x, finalPos.y - initialPos.y);
		
		//Do moveVector in the form of physics
	}
}
