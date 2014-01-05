package com.abstrakti.shooter.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Weapon {
	private WeaponFiremode firemode;
	private boolean triggerStatus;
	private int ammo;
	private int MAXAMMO = 100;
	private Sound sound;
	
	public Weapon(WeaponFiremode firemode) {
		this.triggerStatus = false;
		this.firemode = firemode;
		sound = Gdx.audio.newSound(Gdx.files.internal("barreta_m9-Dion_Stapper-1010051237.wav"));
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
	public boolean fireGun() {
		if (this.ammo == 0) {
			return false;
		} else if (this.firemode == WeaponFiremode.SINGLE && this.triggerStatus == true) {
			return false;
		} else {
			this.triggerStatus = true;
			this.ammo--;
			System.out.println("BANG, ammo" + " " + ammo);
			sound.play(1.0f);
			return true;
		}
	}
	public void releaseTrigger() {
		this.triggerStatus = false;
	}
}
