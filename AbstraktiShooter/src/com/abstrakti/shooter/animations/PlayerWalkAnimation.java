package com.abstrakti.shooter.animations;

import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.objects.Bullet;
import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayerWalkAnimation extends SpriteAnimation{
	private Player owner;
	public PlayerWalkAnimation(Player owner){
		super(4, 0.15f);
		this.owner = owner;
		AssetManager assets = AssetManager.getInstance();
		Sprite playerStanding = assets.getSprite("player_pistol_standing");
		Sprite playerLeftstep = assets.getSprite("player_pistol_leftstep");
		Sprite playerRightstep = assets.getSprite("player_pistol_rightstep");
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
