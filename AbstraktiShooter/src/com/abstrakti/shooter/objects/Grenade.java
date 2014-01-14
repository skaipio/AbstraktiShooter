package com.abstrakti.shooter.objects;

import com.badlogic.gdx.math.Vector2;

public class Grenade extends Explosive {

	public Grenade(Vector2 position) {
		super(position);
		this.throwable = true;
		this.radius = 10;
		this.timer = 5;
		this.damage = 10;
	}
	

}
