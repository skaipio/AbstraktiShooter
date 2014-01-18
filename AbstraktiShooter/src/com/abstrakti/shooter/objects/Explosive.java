package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.io.Drawable;
import com.abstrakti.shooter.states.PlayerState;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Explosive extends DynamicObject {
	int radius;
	boolean throwable;
	float damage;
	private Vector2 initPosition;
	private PlayerState status;
	protected float timer;
	
	public Explosive() {
		this.status = PlayerState.IDLE;
	}
	
	public PlayerState getStatus(){
		return this.status;
	}
	
	public void kill() {
		this.status = PlayerState.DEAD;
	}
	
	public void explode() {
		
	}

	public float getDamage() {
		return this.damage;
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		Drawable drawable = this.getDrawable(0); 
		drawable.update(deltaTime);
	}

	@Override
	public void draw(SpriteBatch batch) {
		Drawable drawable = this.getDrawable(0);
		drawable.draw(batch);
	}

}
