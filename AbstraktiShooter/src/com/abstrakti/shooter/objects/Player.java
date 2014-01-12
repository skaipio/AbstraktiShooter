package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.io.Drawable;
import com.abstrakti.shooter.states.PlayerState;
import com.abstrakti.shooter.triggers.UseRangeSensor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DynamicObject {
	private float speed = 32*7f; //per second
	private PlayerState status; 
	private int health;
	public final int MAXHEALTH = 100;
	private Weapon handGun;
	private Vector2 movementVector;
	private UseRangeSensor useRangeSensor;
	ParticleEffect bloodEffect, gunEffect;
	private TeamState team;
	

	public Player(){	 
		this.movementVector = new Vector2(); 
		this.movementVector.x = 0;
		this.movementVector.y = 0;

		this.status = PlayerState.IDLE;
		this.health = 1;
		this.handGun = new Weapon(WeaponFiremode.SINGLE);
		this.handGun.addAmmo(100);
		bloodEffect = new ParticleEffect();
		bloodEffect.load(Gdx.files.internal("../AbstraktiShooter-desktop/textures/particle-effects/blood.vep"), Gdx.files.internal("../AbstraktiShooter-desktop/textures/entities/"));
		gunEffect = new ParticleEffect();
		gunEffect.load(Gdx.files.internal("../AbstraktiShooter-desktop/textures/particle-effects/fire.p"), Gdx.files.internal("../AbstraktiShooter-desktop/textures/entities/"));
	}
	
	public void setTeamState(TeamState team) {
		this.team = team;
	}
	
	public TeamState getTeamState() {
		return this.team;
	}

	public void hurt(int amount){
		System.out.println("hurting " + amount);
		this.health -= amount;
		if ( amount !=0 ) {
			bloodEffect.start();
		}
		if (this.health <=0) {
			this.status = PlayerState.DEAD; 
			if (this.getAmmo() > 0) {
				int random = MathUtils.random(1,2);
				
				if (random == 1) {
					GameObjectFactory.addAmmunition(this.getPosition(), this.getAmmo());
				} else if (random == 2) {
					GameObjectFactory.addMedpak(this.getPosition());
				}
				
				
			}
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
		if ((this.health + amount) > MAXHEALTH) {
			this.health = MAXHEALTH;
		} else {
			this.health += amount; 
		}
	}
	public void pickAmmunition(Ammunition a) {
		this.handGun.addAmmo(a.withdraw());
	}
	
	public void pickMedpack(Medpack a) {
		// TODO Auto-generated method stub
		this.setHealth(a.withdraw());
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
			float x = this.getX();
			float y = this.getY();
		
			bloodEffect.setPosition(x, y);
			bloodEffect.draw(batch);
			bloodEffect.update(0.05F);
			
			
			x += 22*(float) Math.cos(getAngle());
			y += -22*(float) Math.sin(getAngle());
			
			gunEffect.setPosition(x, y);
			
			for (int i = 0; i < gunEffect.getEmitters().size; i++) { //get the list of emitters - things that emit particles
				gunEffect.getEmitters().get(i).getAngle().setLow(getAngle()); //low is the minimum rotation
				gunEffect.getEmitters().get(i).getAngle().setHigh(getAngle()); //high is the max rotation
	         }
			gunEffect.draw(batch);
			gunEffect.update(0.05F);
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
		boolean gunFired =  this.handGun.fireGun(physiscsWorld, this.getPosition(), this.getAngle());
		if (gunFired) {
			gunEffect.start();
		}
	}
	public void releaseTrigger() {
		this.handGun.releaseTrigger();
	}
	
	public void useDoor(){
		this.useRangeSensor.useClosestDoor();
	}
	
	void setUseRangeSensor(UseRangeSensor useRange){
		this.useRangeSensor = useRange;
	}
}
