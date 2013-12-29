package com.abstrakti.shooter.animations;

import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerRunAnimation extends SpriteAnimation{
	private Player owner;

	public PlayerRunAnimation(Player owner){
		super(4, 0.15f);
		this.owner = owner;
		AssetManager assets = AssetManager.getInstance();
		Sprite playerStanding = new Sprite(assets.getTextureRegion("entities/player_pistol_standing"));
		Sprite playerLeftstep = new Sprite(assets.getTextureRegion("entities/player_pistol_leftstep"));
		Sprite playerRightstep = new Sprite(assets.getTextureRegion("entities/player_pistol_rightstep"));
		super.addFrameAt(0, playerLeftstep);
		super.addFrameAt(1, playerStanding);
		super.addFrameAt(2, playerRightstep);
		super.addFrameAt(3, playerStanding);
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		this.position = this.owner.getPosition();
		
		this.rotation = (float) -Math.toDegrees(this.owner.getAngle());
	}	
}
