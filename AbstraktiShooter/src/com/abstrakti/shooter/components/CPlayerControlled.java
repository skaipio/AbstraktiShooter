package com.abstrakti.shooter.components;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class CPlayerControlled extends Component {
	private float speed;
	private Player player;
	public final Vector2 movementVector = Vector2.Zero;

	public CPlayerControlled(Player player) {
		super(ComponentType.PlayerControlled);
		this.player = player;
		this.speed = player.getSpeed();
	}
	


	@Override
	public void update(float deltaTime){
		if (speed != 0f){
			this.movementVector.x = 0;
			this.movementVector.y = 0;				
			if (Gdx.input.isKeyPressed(Keys.W)) {
				this.player.moveForward(movementVector);
			}
			if (Gdx.input.isKeyPressed(Keys.A)) {
				this.player.strafeRight(movementVector);
			}
			if (Gdx.input.isKeyPressed(Keys.D)) {
				this.player.strafeLeft(movementVector);
			}
			else if(Gdx.input.isKeyPressed(Keys.S)) {
				this.player.moveBackward(movementVector);
			}
			this.movementVector.nor();
			this.movementVector.scl(deltaTime*speed);
			this.player.setVelocity(this.movementVector);
		}
		
		this.player.setRotation(calculatePlayerAngle(Gdx.input.getX(), Gdx.input.getY(), Config.SCREEN_WIDTH/2, Config.SCREEN_HEIGHT/2));
	}
	
	private float calculatePlayerAngle(int mouseX, int mouseY, float playerX, float playerY) {
		//System.out.println(Math.toDegrees((float)Math.atan2((float)mouseY-playerY, (float)mouseX-playerX)));
		return ((float)Math.atan2((float)mouseY-playerY, (float)mouseX-playerX));
	}

	@Override
	public void draw(SpriteBatch batch) {}
}
