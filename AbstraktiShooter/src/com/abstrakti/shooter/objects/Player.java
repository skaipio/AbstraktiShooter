package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.components.CControlledMovement;
import com.abstrakti.shooter.components.CMapPlace;
import com.abstrakti.shooter.components.CSprite;
import com.abstrakti.shooter.components.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends GameObject {
	private static final float WORLD_TO_BOX=0.01f;
	private static final float BOX_TO_WORLD=100f;
	
	private static Player player;
	private float speed = 100f; //per second
	
	public Vector2 controllerVector;
	
	private Player(World world){
		CMapPlace place = new CMapPlace(10, 10);
		this.addComponent(place);
		
		BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;
	    
	    bodyDef.position.set(190*WORLD_TO_BOX, 90*WORLD_TO_BOX);  
	    body = world.createBody(bodyDef);  

	    body.setUserData(player);
	    
	    CircleShape dynamicCircle = new CircleShape();  
	    dynamicCircle.setRadius(15f*WORLD_TO_BOX);  
	    
	    FixtureDef fixtureDef = new FixtureDef();  
	    fixtureDef.shape = dynamicCircle;  
	    fixtureDef.density = 1.0f;  
	    
	    fixtureDef.friction = 0.0f;  
	    fixtureDef.restitution = 1;  
	    body.createFixture(fixtureDef);
	       
		CSprite sprite = new CSprite(place, this);
		sprite.setTextureRegion("entities/testPlayer-01");
		this.addComponent(sprite);
	    
	    CControlledMovement movement = new CControlledMovement(place, this);
		movement.setSpeed(speed);
		this.addComponent(movement);
		
		controllerVector = new Vector2(0,0);
	}
	
	public static Player getInstance(World world){
		if (player == null){
			player = new Player(world);
		}
		return player;
	}
	
	public void handleInput(){
		// jos Hiiren vasen nappi painettu, niin luo Luoti-entiteetti
	}
	
	public void setPosition(float x, float y){
		CMapPlace place = (CMapPlace)this.getComponent(Component.Type.MapPlace);
		
		place.setPlace(x, y);
	}
	
	public void update(float deltaTime){
		super.update(deltaTime);
		
		// If we are going diagonally we are adjusting the vector to sin 45.
		Vector2 vector = controllerVector.cpy();
		if (vector.x*vector.y == 1 || vector.x*vector.y == -1) {
			vector.x = vector.x* 0.707106781f;
			vector.y = vector.y* 0.707106781f;
		}
		
		body.setLinearVelocity(vector);
		
	}
}
