package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.managers.AssetManager;

public class Pistol extends Weapon {

	public Pistol() {
		this.fireRate = 1f;
		this.sound = AssetManager.getInstance().getSound("pistolSound");
		this.firemode = WeaponFiremode.SINGLE;
		this.damage = 1;
		this.numberOfBulletsPerShot = 1;
		this.reloadingTime = 1;
	}
}
