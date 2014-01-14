package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.managers.AssetManager;

public class Machinegun extends Weapon {

	public Machinegun() {
		this.sound = AssetManager.getInstance().getSound("pistolSound");
		this.fireRate = 0.005f;
		this.firemode = WeaponFiremode.CONTINUOS;
		this.damage = 0.5f;
		this.numberOfBulletsPerShot = 1;
		this.reloadingTime = 1;
	}
}
