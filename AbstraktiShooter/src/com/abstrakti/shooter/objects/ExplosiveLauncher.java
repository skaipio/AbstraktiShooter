package com.abstrakti.shooter.objects;

import java.util.ArrayList;

import com.abstrakti.shooter.managers.AssetManager;

public class ExplosiveLauncher extends Weapon {
	
	public ExplosiveLauncher() {
		this.fireRate = 1f;
		this.sound = AssetManager.getInstance().getSound("pistolSound");
		this.firemode = WeaponFiremode.SINGLE;
		this.damage = 1;
		this.numberOfProjectilesPerShot = 1;
		this.reloadingTime = 1;
		this.cartridge = new ArrayList<Grenade>();
	}

}
