package com.abstrakti.shooter.objects;

public class Weapon {
	private WeaponFiremode firemode;
	private boolean triggerStatus;
	private int ammo;
	private int MAXAMMO = 100;
	
	public Weapon(WeaponFiremode firemode) {
		this.triggerStatus = false;
		this.firemode = firemode;
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
		//	System.out.println("BANG, ammo" + " " + ammo);
			return true;
		}
	}
	public void releaseTrigger() {
		this.triggerStatus = false;
	}
}
