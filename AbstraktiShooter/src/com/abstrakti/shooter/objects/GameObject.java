package com.abstrakti.shooter.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {
	public abstract void update(float deltaTime);
	
	public abstract void draw(SpriteBatch batch);
}
