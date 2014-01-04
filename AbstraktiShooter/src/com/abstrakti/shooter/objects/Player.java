package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.animations.SpriteAnimation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends DynamicObject {
	private float speed = 32*7f; //per second
	private PlayerState status; 
	private int health;
	private Weapon handGun;
	private Vector2 movementVector;

	public Player(){	    
		super(PlayerState.values().length);
		this.movementVector = new Vector2(); 
		this.movementVector.x = 0;
		this.movementVector.y = 0;

		this.status = PlayerState.IDLE;
		this.health = 1;
		this.handGun = new Weapon(WeaponFiremode.SINGLE);
		this.handGun.addAmmo(100);
	}

	public void hurt(int amount){
		this.health -= amount;
		if (this.health <=0) {
			this.status = PlayerState.DEAD; 
		}
	}
	public PlayerState getStatus(){
		return this.status;
	}
	public void setStatus(PlayerState state){
		this.status = state;
	}
	public int getAmmo() {
		return this.handGun.getAmmo();
	}

	@Override
	public void update(float delta){
		super.update(delta);
	
		this.updateMovementVector(delta);
		SpriteAnimation currentAnim = this.getAnimationAt(this.status.ordinal());
		if (currentAnim != null) {
			currentAnim.update(delta);
		}
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
	public void stopMovement() {
		this.movementVector.x = 0;
		this.movementVector.y = 0;
	}
	

	@Override
	public void draw(SpriteBatch batch){
		SpriteAnimation currentAnim = this.getAnimationAt(this.status.ordinal());
		if (currentAnim != null){
			currentAnim.draw(batch);
		}
	}

	public float getSpeed() {
		return this.speed;
	}


	public void moveForward(float delta) {
		this.movementVector.x += (float) Math.cos(this.getAngle());
		this.movementVector.y += -(float) Math.sin(this.getAngle());
	}
	public void moveBackward(float delta) {
		this.movementVector.x += -(float) Math.cos(this.getAngle());
		this.movementVector.y += (float) Math.sin(this.getAngle());
	}
	public void strafeLeft(float delta) {
		this.movementVector.x += -(float) Math.cos(this.getAngle()+Math.toRadians(90));
		this.movementVector.y += (float) Math.sin(this.getAngle()+Math.toRadians(90));
	}
	public void strafeRight(float delta) {
		this.movementVector.x += (float) Math.cos(this.getAngle()+Math.toRadians(90));
		this.movementVector.y += -(float) Math.sin(this.getAngle()+Math.toRadians(90));
	}
	public void turnLeft() {

	}
	public void turnRight() {


	}
	public void shoot() {
		this.handGun.fireGun();
	}
	public void releaseTrigger() {
		this.handGun.releaseTrigger();
	}
}
