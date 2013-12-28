package com.abstrakti.shooter;

import com.abstrakti.shooter.io.GameScreen;
import com.abstrakti.shooter.objects.Enemy;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.Wall;
import com.badlogic.gdx.Gdx;
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
	private final Player player;
	private TiledMap map;
	
	public Level(String levelname){
		this.physicsWorld = new World(Vector2.Zero, true);	
		
		this.player = Player.getInstance(physicsWorld);
		this.player.setPosition(190, 90);
				
		Enemy.getInstance(physicsWorld).setPosition(191,91);
		
		TmxMapLoader loader = new TmxMapLoader();
		
		this.map = loader.load(levelname);
		
		GameScreen.getInstance().setMap(map);
		GameScreen.getInstance().lockCameraOn(this.player);
	}
	
	public void init(){
		TiledMapTileLayer layer = (TiledMapTileLayer)this.map.getLayers().get(0);
		int tileSize = Config.TILE_SIZE;
		float tileSizeHalved = tileSize / 2f;
		
		PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(tileSizeHalved*Config.WORLD_TO_BOX,tileSizeHalved*Config.WORLD_TO_BOX);
        
        BodyDef myBodyDef2 = new BodyDef();
        myBodyDef2.type = BodyType.StaticBody; //this will be a static$       
        myBodyDef2.angle = 0; //set the starting angle
		
		for (int i=0;i<layer.getWidth();i++) {
			for (int j=0;j<layer.getHeight();j++) {
			
				Cell cell = layer.getCell(i,j);
				if (cell==null) {
					continue;
				}
				
				if (cell.getTile().getProperties().get("collision").equals("true") ) {
								
					myBodyDef2.position.set(tileSize*i*Config.WORLD_TO_BOX, tileSize*j*Config.WORLD_TO_BOX); //set the starting position

	                Body body2 = physicsWorld.createBody(myBodyDef2);    

	                FixtureDef boxFixtureDef2 = new FixtureDef();
	                boxFixtureDef2.shape = boxShape;
	                boxFixtureDef2.density = 1;
	                body2.createFixture(boxFixtureDef2);
	                body2.setUserData(new Wall());					
					
				}
				
			}
		}
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
	
	public void addEntity(GameObject o){
		// this.physicsWorld.createBody()
	}
}
