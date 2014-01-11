package com.abstrakti.shooter.io;

import com.abstrakti.shooter.objects.DynamicObject;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class StaticDrawable implements Drawable {
	private Sprite sprite;
	private DynamicObject owner;
	private float rotation;
	private Vector2 position;
	
	public StaticDrawable(Sprite sprite, DynamicObject owner){
		this.sprite = sprite;
		this.owner = owner;
	}
	@Override
	public void update(float deltaTime) {
		this.position = this.owner.getPosition();		
		this.rotation = (float) -Math.toDegrees(this.owner.getAngle());
	}
	
	@Override
	public void draw(SpriteBatch batch){
		if (this.position == null) return;
		this.sprite.setPosition(this.position.x-sprite.getWidth()/2, this.position.y-sprite.getHeight()/2);
		this.sprite.setRotation(this.rotation);
		
		this.sprite.draw(batch);
	}
	@Override
	public void resetState() {}
}
