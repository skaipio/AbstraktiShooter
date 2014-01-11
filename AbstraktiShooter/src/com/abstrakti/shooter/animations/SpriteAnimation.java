package com.abstrakti.shooter.animations;

import com.abstrakti.shooter.io.Drawable;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SpriteAnimation implements Drawable {
	private Sprite[] sprites;
	private int frames;
	protected float rotation;
	protected Vector2 position;
	private float frameTime;
	private float stateTime = 0f;
	
	public SpriteAnimation(int frames, float frameTime){
		this.sprites = new Sprite[frames];
		this.frames = frames;
		this.frameTime = frameTime;
	}
	
	public void addFrameAt(int index, Sprite frame){
		this.sprites[index] = frame;
	}
	
	public void update(float deltaTime){
		this.stateTime += deltaTime;
		this.stateTime = this.stateTime % (frameTime * frames);
	}
	
	public void draw(SpriteBatch batch){
		int frameIndex = (int) (this.stateTime / frameTime);
		float spriteWidth = sprites[frameIndex].getWidth();
		float spriteHeight = sprites[frameIndex].getHeight();
		this.sprites[frameIndex].setPosition(position.x-spriteWidth/2, position.y-spriteHeight/2);
		this.sprites[frameIndex].setRotation(this.rotation);		
		this.sprites[frameIndex].draw(batch);
	}

	@Override
	public void resetState() {
		this.stateTime = 0f;
	}
}
