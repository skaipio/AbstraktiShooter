package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.animations.SpriteAnimation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class DynamicObject extends GameObject{
	protected Body body;
	private SpriteAnimation[] animations;
	
	public DynamicObject(int numOfStates){
		this.animations = new SpriteAnimation[numOfStates];
	}

	public Vector2 getPosition() {
		return this.body.getPosition().scl(Config.BOX_TO_WORLD);
	}
	public float getX() {
		return getPosition().x;
	}
	public float getY() {
		return getPosition().y;
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
	
	public Body getBody(){
		return this.body;
	}
	
	protected SpriteAnimation getAnimationAt(int index){
		return this.animations[index];
	}
	
	void addAnimation(SpriteAnimation animation, int indexToAddAt){
		this.animations[indexToAddAt] = animation;
	}
	
	void setBody(Body body){
		this.body = body;
	}
}
