package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.Config;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class DynamicObject extends GameObject{
	protected Body body;

	public DynamicObject() {}

	public Vector2 getPosition() {
		return this.body.getPosition().scl(Config.BOX_TO_WORLD);
	}
	
	public void setPosition(Vector2 vector){
		this.setPosition(vector.x, vector.y);
	}
	public void setPosition(float x, float y){
		this.body.setTransform(x*Config.WORLD_TO_BOX,  y*Config.WORLD_TO_BOX, 0);		
	}
	public void setPosition(float x, float y, float angle){
		this.body.setTransform(x*Config.WORLD_TO_BOX,  y*Config.WORLD_TO_BOX, angle);		
	}
	public void setVelocity(Vector2 velocity){
		//this.body.setLinearVelocity(velocity.scl(Config.WORLD_TO_BOX));
		this.body.setLinearVelocity(velocity);
	}
	public void setRotation(float angle){
		this.body.setTransform(this.body.getPosition(), angle);
	}
	public float getAngle(){
		return this.body.getAngle();
	}
	
	protected void createBody(World world, Shape shape){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		
		this.body = world.createBody(bodyDef);
		this.body.setUserData(this);
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;

		fixtureDef.friction = 0.0f;
		fixtureDef.restitution = 0;
		this.body.createFixture(fixtureDef);
	}
}
