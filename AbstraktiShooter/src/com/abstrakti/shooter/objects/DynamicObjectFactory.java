package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.animations.PlayerIdleAnimation;
import com.abstrakti.shooter.animations.PlayerWalkAnimation;
import com.abstrakti.shooter.animations.SpriteAnimation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public final class DynamicObjectFactory {
	public static Player createPlayer(World world){
		Player player = new Player();
		SpriteAnimation playerWalking = new PlayerWalkAnimation(player);
		player.addAnimation(playerWalking, PlayerState.WALKING.ordinal());
		SpriteAnimation playerStanding = new PlayerIdleAnimation(player);
		player.addAnimation(playerStanding, PlayerState.IDLE.ordinal());
		
		CircleShape shape = new CircleShape();  
		shape.setRadius(15f*Config.WORLD_TO_BOX); 
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		
		Body body = world.createBody(bodyDef);
		body.setUserData(player);
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;

		fixtureDef.friction = 0.0f;
		fixtureDef.restitution = 0;
		body.createFixture(fixtureDef);
		
		player.setBody(body);
		
		shape.dispose();
		
		return player;
	}
}
