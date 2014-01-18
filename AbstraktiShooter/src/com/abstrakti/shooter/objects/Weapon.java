package com.abstrakti.shooter.objects;

import java.util.ArrayList;

import com.abstrakti.shooter.managers.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Weapon {
	protected WeaponFiremode firemode;
	protected int numberOfProjectilesPerShot;
	private boolean triggerStatus;
	private int ammo;
	private int MAXAMMO = 100;
	protected Sound sound;
	protected float fireRate;
	protected int reloadingTime;
	protected float damage;
	private static float time = 0;
	protected ArrayList<?> cartridge;
	
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
				this.shootMultipleProjectiles(physiscsWorld, position,angle,numberOfProjectilesPerShot); 
				sound.play(1.0f);
				return true;
			}
			return false;
		} else {
			this.triggerStatus = true;
			this.ammo--;
			this.shootMultipleProjectiles(physiscsWorld, position,angle,numberOfProjectilesPerShot); 
			sound.play(1.0f);
			return true;
		}
	}
	private void shootProjectile(World physiscsWorld, Vector2 position, float angle) {
		float x = position.x;
		float y = position.y;
	
		x += 27*(float) Math.cos(angle);
		y += -27*(float) Math.sin(angle);

		Bullet b = GameObjectFactory.createBullet(physiscsWorld);

		Vector2 newPosition = new Vector2(x,y);
		b.setPosition(newPosition);
		b.setRotation(angle);
		//Grenade g = GameObjectFactory.createGrenade(physiscsWorld, newPosition, angle);
	}
	private void shootMultipleProjectiles(World physiscsWorld, Vector2 position, float angle, int numberOfProjectiles) {
		for (int i=0; i<numberOfProjectiles; i++) {
			shootProjectile(physiscsWorld, position, angle);
			angle += 0.01f;
		}
		
	}
	public void releaseTrigger() {
		this.triggerStatus = false;
	}
}
