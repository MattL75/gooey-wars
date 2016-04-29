package com.gooeywars.AI;

import java.security.KeyStore.PrivateKeyEntry;

import com.badlogic.gdx.utils.Array;
import com.gooeywars.game.Component;
import com.gooeywars.game.GameBox;
import com.sun.jmx.snmp.tasks.TaskServer;
import com.sun.org.apache.bcel.internal.generic.ATHROW;

public class Computer extends Component{
	private int playerId;
	private GameBox gamebox;
	private Array<AiTask> tasks;
	
	public Computer(GameBox gamebox){
		playerId = 1;
		this.gamebox = gamebox;
	}

	@Override
	public void create() {
		Thread thread = new Thread(new AiRunnable());
		thread.start();
	}

	@Override
	public void update() {
		for(int i = 0; i < tasks.size; i++){
			tasks.get(i).execute();
		}
	}
	
	class AiRunnable implements Runnable{
		float resourcePriority;
		float defencePriority;
		float attackPriority;
		
		@Override
		public void run() {
			
		}
		
	}
}


