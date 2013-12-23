package com.abstrakti.shooter;

import com.abstrakti.shooter.io.GameScreen;
import com.abstrakti.shooter.objects.GameObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Level {
	private final World physicsWorld;
	private TiledMap map;
	
	public Level(String levelname){
		this.physicsWorld = new World(Vector2.Zero, true);		
		TmxMapLoader loader = new TmxMapLoader();
		
		this.map = loader.load(levelname);
		
		GameScreen.getInstance().setMap(map);
	}
	
	public void init(){
//		TiledMapTileLayer layer = (TiledMapTileLayer)this.map.getLayers().get(0);
//		layer.getCell(0,0).getTile().getProperties().containsKey("collision").equals("true") jos true, lisää fysiikka
	}
	
	public void update(){
		this.physicsWorld.step(1/60f, 6, 2);
	}
	
	public void addEntity(GameObject o){
		// this.physicsWorld.createBody()
	}
}
