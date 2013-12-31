package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.animations.SpriteAnimation;
import com.abstrakti.shooter.components.CSprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends DynamicObject {
	private float speed = 32*7f; //per second
	private PlayerState status; 
	private int health;
	private Weapon handGun;
	
	Player(){	    
		super(PlayerState.values().length);
		CSprite sprite = new CSprite(this);
		sprite.setTextureRegion("entities/player_pistol_standing");
		this.addComponent(sprite);
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
//		SpriteAnimation currentAnim = this.getAnimationAt(this.status.ordinal());
//		if (currentAnim != null)
//			currentAnim.reset();
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);	
		SpriteAnimation currentAnim = this.getAnimationAt(this.status.ordinal());
		if (currentAnim != null)
			currentAnim.update(deltaTime);
	}
	
	@Override
	public void draw(SpriteBatch batch){
//		super.draw(batch);
		SpriteAnimation currentAnim = this.getAnimationAt(this.status.ordinal());
		if (currentAnim != null){
			currentAnim.draw(batch);
		}
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public void moveForward(Vector2 movementVector) {
		movementVector.x += (float) Math.cos(this.getAngle());
		movementVector.y += -(float) Math.sin(this.getAngle());
	}
	public void moveBackward(Vector2 movementVector) {
		movementVector.x += -(float) Math.cos(this.getAngle());
		movementVector.y += (float) Math.sin(this.getAngle());
	}
	public void strafeLeft(Vector2 movementVector) {
		movementVector.x += -(float) Math.cos(this.getAngle()+Math.toRadians(90));
		movementVector.y += (float) Math.sin(this.getAngle()+Math.toRadians(90));
	}
	public void strafeRight(Vector2 movementVector) {
		movementVector.x += (float) Math.cos(this.getAngle()+Math.toRadians(90));
		movementVector.y += -(float) Math.sin(this.getAngle()+Math.toRadians(90));
	}
	public void shoot() {
		this.handGun.fireGun();
	}
	public void releaseTrigger() {
		this.handGun.releaseTrigger();
	}
}
