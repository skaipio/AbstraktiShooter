package com.abstrakti.shooter;

import java.util.List;

import com.abstrakti.shooter.io.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Level {
	private final World physicsWorld;
	private TiledMap map;
	private List<Entity> entities;
	
	public Level(String levelname){
		this.physicsWorld = new World(Vector2.Zero, true);		
		TmxMapLoader loader = new TmxMapLoader();
		
		this.map = loader.load(levelname);
		
		GameScreen.getInstance().setMap(map);
		
//		this.physicsWorld.createBody(new BodyDef());
//		BodyDef def = new BodyDef();
	}
	
	public void update(){
		this.physicsWorld.step(1/60f, 6, 2);
	}
	
	public void addEntity(Entity e){
		this.entities.add(e);
	}
}
