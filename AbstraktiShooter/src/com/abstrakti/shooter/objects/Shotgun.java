package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.managers.AssetManager;

public class Shotgun extends Weapon {

	Shotgun() {
		this.sound = AssetManager.getInstance().getSound("pistolSound");
		this.numberOfBulletsPerShot = 6;
		this.reloadingTime = 2;
		this.firemode = WeaponFiremode.SINGLE;
		this.damage = 0.5f;
		this.fireRate =1f;
	}
}
