package com.abstrakti.shooter.io;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Drawable {
	void update(float deltaTime);
	void draw(SpriteBatch batch);
	void resetState();
}
