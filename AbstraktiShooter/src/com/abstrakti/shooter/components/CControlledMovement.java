package com.abstrakti.shooter.components;

import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class CControlledMovement extends Component {
	private float speed;
	private CMapPlace mapPlace;
	private Player player;
	private boolean a, s, d, w;

	public CControlledMovement(CMapPlace place, Player player) {
		super(Type.ControlledMovement);
		this.mapPlace = place;
		this.player = player;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}

	@Override
	public void update(float deltaTime){
		if (speed != 0f){
			Vector2 movement = new Vector2();
			
			if (Gdx.input.isKeyPressed(Keys.A)) {
				//body.setLinearVelocity(-1.0f, 0.0f);
				
				//movement.add(-1, 0);
			}
			
			if (Gdx.input.isKeyPressed(Keys.D)) {
				//body.setLinearVelocity(1.0f, 0.0f);
				//movement.add(1, 0);
			}
				
			if (Gdx.input.isKeyPressed(Keys.W)) {
				//movement.add(0, 1);
			}
			
			if(Gdx.input.isKeyPressed(Keys.S)) {
				//movement.add(0, -1);
			}
			
			if (a) {
				if (Gdx.input.isKeyPressed(Keys.A) == false) {
					player.controllerVector.x += 1;
					a = false;
				}
			}
			
			if (a == false) {
				if (Gdx.input.isKeyPressed(Keys.A)) {
					player.controllerVector.x += -1;
					a = true;
				}
			}
			
			if (d) {
				if (Gdx.input.isKeyPressed(Keys.D) == false) {
					player.controllerVector.x += -1;
					d = false;
				}
			}
			
			if (d == false) {
				if (Gdx.input.isKeyPressed(Keys.D)) {
					player.controllerVector.x += +1;
					d = true;
				}
			}
			
			if (s) {
				if (Gdx.input.isKeyPressed(Keys.S) == false) {
					player.controllerVector.y += 1;
					s = false;
				}
			}
			
			if (s == false) {
				if (Gdx.input.isKeyPressed(Keys.S)) {
					player.controllerVector.y += -1;
					s = true;
				}
			}
			
			
			if (w) {
				if (Gdx.input.isKeyPressed(Keys.W) == false) {
					player.controllerVector.y += -1;
					w = false;
				}
			}
			
			if (w == false) {
				if (Gdx.input.isKeyPressed(Keys.W)) {
					player.controllerVector.y += 1;
					w = true;
				}
			}
									
			//movement.scl(deltaTime*speed);
			this.mapPlace.setPlace(player.getBody().getPosition().x*100, player.getBody().getPosition().y*100);
		}
	}
	
	@Override
	public String toString(){
		return "Controlled movement component";
	}
}
