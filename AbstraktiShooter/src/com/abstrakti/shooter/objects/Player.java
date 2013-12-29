package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.components.CPlayerControlled;
import com.abstrakti.shooter.components.CSprite;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DynamicObject {
	private static Player player;
	private float speed = 32*7f; //per second
	private playerState status; 
	private int health;
	
	private Player(World world){	    
		CircleShape dynamicCircle = new CircleShape();  
	    dynamicCircle.setRadius(15f*Config.WORLD_TO_BOX);
	    this.createBody(world, dynamicCircle);
	    
	    dynamicCircle.dispose();
	       
		CSprite sprite = new CSprite(this);
		sprite.setTextureRegion("entities/player_pistol");
		this.addComponent(sprite);
	    
	    CPlayerControlled movement = new CPlayerControlled(this);
		movement.setSpeed(speed);
		this.addComponent(movement);
		this.status = playerState.IDLE;
	}
	
	public static Player getInstance(World world){
		if (player == null){
			player = new Player(world);
		}
		return player;
	}
	
	public void hurt(int amount){
		this.health -= amount;
		if (this.health <=0) {
			this.status = playerState.DEAD; 
		}
	}
	public playerState getStatus(){
		return this.status;
	}
	
	public void update(float deltaTime){
		super.update(deltaTime);		
	}
}
