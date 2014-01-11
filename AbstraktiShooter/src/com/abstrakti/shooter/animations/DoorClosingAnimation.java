package com.abstrakti.shooter.animations;

import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.objects.Door;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class DoorClosingAnimation extends SpriteAnimation {
	private Door owner;

	public DoorClosingAnimation(Door owner) {
		super(4, 0.1f);
		this.owner = owner;
		AssetManager assets = AssetManager.getInstance();
		Sprite door = assets.getSprite("slideDoor5");
		super.addFrameAt(0, door);
		door = assets.getSprite("slideDoor4");
		super.addFrameAt(1, door);
		door = assets.getSprite("slideDoor3");
		super.addFrameAt(2, door);
		door = assets.getSprite("slideDoor2");
		super.addFrameAt(3, door);
	}

	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		this.position = this.owner.getPosition();		
		this.rotation = -(float)Math.toDegrees(this.owner.getAngle());
	}
}
