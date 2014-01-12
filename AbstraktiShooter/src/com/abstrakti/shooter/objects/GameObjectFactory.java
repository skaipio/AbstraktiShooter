package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.animations.DoorClosingAnimation;
import com.abstrakti.shooter.animations.DoorOpeningAnimation;
import com.abstrakti.shooter.animations.PlayerWalkAnimation;
import com.abstrakti.shooter.animations.SpriteAnimation;
import com.abstrakti.shooter.io.StaticDrawable;
import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.states.DoorState;
import com.abstrakti.shooter.states.PlayerState;
import com.abstrakti.shooter.triggers.EndOfLevelTrigger;
import com.abstrakti.shooter.triggers.UseRangeSensor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public final class GameObjectFactory {
	
	public static World world;
	private static Array<GameObject> itemsToAdd = new Array<GameObject>();
	
	
	public static Player createPlayer(World world){

		Player player = new Player();
		
		AssetManager assets = AssetManager.getInstance();
		Sprite playerStanding = assets.getSprite("player_pistol_standing");	
		StaticDrawable playerStandingDrawable = new StaticDrawable(playerStanding, player);
		player.addDrawable(playerStandingDrawable, PlayerState.IDLE.ordinal());
		SpriteAnimation playerWalking = new PlayerWalkAnimation(player);
		player.addDrawable(playerWalking, PlayerState.WALKING.ordinal());
		Sprite playerDead = assets.getSprite("corpse2");
		StaticDrawable playerDeadDrawable = new StaticDrawable(playerDead, player);
		player.addDrawable(playerDeadDrawable, PlayerState.DEAD.ordinal());
		
	
		CircleShape shape = new CircleShape();  
		shape.setRadius(16f*Config.WORLD_TO_BOX);
	
		
		/*
		PolygonShape shape = new PolygonShape();
		 shape.setAsBox(40/2*Config.WORLD_TO_BOX, 25/2*Config.WORLD_TO_BOX);
		*/
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		
		Body body = world.createBody(bodyDef);
		body.setUserData(player);
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.0f;

		fixtureDef.friction = 0.0f;
		fixtureDef.restitution = 0;
		Fixture playerFixture = body.createFixture(fixtureDef);
		playerFixture.setUserData(player);
		
		shape.setRadius(32*Config.WORLD_TO_BOX);
		FixtureDef useRangeDef = new FixtureDef();
		useRangeDef.shape = shape;
		useRangeDef.isSensor = true;
		Fixture useRange = body.createFixture(useRangeDef);
		UseRangeSensor useRangeSensor = new UseRangeSensor(player);
		useRange.setUserData(useRangeSensor);
				
		
		player.setBody(body);
		player.setUseRangeSensor(useRangeSensor);
		
		shape.dispose();
		
		return player;
	}
	
	public static Bullet createBullet(World world) {
		Bullet b = new Bullet();
		
		AssetManager assets = AssetManager.getInstance();		
		Sprite bulletSprite = assets.getSprite("bullet");
		StaticDrawable bulletDrawable = new StaticDrawable(bulletSprite, b);
		b.addDrawable(bulletDrawable, 0);
		
		CircleShape shape = new CircleShape();  
		shape.setRadius(1f*Config.WORLD_TO_BOX); 
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.bullet = true;
		bodyDef.fixedRotation = true;
		
		Body body = world.createBody(bodyDef);
		body.setUserData(b);
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.0f;
		fixtureDef.friction = 0.0f;
		fixtureDef.restitution = 0;
		body.createFixture(fixtureDef);
		
		b.setBody(body);
		
		shape.dispose();
		
		return b;
		
	}
	public static void createWall(World world, Vector2 position){
		int tileSize = Config.TILE_SIZE;
		float tileSizeHalved = tileSize / 2f;
		
		PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(tileSizeHalved*Config.WORLD_TO_BOX,tileSizeHalved*Config.WORLD_TO_BOX);
        
        BodyDef myBodyDef2 = new BodyDef();
        myBodyDef2.type = BodyType.StaticBody; //this will be a static$       
        myBodyDef2.angle = 0; //set the starting angle
        
        myBodyDef2.position.set(position); //set the starting position

        Body body2 = world.createBody(myBodyDef2);    

        FixtureDef boxFixtureDef2 = new FixtureDef();
        boxFixtureDef2.shape = boxShape;
        boxFixtureDef2.density = 1;
        body2.createFixture(boxFixtureDef2);
        body2.setUserData(new Wall());				
        
        boxShape.dispose();
	}
	
	public static void createEndOfLevelTrigger(World world, Rectangle collisionZone){
		PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(collisionZone.width*Config.WORLD_TO_BOX,collisionZone.height*Config.WORLD_TO_BOX);
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(collisionZone.x*Config.WORLD_TO_BOX, collisionZone.y*Config.WORLD_TO_BOX);
        
        FixtureDef sensor = new FixtureDef();
        sensor.shape = boxShape;
        sensor.isSensor = true;
        
        Body body = world.createBody(bodyDef);
        Fixture sensorFixture = body.createFixture(sensor);
        sensorFixture.setUserData(new EndOfLevelTrigger());
	}
	
	public static void createDoor(World world, Vector2 position){
		int tileSize = Config.TILE_SIZE;
		float tileSizeHalved = tileSize / 2f;
		
		AssetManager assets = AssetManager.getInstance();	
		
		Door door = new Door();
		Sprite doorClosed = assets.getSprite("slideDoor1");
		Sprite doorOpen = assets.getSprite("slideDoor6");
		door.addDrawable(new StaticDrawable(doorClosed, door), DoorState.CLOSED.ordinal());
		door.addDrawable(new DoorClosingAnimation(door), DoorState.CLOSING.ordinal());
		door.addDrawable(new DoorOpeningAnimation(door), DoorState.OPENING.ordinal());
		door.addDrawable(new StaticDrawable(doorOpen, door), DoorState.OPEN.ordinal());
		
		PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(tileSizeHalved*Config.WORLD_TO_BOX,tileSizeHalved*Config.WORLD_TO_BOX);
        
        BodyDef myBodyDef2 = new BodyDef();
        myBodyDef2.type = BodyType.StaticBody; //this will be a static$       
        myBodyDef2.angle = 0; //set the starting angle
        
        myBodyDef2.position.set(position); //set the starting position

        Body body2 = world.createBody(myBodyDef2);    

        FixtureDef boxFixtureDef2 = new FixtureDef();
        boxFixtureDef2.shape = boxShape;
        boxFixtureDef2.density = 1;
        body2.createFixture(boxFixtureDef2);
        body2.setUserData(door);		
        
        door.setBody(body2);
        door.setState(DoorState.CLOSED);
        
        boxShape.dispose();
	}
	
	public static Ammunition createAmmunition(Ammunition ammo){
		AssetManager assets = AssetManager.getInstance();
		
		
		Sprite ammoSprite = assets.getSprite("pistol_ammo");
		StaticDrawable ammoDrawable = new StaticDrawable(ammoSprite, ammo);
		
		ammo.addDrawable(ammoDrawable, 0);
		
		// muita juttuja?
		
		//System.out.println(position);
	//	b.setPosition(100,100);
		
		CircleShape shape = new CircleShape();  
		shape.setRadius(7f*Config.WORLD_TO_BOX); 
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		//bodyDef.position.set(100,100); //set the starting position
		Body body = world.createBody(bodyDef);
		body.setUserData(ammo);
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;

		fixtureDef.friction = 0.0f;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor = false;
		body.createFixture(fixtureDef);
		body.setLinearDamping(4f);
		
		// random direction
		float randomAngle = (float) (MathUtils.random()*2.0f*Math.PI);
        body.setLinearVelocity(new Vector2((float)4*MathUtils.cos(randomAngle), (float)4*MathUtils.sin(randomAngle)));
    
		ammo.setBody(body);
		
		shape.dispose();
		
		return ammo;

	}
	
	private static Medpack createMedpack(Medpack medpack) {
		// TODO Auto-generated method stub
AssetManager assets = AssetManager.getInstance();
		
		
		Sprite medpackSprite = assets.getSprite("medpack");
		StaticDrawable ammoDrawable = new StaticDrawable(medpackSprite, medpack);
		
		medpack.addDrawable(ammoDrawable, 0);
		
		// muita juttuja?
		
		//System.out.println(position);
	//	b.setPosition(100,100);
		
		CircleShape shape = new CircleShape();  
		shape.setRadius(7f*Config.WORLD_TO_BOX); 
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		//bodyDef.position.set(100,100); //set the starting position
		Body body = world.createBody(bodyDef);
		body.setUserData(medpack);
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;

		fixtureDef.friction = 0.0f;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor = false;
		body.createFixture(fixtureDef);
		body.setLinearDamping(4f);
		
		// random direction
		float randomAngle = (float) (MathUtils.random()*2.0f*Math.PI);
        body.setLinearVelocity(new Vector2((float)4*MathUtils.cos(randomAngle), (float)4*MathUtils.sin(randomAngle)));
    
		medpack.setBody(body);
		
		shape.dispose();
		
		return medpack;
	}

	public static void addAmmunition(Vector2 position, int amount) {
		Ammunition ammo = new Ammunition(position, amount);
		
		itemsToAdd.add(ammo);
	}
	public static void addMedpak(Vector2 position) {
		// TODO Auto-generated method stub
		Medpack medpack = new Medpack(position);
		itemsToAdd.add(medpack);
	}
	
	public static void createAmmunitions() {
		for (GameObject g: itemsToAdd) {
			if (g instanceof Ammunition) {
				
				Ammunition ammo = (Ammunition) g;
				createAmmunition(ammo);
				ammo.setPosition(ammo.getInitPosition());
			}
			
			if(g instanceof Medpack) {
				Medpack medpack = (Medpack) g;
				createMedpack(medpack);
				medpack.setPosition(medpack.getInitPosition());
			}
		}
		
		itemsToAdd.clear();
	}

	

}
	
	
