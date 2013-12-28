package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.components.CSprite;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends DynamicObject {
	private static Enemy enemy;
	private float speed = 32*7f; //per second
	
	private Enemy(World world){	    
		CircleShape dynamicCircle = new CircleShape();  
	    dynamicCircle.setRadius(15f*Config.WORLD_TO_BOX);
	    this.createBody(world, dynamicCircle);
	    dynamicCircle.dispose();
	       
		CSprite sprite = new CSprite(this);
		sprite.setTextureRegion("entities/testPlayer-01");
		this.addComponent(sprite);
	}
	
	public static Enemy getInstance(World world){
		if (enemy == null){
			enemy = new Enemy(world);
		}
		return enemy;
	}
	
	public void handleInput(){
		// jos Hiiren vasen nappi painettu, niin luo Luoti-entiteetti
	}
	
	public void update(float deltaTime){
		super.update(deltaTime);		
	}
}