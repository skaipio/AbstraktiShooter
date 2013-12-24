package com.abstrakti.shooter.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Component {
	public final Type TYPE;

	public Component(Type type) {
		this.TYPE = type;
	}

	public void update(float deltaTime) {
	}
	
	public void draw(SpriteBatch batch) {}

	public enum Type {
		MapPlace(0), ControlledMovement(1), Sprite(2);

		public final int ID;

		private Type(int id) {
			this.ID = id;
		}
	}
}
