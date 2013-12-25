package com.abstrakti.shooter;

import com.abstrakti.shooter.components.CMapPlace;
import com.abstrakti.shooter.components.Component;
import com.abstrakti.shooter.io.GameScreen;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Level {

	
	private final World physicsWorld;
	private final Player player;
	private TiledMap map;
	
	public Level(String levelname){
		this.physicsWorld = new World(Vector2.Zero, true);	
		
		this.player = Player.getInstance(physicsWorld);
		this.player.setPosition(190, 90);
				
		
		TmxMapLoader loader = new TmxMapLoader();
		
		this.map = loader.load(levelname);
		
		GameScreen.getInstance().setMap(map);
		GameScreen.getInstance().lockCameraOn(this.player);
	}
	
	public void init(){
		TiledMapTileLayer layer = (TiledMapTileLayer)this.map.getLayers().get(0);
		
	
		for (int i=0;i<layer.getWidth();i++) {
			for (int j=0;j<layer.getHeight();j++) {
			
				Cell cell = layer.getCell(i,j);
				if (cell==null) {
					continue;
				}
				
				if (cell.getTile().getProperties().get("collision").equals("true") ) {
								
					BodyDef myBodyDef2 = new BodyDef();
	                myBodyDef2.type = BodyType.StaticBody; //this will be a static$
	                myBodyDef2.position.set(32*i*0.01f, 32*j*0.01f); //set the starting position
	                myBodyDef2.angle = 0; //set the starting angle

	                Body body2 = physicsWorld.createBody(myBodyDef2);

	                PolygonShape boxShape = new PolygonShape();
	                boxShape.setAsBox(16f*0.01f,16f*0.01f);

	                FixtureDef boxFixtureDef2 = new FixtureDef();
	                boxFixtureDef2.shape = boxShape;
	                boxFixtureDef2.density = 1;
	                body2.createFixture(boxFixtureDef2);
	                body2.setUserData(this);					
					
				}
				
			}
		}
	}
	
	public void update(){
		this.player.update(Gdx.graphics.getDeltaTime());
		this.physicsWorld.step(1/60f, 6, 2);
		CMapPlace place = (CMapPlace)player.getComponent(Component.Type.MapPlace);

	}
	
	public void addEntity(GameObject o){
		// this.physicsWorld.createBody()
	}
}
