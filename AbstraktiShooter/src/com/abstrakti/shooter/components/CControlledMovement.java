package com.abstrakti.shooter.components;

import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class CControlledMovement extends Component {
	private float speed;
	private Player player;
	public final Vector2 movementVector = Vector2.Zero;

	public CControlledMovement(Player player) {
		super(ComponentType.ControlledMovement);
		this.player = player;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}

	@Override
	public void update(float deltaTime){
		if (speed != 0f){
			if (Gdx.input.isKeyPressed(Keys.A)) {
				this.movementVector.x = -1;
			}
			
			else if (Gdx.input.isKeyPressed(Keys.D)) {
				this.movementVector.x = 1;
			}else{
				this.movementVector.x = 0;
			}
				
			if (Gdx.input.isKeyPressed(Keys.W)) {
				this.movementVector.y = 1;
			}
			
			else if(Gdx.input.isKeyPressed(Keys.S)) {
				this.movementVector.y = -1;
			}else{
				this.movementVector.y = 0;
			}
			this.movementVector.nor();
			this.movementVector.scl(deltaTime*speed);
			this.player.setVelocity(this.movementVector);
		}
	}

	@Override
	public void draw(SpriteBatch batch) {}
}
