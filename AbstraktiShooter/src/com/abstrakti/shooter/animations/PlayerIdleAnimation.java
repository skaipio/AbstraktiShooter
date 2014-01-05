package com.abstrakti.shooter.animations;

import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayerIdleAnimation extends SpriteAnimation {
	private Player owner;

	public PlayerIdleAnimation(Player owner) {
		super(1, 1f);
		this.owner = owner;
		AssetManager assets = AssetManager.getInstance();
		Sprite playerStanding = new Sprite(assets.getEntityTexture("player_pistol_standing"));
		playerStanding.setOrigin(playerStanding.getRegionWidth()/2, playerStanding.getRegionHeight()/2);
		super.addFrameAt(0, playerStanding);
	}

	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		this.position = this.owner.getPosition();		
		this.rotation = (float) -Math.toDegrees(this.owner.getAngle());
	}	
}
