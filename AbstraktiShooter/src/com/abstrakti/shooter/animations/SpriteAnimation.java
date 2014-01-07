package com.abstrakti.shooter.animations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SpriteAnimation {
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
	
	public void reset(){
		this.stateTime = 0f;
	}
	
	public void update(float deltaTime){
		this.stateTime += deltaTime;
		this.stateTime = this.stateTime % (frameTime * frames);
	}
	
	public void draw(SpriteBatch batch){
		int frameIndex = (int) (this.stateTime / frameTime);
		this.sprites[frameIndex].setPosition(position.x, position.y);
		this.sprites[frameIndex].setRotation(this.rotation);		
		this.sprites[frameIndex].draw(batch);
	}
}
