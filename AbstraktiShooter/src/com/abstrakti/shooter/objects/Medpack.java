package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.io.Drawable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Medpack extends DynamicObject {
	private int amount;
	private Vector2 initPosition;
	private PlayerState status;
	
	public Medpack(Vector2 position) {
		this.initPosition = position;
		this.amount = 10;
		
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
