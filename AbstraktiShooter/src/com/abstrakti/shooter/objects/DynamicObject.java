package com.abstrakti.shooter.objects;

import java.util.HashMap;
import java.util.Map;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.animations.SpriteAnimation;
import com.abstrakti.shooter.io.Drawable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class DynamicObject extends GameObject{
	protected Body body;
	private Map<Integer, Drawable> drawables = new HashMap<Integer, Drawable>();

	public Vector2 getPosition() {
		
		Vector2 v =  this.body.getPosition().scl(Config.BOX_TO_WORLD);
		

		return v;
		
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
		this.body.setTransform(this.body.getPosition(), -angle);
	}
	public float getAngle(){
		return -this.body.getAngle();
	}
	
	public Body getBody(){
		return this.body;
	}
	
	protected Drawable getDrawable(int stateOrdinal){
		return this.drawables.get(stateOrdinal);
	}
	
	void addDrawable(Drawable drawable, int stateOrdinal){
		this.drawables.put(stateOrdinal, drawable);
	}
	
	public void draw(SpriteBatch batch) {
		draw(batch);
	}
	
	
	void setBody(Body body){
		this.body = body;
	}
}
