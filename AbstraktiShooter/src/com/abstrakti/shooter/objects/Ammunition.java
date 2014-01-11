package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.io.Drawable;
import com.abstrakti.shooter.states.PlayerState;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ammunition extends DynamicObject {
	private int amount;
	private Vector2 initPosition;
	private PlayerState status;
	
	public Ammunition(Vector2 position, int amount) {
		this.initPosition = position;
		this.amount = amount;
		
	}
	
	public PlayerState getStatus(){
		return this.status;
	}
	
	public int withdraw() {
		int tmp = amount;
		amount = 0;
		this.status = PlayerState.DEAD;
		return tmp;
	}
	
	
	public Vector2 getInitPosition() {
		return this.initPosition;
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
