package com.abstrakti.shooter.map;

import java.util.ArrayList;
import java.util.Iterator;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.io.AiController;
import com.abstrakti.shooter.io.GameScreen;
import com.abstrakti.shooter.objects.Ammunition;
import com.abstrakti.shooter.objects.Bullet;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.GameObjectFactory;
import com.abstrakti.shooter.objects.Medpack;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.Wall;
import com.abstrakti.shooter.states.PlayerState;
import com.abstrakti.shooter.triggers.Trigger;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
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
		GameObjectFactory.world = this.physicsWorld;
		
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
				MapProperties cellProperties = cell.getTile().getProperties();
				
				if (cellProperties.get("collision").equals("true") ) {								
					Vector2 position = new Vector2(tileSize/2*Config.WORLD_TO_BOX+tileSize*i*Config.WORLD_TO_BOX, tileSize/2*Config.WORLD_TO_BOX+tileSize*j*Config.WORLD_TO_BOX);
					GameObjectFactory.createWall(physicsWorld, position);
				}
				if (cellProperties.get("tag").equals("concrete_floor") ) {								
					cell.setTile(TileFactory.createConcreteFloorTile());
				}
				if (cellProperties.get("tag").equals("concrete_wall_upper") ) {								
					cell.setTile(TileFactory.createConcreteUpperWallTile());
				}
			}
		}
		//enemies need to be spawned first
		this.spawnEnemies(); 
		this.setPlayerIntoLevel();
		this.registerEndOfLevel();
		this.setDoors();
	}
	
	private void setDoors() {
		TiledMapTileLayer doorLayer = (TiledMapTileLayer)map.getLayers().get("Doors");	
		
		int tileSize = Config.TILE_SIZE;

		for (int i=0;i<doorLayer.getWidth();i++) {
			for (int j=0;j<doorLayer.getHeight();j++) {
			
				Cell cell = doorLayer.getCell(i,j);
				if (cell==null) {
					continue;
				}
				Vector2 position = new Vector2(tileSize/2*Config.WORLD_TO_BOX+tileSize*i*Config.WORLD_TO_BOX, tileSize/2*Config.WORLD_TO_BOX+tileSize*j*Config.WORLD_TO_BOX);
				GameObjectFactory.createDoor(physicsWorld, position);
				
				cell.setTile(null);
			}
		}
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
		this.player.setHealth(20);
		
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
				AiController ai = new AiController(guard, physicsWorld);
				enemies.add(ai);
			}
		}
	}
	
	private void registerEndOfLevel(){
		MapLayer triggerLayer = map.getLayers().get("Triggers");
		MapObjects triggers = triggerLayer.getObjects();	
		MapObject endZone = triggers.get("end_of_level");
		MapProperties properties = endZone.getProperties();
		Iterator<String> keys = properties.getKeys();
		while(keys.hasNext()){
			String key = keys.next();
			System.out.println(key);
		}

		int tileSize = Config.TILE_SIZE;
		int x = (Integer)properties.get("x")+tileSize;
		int y = (Integer)properties.get("y")+tileSize/2;
		float width = Float.parseFloat((String) properties.get("width"))*tileSize/2;
		float height = Float.parseFloat((String)properties.get("height"))*tileSize/2;
		Rectangle triggerZone = new Rectangle(x, y, width, height);
		GameObjectFactory.createEndOfLevelTrigger(this.physicsWorld, triggerZone);
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
			if ( body.getUserData() instanceof Bullet) {
				Bullet b = (Bullet)body.getUserData();
				if ( b.getStatus()  == PlayerState.DEAD) {	
					physicsWorld.destroyBody(body);
				}
			}
			if ( body.getUserData() instanceof Ammunition) {
				Ammunition a = (Ammunition)body.getUserData();
				if (a.getStatus() == PlayerState.DEAD) {
					physicsWorld.destroyBody(body);
				}
			}
			if ( body.getUserData() instanceof Medpack) {
				Medpack a = (Medpack)body.getUserData();
				if (a.getStatus() == PlayerState.DEAD) { 
					physicsWorld.destroyBody(body);
				}
			}
			
			if ( body.getUserData() instanceof Player) {
				Player p = (Player)body.getUserData();
				if ( p.getStatus()  == PlayerState.DEAD) {	
					p.getBody().setActive(false);
					//physicsWorld.destroyBody(body);
				}
			}d
		}
		
		GameObjectFactory.createAmmunitions();
		
		this.physicsWorld.step(deltaTime, 6, 2);
	}
	
	public World getWorld() {
		return this.physicsWorld;
	}
	
	public Player getPlayer() {
		return this.player;
	}
}
