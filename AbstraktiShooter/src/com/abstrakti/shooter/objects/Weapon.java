package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.managers.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Weapon {
	private WeaponFiremode firemode;
	private boolean triggerStatus;
	private int ammo;
	private int MAXAMMO = 100;
	private Sound sound;
	
	public Weapon(WeaponFiremode firemode) {
		this.triggerStatus = false;
		this.firemode = firemode;
		this.sound = AssetManager.getInstance().getPistolSound();
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
	public boolean fireGun(World physiscsWorld, Vector2 position, float angle) {
		if (this.ammo == 0) {
			return false;
		} else if (this.firemode == WeaponFiremode.SINGLE && this.triggerStatus == true) {
			return false;
		} else {
			this.triggerStatus = true;
			this.ammo--;
			this.shootBullet(physiscsWorld, position,angle); 
//			System.out.println("BANG, ammo" + " " + ammo);
			sound.play(1.0f);
			return true;
		}
	}
	private void shootBullet(World physiscsWorld, Vector2 position, float angle) {
		Bullet b = GameObjectFactory.createBullet(physiscsWorld);
		
		//horizontal position of the bullets
		float x = position.x;
		float y = position.y;
		
		//vertical  position of the bullets
		System.out.println(Math.toDegrees(angle));
		x += 27*(float) Math.cos(angle);
		y += -27*(float) Math.sin(angle);

		Vector2 newPosition = new Vector2(x,y);
		b.setPosition(newPosition);
		b.setRotation(angle);
	}
	public void releaseTrigger() {
		this.triggerStatus = false;
	}
}
