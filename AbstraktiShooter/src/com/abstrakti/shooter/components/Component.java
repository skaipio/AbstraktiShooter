package com.abstrakti.shooter.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Component {
	public final ComponentType TYPE;

	public Component(ComponentType type) {
		this.TYPE = type;
	}

	public abstract void update(float deltaTime);
	
	public abstract void draw(SpriteBatch batch);
}
