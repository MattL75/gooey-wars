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
import com.gooeywars.entities.Goo;
import com.gooeywars.entities.Obstacle;
import com.gooeywars.game.Component;
import com.gooeywars.game.GameBox;
import com.gooeywars.game.Main;
import com.gooeywars.pathfinding.Node;
import com.gooeywars.pathfinding.Pathfinder;

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
		Geyser geyser = new Geyser(700,700,0);
		game.addEntity(geyser);
		game.addEntity(new Geyser(700,600,1));
		game.addEntity(new Geyser(700,500,2));
		game.addEntity(new Geyser(700,400,2));

		
		Environment environment = new Environment(game.size.x, game.size.y,Color.LIGHT_GRAY);
		
		
		//environment.addChild(new Obstacle(0,0));
		Obstacle obstacle = new Obstacle(10,10);
		obstacle.getColliders().get(0).getPolygon().getX();
		environment.addChild(obstacle);

		environment.addChild(new Obstacle(120,500));
		environment.addChild(new Obstacle(160,500));
		environment.addChild(new Obstacle(200,500));
		environment.addChild(new Obstacle(240,500));
		
		environment.addChild(new Obstacle(240,500));
		environment.addChild(new Obstacle(240,540));
		environment.addChild(new Obstacle(240,580));
		environment.addChild(new Obstacle(240,620));
		environment.addChild(new Obstacle(240,660));
		environment.addChild(new Obstacle(240,700));

		environment.addChild(new Obstacle(80,700));
		environment.addChild(new Obstacle(120,700));
		environment.addChild(new Obstacle(160,700));
		environment.addChild(new Obstacle(200,700));
		
		
		environment.addChild(new Obstacle(80,580));
		environment.addChild(new Obstacle(80,620));
		environment.addChild(new Obstacle(80,660));
		environment.addChild(new Obstacle(80,700));
		
		environment.addChild(new Obstacle(120,580));
		environment.addChild(new Obstacle(160,580));
		
		environment.addChild(new Obstacle(160,620));
		
		
		game.addEntity(environment);
		Goo goo  = new Goo(15, 500, 20);
		goo.setOwner(0);
		game.addEntity(goo);
		/*
		for(int i = 0; i < 1; i++){
			for(int j = 0; j < 1; j++){
				Goo goo = new Goo(i*60,j*60 + 400,20);
				goo.setOwner(0);
				game.addEntity(goo);
			}
		}
		
		
		/*for(int i = 0; i < 1; i++){
			for(int j = 0; j < 1; j++){
				Goo goo = new Goo(i*60+400,j*60 + 400,20);
				goo.setColorInt(1);
				goo.setOwner(1);
				game.addEntity(goo);
			}
		}*/
		
		
		
		//Pathfind testing
		/*Pathfinder finder = new Pathfinder(Main.findGameBox("game").getGrid());

		Array<Node> path = finder.findPath(new Vector2(40, 0), new Vector2(60, 450));
		Pixmap pix = new Pixmap(20, 20, Format.RGB888);
		pix.setColor(Color.RED);
		pix.fill();
		Texture tex = new Texture(pix);
		pix.dispose();
		for (int i = 0; i < path.size; i++) {
			game.addEntity(new Entity(new Sprite(tex), path.get(i).getWorldPos().x, path.get(i).getWorldPos().y));

		}*/

		
		
	}

	@Override
	public void update() {
		
	}
}
