package com.abstrakti.shooter.triggers;

import com.abstrakti.shooter.objects.DynamicObject;
import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Trigger extends DynamicObject {

	public Trigger() {
		super(1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(SpriteBatch batch){}
	
	public abstract void execute(Player collisionWith);
}
