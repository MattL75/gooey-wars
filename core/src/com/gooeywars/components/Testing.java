package com.gooeywars.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gooeywars.entities.Entity;
import com.gooeywars.entities.Environment;
import com.gooeywars.entities.Geyser;
import com.gooeywars.entities.GeyserProperty;
import com.gooeywars.entities.Goo;
import com.gooeywars.entities.Obstacle;
import com.gooeywars.game.Component;
import com.gooeywars.game.GameBox;
import com.gooeywars.game.Main;
import com.gooeywars.pathfinding.Node;
import com.gooeywars.pathfinding.Pathfinder;
import com.sun.xml.internal.ws.db.glassfish.BridgeWrapper;

public class Testing extends Component{
	
	private Sprite sprite;
	private Texture texture;
	private Entity ent;
	private Entity ent2;
	float mod = 0;
	
	private Array<Entity> entities;
	private GameBox game;
	
	@Override
	public void create() {
		
		game = Main.findGameBox("game");
		
		Environment environment = new Environment(game.size.x, game.size.y,Color.LIGHT_GRAY);
		
		game.addEntity(new Geyser(game.size.x/2, game.size.y/2,GeyserProperty.URANIUM));
		game.addEntity(new Geyser(game.size.x/2, game.size.y/2+ 70, GeyserProperty.OXYGEN));
		

		game.addEntity(new Geyser(2400, 200, 1));
		game.addEntity(new Geyser(150, (int)game.size.y - 200, 2));
		
		game.addEntity(new Geyser(300, 300));
		game.addEntity(new Geyser(game.size.x-300, game.size.y - 400,0));
		
		environment.addChild(new Obstacle(120,500));
		environment.addChild(new Obstacle(160,500));
		environment.addChild(new Obstacle(200,500));
		environment.addChild(new Obstacle(240,500));
		
		environment.addChild(new Obstacle(400,500));
		environment.addChild(new Obstacle(400,460));
		environment.addChild(new Obstacle(400,420));
		environment.addChild(new Obstacle(400,380));
		environment.addChild(new Obstacle(400,340));
		environment.addChild(new Obstacle(400,300));
		
		environment.addChild(new Obstacle(2300, 0));
		environment.addChild(new Obstacle(2300, 40));
		environment.addChild(new Obstacle(2300, 80));
		
		environment.addChild(new Obstacle(2300, 260));
		environment.addChild(new Obstacle(2300, 300));
		environment.addChild(new Obstacle(2300, 340));
		environment.addChild(new Obstacle(2300, 380));
		
		environment.addChild(new Obstacle(2340, 380));
		environment.addChild(new Obstacle(2380, 380));
		environment.addChild(new Obstacle(2420, 380));
		environment.addChild(new Obstacle(2460, 380));
		environment.addChild(new Obstacle(2500, 380));
		environment.addChild(new Obstacle(2540, 380));
		environment.addChild(new Obstacle(2580, 380));
		environment.addChild(new Obstacle(2620, 380));
		
		environment.addChild(new Obstacle(400, (int)game.size.y - 80));
		environment.addChild(new Obstacle(400, (int)game.size.y - 120));
		environment.addChild(new Obstacle(400, (int)game.size.y - 160));
		environment.addChild(new Obstacle(400, (int)game.size.y - 200));
		environment.addChild(new Obstacle(400, (int)game.size.y - 240));
		environment.addChild(new Obstacle(400, (int)game.size.y - 280));
		environment.addChild(new Obstacle(400, (int)game.size.y - 320));
		environment.addChild(new Obstacle(400, (int)game.size.y - 360));
		
		
		
		
		
		
		//environment.addChild(new Obstacle(160,620));
		
		
		game.addEntity(environment);
		
		
		Goo goo = new Goo(game.size.x-200, game.size.y - 200);
		goo.setColorInt(1);
		goo.setOwner(1);
		game.addEntity(goo);
		
		
		game.addEntity(new Geyser(game.size.x-300, game.size.y - 300,0));
		
		Goo goo2 = new Goo(200, 200);
		goo2.setColorInt(0);
		goo2.setOwner(0);
		game.addEntity(goo2);
	}

	@Override
	public void update() {
		
	}
}
