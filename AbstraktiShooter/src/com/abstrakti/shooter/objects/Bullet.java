package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.animations.SpriteAnimation;
import com.abstrakti.shooter.managers.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends DynamicObject {
	private float speed = 128*7f;
	private Vector2 movementVector;
	private int health;
	private PlayerState status; 
	
	public Bullet() {
		super(PlayerState.values().length);
		this.movementVector = new Vector2(); 
		this.movementVector.x = 0;
		this.movementVector.y = 0;
		this.health = 1;
		
	}
	public PlayerState getStatus(){
		return this.status;
	}
	public void setStatus(PlayerState state){
		this.status = state;
	}
	public void hurt(int amount){
		this.health -= amount;
		if (this.health <=0) {
			this.status = PlayerState.DEAD; 
		}
		System.out.println("hurt");
	}
	public void moveForward(float delta) {
		this.movementVector.x += (float) Math.cos(this.getAngle());
		this.movementVector.y += -(float) Math.sin(this.getAngle());
	}
	@Override
	public void update(float delta){
		super.update(delta);
		this.moveForward(delta);
		this.updateMovementVector(delta);
	}
	private void updateMovementVector(float delta) {
		this.movementVector.nor();
		this.movementVector.scl(delta*this.getSpeed());
		if (this.movementVector.x != 0 || this.movementVector.y != 0){
			this.setStatus(PlayerState.WALKING);
		}else{
			this.setStatus(PlayerState.IDLE);
		}
		this.setVelocity(this.movementVector);
	}
	@Override
	public void draw(SpriteBatch batch){
		Texture t = new Texture(Gdx.files.internal("../AbstraktiShooter-desktop/textures/entities/bullet.png"));
		batch.draw(t, this.getPosition().x, this.getPosition().y);
	}
	public float getSpeed() {
		return this.speed;
	}
	public String toString() {
		return "bullet";
	}

}
