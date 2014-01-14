package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.managers.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Weapon {
	protected WeaponFiremode firemode;
	protected int numberOfBulletsPerShot;
	private boolean triggerStatus;
	private int ammo;
	private int MAXAMMO = 100;
	protected Sound sound;
	protected float fireRate;
	protected int reloadingTime;
	protected float damage;
	private static float time = 0;
	
	public Weapon() {
		this.triggerStatus = false;
	}
	
	public int getAmmo() {
		return this.ammo;
	}
	
	public void addAmmo(int amount) {
		if ((this.ammo + amount) > MAXAMMO) {
			this.ammo = MAXAMMO;
		} else {
			this.ammo += amount; 
		}
	}
	
	/* Checks the firemode before firing, the trigger needs to be released later if the firemode is single.
	 */
	public boolean fireGun(World physiscsWorld, Vector2 position, float angle, float delta){
		time -= delta;
		if (this.ammo == 0) {
			return false;
		} else if (this.firemode == WeaponFiremode.SINGLE && this.triggerStatus == true) {
			return false;
		} else if (this.firemode == WeaponFiremode.CONTINUOS) {
			if (time <= 0) {
				time = fireRate;	
				this.ammo--;
				this.shootMultipleBullets(physiscsWorld, position,angle,numberOfBulletsPerShot); 
				sound.play(1.0f);
				return true;
			}
			return false;
		} else {
			this.triggerStatus = true;
			this.ammo--;
			this.shootMultipleBullets(physiscsWorld, position,angle,numberOfBulletsPerShot); 
			sound.play(1.0f);
			return true;
		}
	}
	private void shootBullet(World physiscsWorld, Vector2 position, float angle) {
		float x = position.x;
		float y = position.y;
		x += 27*(float) Math.cos(angle);
		y += -27*(float) Math.sin(angle);
		
		Bullet b = GameObjectFactory.createBullet(physiscsWorld);

		Vector2 newPosition = new Vector2(x,y);
		b.setPosition(newPosition);
		b.setRotation(angle);
	}
	private void shootMultipleBullets(World physiscsWorld, Vector2 position, float angle, int numberOfBullets) {
		for (int i=0; i<numberOfBullets; i++) {
			shootBullet(physiscsWorld, position, angle);
			angle += 0.01f;
		}
		
	}
	public void releaseTrigger() {
		this.triggerStatus = false;
	}
}
