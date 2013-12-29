package com.abstrakti.shooter.animations;

import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerRunAnimation{
	private Player owner;
	private Animation runAnimation;
	private TextureRegion[] runFrames = new TextureRegion[4];
	private TextureRegion currentFrame;
	private float stateTime = 0f;
	
	
	public PlayerRunAnimation(Player owner){
		this.owner = owner;
		AssetManager assets = AssetManager.getInstance();
		this.runFrames[0] = assets.getTextureRegion("entities/player_pistol_leftstep");
		this.runFrames[1] = assets.getTextureRegion("entities/player_pistol_standing");
		this.runFrames[2] = assets.getTextureRegion("entities/player_pistol_rightstep");
		this.runFrames[3] = this.runFrames[1];
		this.runAnimation = new Animation(0.15f, this.runFrames);
	}
	
	public void update(float deltaTime){
		this.stateTime += deltaTime;
		this.currentFrame = this.runAnimation.getKeyFrame(stateTime, true);		
	}
	
	public void draw(SpriteBatch batch){
		Vector2 position = this.owner.getPosition();	
		batch.draw(currentFrame, position.x, position.y);
	}
	
	public void reset(){
		this.stateTime = 0;
	}
	
}
