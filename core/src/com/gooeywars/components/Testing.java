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
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				Goo goo = new Goo(i*60,j*60 + 400,40);
				goo.setOwner(1);
				game.addEntity(goo);
			}
		}
		
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				Goo goo = new Goo(i*60+400,j*60 + 400,40);
				goo.setColorInt(1);
				goo.setOwner(2);
				game.addEntity(goo);
			}
		}
		
		Pathfinder finder = new Pathfinder(Main.findGameBox("game").getGrid());
		Array<Node> path = finder.findPath(new Vector2(40, 0), new Vector2(40, 600));
		Pixmap pix = new Pixmap(20, 20, Format.RGB888);
		pix.setColor(Color.RED);
		pix.fill();
		Texture tex = new Texture(pix);
		pix.dispose();
		for (int i = 0; i < path.size; i++) {
			game.addEntity(new Entity(new Sprite(tex), path.get(i).getWorldPos().x, path.get(i).getWorldPos().y));
		}

		
		Geyser geyser = new Geyser(700,700);
		game.addEntity(geyser);
		
		Environment environment = new Environment(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),Color.LIGHT_GRAY);
		environment.addChild(new Obstacle(40, 400));
		game.addEntity(environment);
	}

	@Override
	public void update() {
		
	}
}
