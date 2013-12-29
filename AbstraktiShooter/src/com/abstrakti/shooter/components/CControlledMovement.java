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
			}
			
			else if (Gdx.input.isKeyPressed(Keys.D)) {
			}else{
				this.movementVector.x = 0;
			}
				
			if (Gdx.input.isKeyPressed(Keys.W)) {
				this.movementVector.x = (float) Math.cos(this.player.getAngle());
				this.movementVector.y = -(float) Math.sin(this.player.getAngle());
			}
			
			else if(Gdx.input.isKeyPressed(Keys.S)) {
				this.movementVector.x = -(float) Math.cos(this.player.getAngle());
				this.movementVector.y = (float) Math.sin(this.player.getAngle());
			}else{
				this.movementVector.y = 0;
			}
			this.movementVector.nor();
			this.movementVector.scl(deltaTime*speed);
			this.player.setVelocity(this.movementVector);
		}
		
		this.player.setRotation(calculatePlayerAngle(Gdx.input.getX(), Gdx.input.getY(), 1024/2, 768/2));
	}
	
	private float calculatePlayerAngle(int mouseX, int mouseY, float playerX, float playerY) {
		//System.out.println(Math.toDegrees((float)Math.atan2((float)mouseY-playerY, (float)mouseX-playerX)));
		return ((float)Math.atan2((float)mouseY-playerY, (float)mouseX-playerX));
	}

	@Override
	public void draw(SpriteBatch batch) {}
}
