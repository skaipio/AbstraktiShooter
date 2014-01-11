package com.abstrakti.shooter.triggers;

import com.abstrakti.shooter.objects.DynamicObject;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Trigger extends DynamicObject {

	@Override
	public void draw(SpriteBatch batch){}
	
	@Override
	public void update(float deltaTime) {}
	
	public abstract void contact(GameObject collider);
	public abstract void endOfContact(GameObject collider);
}
