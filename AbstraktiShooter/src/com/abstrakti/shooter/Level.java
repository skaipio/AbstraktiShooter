package com.abstrakti.shooter;

import javax.rmi.CORBA.Tie;

import com.abstrakti.shooter.io.GameScreen;
import com.abstrakti.shooter.objects.GameObjectFactory;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.Wall;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Level {

	
	private final World physicsWorld;
	private Player player;
	private TiledMap map;
	
	public Level(String levelname){
		this.physicsWorld = new World(Vector2.Zero, true);	
		TmxMapLoader loader = new TmxMapLoader();
		
		this.map = loader.load(levelname);
		
		GameScreen.getInstance().setMap(map);		
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
		
		this.setPlayerIntoLevel();
	}
	
	private void setPlayerIntoLevel(){
		System.out.println(map.getLayers().getCount());
		MapLayer triggerLayer = map.getLayers().get("Spawns");
		MapObjects mapObjects = triggerLayer.getObjects();		
		MapObject mapObject = mapObjects.get("playerSpawn");
		MapProperties properties = mapObject.getProperties();
		int x = (Integer)properties.get("x");
		int y = (Integer)properties.get("y");
		
		this.player = GameObjectFactory.createPlayer(physicsWorld);
		this.player.setPosition(x, y);
		
		GameScreen.getInstance().lockCameraOn(this.player);
	}
	
	public void update(float deltaTime){
		this.player.update(deltaTime);
		
		Array<Body> bodies = new Array<Body>();
		this.physicsWorld.getBodies(bodies);
		
		for (Body body: bodies) {
			GameObject obj = (GameObject) body.getUserData();
			if (obj!=null) {
				if (Wall.class.isInstance(obj)) {
					continue;
				}
				if (Player.class.isInstance(obj)) {
					continue;
				}
				
				obj.update(deltaTime);
			}
		}
			

		//this.physicsWorld.step(1/60f, 6, 2);
		this.physicsWorld.step(deltaTime, 6, 2);
	}
	
	public World getWorld() {
		return physicsWorld;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void addEntity(GameObject o){
		// this.physicsWorld.createBody()
	}
}
