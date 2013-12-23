package com.abstrakti.shooter.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class CControlledMovement extends Component {
	private float speed;
	private CMapPlace mapPlace;

	public CControlledMovement(CMapPlace place) {
		super(Type.ControlledMovement);
		this.mapPlace = place;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}

	@Override
	public void update(float deltaTime){
		if (speed != 0f){
			Vector2 movement = new Vector2();
			if (Gdx.input.isKeyPressed(Keys.A)) {	
				movement.add(-1, 0);
			}
			if (Gdx.input.isKeyPressed(Keys.D)) {
				movement.add(1, 0);
			}
			if (Gdx.input.isKeyPressed(Keys.W)) {
				movement.add(0, 1);
			}
			if (Gdx.input.isKeyPressed(Keys.S)) {
				movement.add(0, -1);
			}
			movement.scl(deltaTime*speed);
			this.mapPlace.getPlace().add(movement);
		}
	}
	
	@Override
	public String toString(){
		return "Controlled movement component";
	}
}
