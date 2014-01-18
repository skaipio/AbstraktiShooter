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
	private PlayerState status; 
	private int health;
	public final int MAXHEALTH = 100;
	private Weapon handGun;
	private UseRangeSensor useRangeSensor;
	ParticleEffect bloodEffect, gunEffect;
	private TeamState team;
	int currentWeapon = 0;
	

	public Player(){	 
		this.status = PlayerState.IDLE;
		this.health = 1;
		this.handGun = new Machinegun();
		this.handGun.addAmmo(100);
		bloodEffect = new ParticleEffect();
		bloodEffect.load(Gdx.files.internal("particle-effects/blood.vep"), Gdx.files.internal("particle-effects"));
		gunEffect = new ParticleEffect();
		gunEffect.load(Gdx.files.internal("particle-effects/fire.p"), Gdx.files.internal("particle-effects"));
	}
	
	public void changeWeapon(int number) {
		this.currentWeapon = number;
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
		Drawable drawable = this.getDrawable(this.status.ordinal());
		if (drawable != null) {
			drawable.update(delta);
		}
	}
	
	public void stopMovement() {
		this.setStatus(PlayerState.IDLE);
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

	public void  applyMovementImpulse(float impulseX, float impulseY) {
		this.setStatus(PlayerState.WALKING);
		//this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().add(new Vector2(0.3f*impulseX,0.3f*impulseY)));
		this.getBody().applyLinearImpulse(new Vector2(7.5f*impulseX,7.5f*impulseY), this.getBody().getPosition(), true);
	}

	public void moveForward(float delta) {
		float impulseX = (float) Math.cos(this.getAngle());
		float impulseY = -(float) Math.sin(this.getAngle());
		applyMovementImpulse(impulseX, impulseY);	
	}
	public void moveBackward(float delta) {	
		float impulseX = -(float) Math.cos(this.getAngle());
		float impulseY = (float) Math.sin(this.getAngle());
		applyMovementImpulse(impulseX, impulseY);
	}
	public void strafeLeft(float delta) {
		float impulseX = -(float) Math.cos(this.getAngle()+Math.toRadians(90));
		float impulseY = (float) Math.sin(this.getAngle()+Math.toRadians(90));
		applyMovementImpulse(impulseX, impulseY);
	}
	public void strafeRight(float delta) {
		float impulseX = (float) Math.cos(this.getAngle()+Math.toRadians(90));
		float impulseY = -(float) Math.sin(this.getAngle()+Math.toRadians(90));
		applyMovementImpulse(impulseX, impulseY);
	}
	public void shoot(World physiscsWorld, float delta) {
		boolean gunFired =  this.handGun.fireGun(physiscsWorld, this.getPosition(), this.getAngle(), delta);
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
