package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.io.Drawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DynamicObject {
	private float speed = 32*7f; //per second
	private PlayerState status; 
	private int health;
	private Weapon handGun;
	private Vector2 movementVector;
	ParticleEffect effect;
	
	boolean bloodEffect = false; 

	public Player(){	 
		this.movementVector = new Vector2(); 
		this.movementVector.x = 0;
		this.movementVector.y = 0;

		this.status = PlayerState.IDLE;
		this.health = 1;
		this.handGun = new Weapon(WeaponFiremode.SINGLE);
		this.handGun.addAmmo(100);
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("../AbstraktiShooter-desktop/textures/particle-effects/blood.vep"), Gdx.files.internal("../AbstraktiShooter-desktop/textures/entities/"));
	}

	public void hurt(int amount){
		this.health -= amount;
		effect.start();
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
	public int getHealth() {
		return this.health;
	}
	public void setHealth(int amount) {
		this.health += amount;
	}
	@Override
	public void update(float delta){

		this.updateMovementVector(delta);
		
		Drawable drawable = this.getDrawable(this.status.ordinal());
		if (drawable != null) {
			drawable.update(delta);
		}
	}
	
	private void updateMovementVector(float delta) {
		if (this.status != PlayerState.DEAD) {
			this.movementVector.nor();
			this.movementVector.scl(delta*this.getSpeed());
			if (this.movementVector.x != 0 || this.movementVector.y != 0){
				this.setStatus(PlayerState.WALKING);
			}else{
				this.setStatus(PlayerState.IDLE);
			}
			this.setVelocity(this.movementVector);
		}
	}
	public void stopMovement() {
		this.movementVector.x = 0;
		this.movementVector.y = 0;
	}
	

	@Override
	public void draw(SpriteBatch batch){
		Drawable drawable = this.getDrawable(this.status.ordinal());
		if (drawable != null){
			drawable.draw(batch);
			float x = this.getX()-40;
			float y = this.getY()-25;
		
			effect.setPosition(x, y);
			effect.draw(batch);
			effect.update(0.05F);
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
	public void shoot(World physiscsWorld) {
		this.handGun.fireGun(physiscsWorld, this.getPosition(), this.getAngle());
	}
	public void releaseTrigger() {
		this.handGun.releaseTrigger();
	}
}
