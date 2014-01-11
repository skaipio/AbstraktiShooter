package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.io.Drawable;
import com.abstrakti.shooter.states.DoorState;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Door extends DynamicObject {
	private DoorState doorState;
	private float changeTimer = 0f;
	
	void setState(DoorState state){
		this.doorState = state;
		if (this.doorState == DoorState.CLOSING){
			this.getBody().setActive(true);
		}else if(this.doorState == DoorState.OPEN){
			this.getBody().setActive(false);
		}
		Drawable drawable = this.getDrawable(this.doorState.ordinal());
		if (drawable != null){
			drawable.resetState();
		}
	}

	@Override
	public void update(float deltaTime) {
		Drawable drawable = this.getDrawable(this.doorState.ordinal());
		if (drawable != null){
			drawable.update(deltaTime);
		}
		if (this.doorState == DoorState.CLOSING){
			if (this.changeTimer <= 0f){
				this.setState(DoorState.CLOSED);
				this.changeTimer = 0f;
			}else{
				this.changeTimer -= deltaTime;
			}
		}
		else if (this.doorState == DoorState.OPENING){
			if (this.changeTimer <= 0f){
				this.setState(DoorState.OPEN);
				this.changeTimer = 0f;
			}else{
				this.changeTimer -= deltaTime;
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch){
		Drawable drawable = this.getDrawable(this.doorState.ordinal());
		if (drawable != null){
			drawable.draw(batch);
		}
	}
	
	public void use(){
		if (this.doorState == DoorState.CLOSED){
			this.setState(DoorState.OPENING);
			this.changeTimer = 0.35f;
		}else if (this.doorState == DoorState.OPEN){
			this.setState(DoorState.CLOSING);
			this.changeTimer = 0.35f;
		}
	}
}
