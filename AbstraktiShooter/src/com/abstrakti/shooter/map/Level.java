package com.abstrakti.shooter.map;

import java.util.ArrayList;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.io.AiController;
import com.abstrakti.shooter.io.GameScreen;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.GameObjectFactory;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.Wall;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Level {

	
	private final World physicsWorld;
	private Player player;
	private TiledMap map;
	private ArrayList<AiController> enemies;
	
	public Level(String levelname){
		this.physicsWorld = new World(Vector2.Zero, true);	
		TmxMapLoader loader = new TmxMapLoader();
		
		this.map = loader.load(levelname);
		
		GameScreen.getInstance().setMap(map);
		this.enemies = new ArrayList<AiController>();
	}
	
	public void init(){
		TiledMapTileLayer layer = (TiledMapTileLayer)this.map.getLayers().get(0);
		
		
		int tileSize = Config.TILE_SIZE;
		
		for (int i=0;i<layer.getWidth();i++) {
			for (int j=0;j<layer.getHeight();j++) {
			
				Cell cell = layer.getCell(i,j);
				if (cell==null) {
					continue;
				}
				
				if (cell.getTile().getProperties().get("collision").equals("true") ) {								
					Vector2 position = new Vector2(tileSize*i*Config.WORLD_TO_BOX, tileSize*j*Config.WORLD_TO_BOX);
					GameObjectFactory.createWall(physicsWorld, position);
				}
				
			}
		}
		//enemies need to be spawned first
		this.spawnEnemies(); 
		this.setPlayerIntoLevel();
	}
	
	private void setPlayerIntoLevel(){
		MapLayer spawnLayer = map.getLayers().get("Spawns");
		MapObjects mapObjects = spawnLayer.getObjects();		
		MapObject mapObject = mapObjects.get("playerSpawn");
		MapProperties properties = mapObject.getProperties();
		int x = (Integer)properties.get("x");
		int y = (Integer)properties.get("y");
		
		this.player = GameObjectFactory.createPlayer(physicsWorld);
		this.player.setPosition(x, y);
		
		GameScreen.getInstance().lockCameraOn(this.player);
		for (AiController ai : enemies) {
			ai.setPlayer(this.player);
		}
	}
	
	private void spawnEnemies(){
		MapLayer spawnLayer = map.getLayers().get("Spawns");
		MapObjects mapObjects = spawnLayer.getObjects();		
		for (MapObject mapObj : mapObjects) {
			MapProperties properties = mapObj.getProperties();
			String type = (String)properties.get("type");
			if (type.equals("guardSpawn")){
				int x = (Integer)properties.get("x");
				int y = (Integer)properties.get("y");
				Player guard = GameObjectFactory.createPlayer(physicsWorld);
				guard.setPosition(x, y);
				AiController ai = new AiController(guard);
				enemies.add(ai);
			}
		}
	}
	
	public ArrayList<AiController> getEnemies() {
		return this.enemies;
	}
	
	public void update(float deltaTime){
		Array<Body> bodies = new Array<Body>();
		this.physicsWorld.getBodies(bodies);
		
		for (Body body: bodies) {
			GameObject obj = (GameObject) body.getUserData();
			if (obj!=null) {
				if (Wall.class.isInstance(obj)) {
					continue;
				}
				
				obj.update(deltaTime);
			}
		}
		this.physicsWorld.step(deltaTime, 6, 2);
	}
	
	public World getWorld() {
		return physicsWorld;
	}
	
	public Player getPlayer() {
		return this.player;
	}
}
