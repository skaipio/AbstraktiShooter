package com.abstrakti.shooter.io;

import com.abstrakti.shooter.objects.EnemyAiState;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.PlayerState;

public class AiController {
	private Player puppet, humanPlayer;
	private EnemyAiState currentState;
	private float observationRadius;

	public AiController(Player puppet, Player humanPlayer) {
		this.puppet = puppet;
		this.humanPlayer = humanPlayer;
		this.currentState = EnemyAiState.IDLE;
		this.observationRadius = 100;
	}
		
	public void act() {
		while(puppet.getStatus() != PlayerState.DEAD) {
			switch (this.currentState) {
			case IDLE:
				idle();
			case ATACKKING:
				attack();
			}
		}
	}
	
	public Player getPuppet(){
		return this.puppet;
	}
	
	private void idle() {
		if ( isHumanPlayerClose() == true) {
			this.currentState = EnemyAiState.ATACKKING;
		}
	}
	
	private boolean isHumanPlayerClose() {
		double playerDistance = 10;
		if (playerDistance < this.observationRadius) {
			return true;
		} else { 
			return false;
		}
	}
	
	private void attack() {
		turnToPlayer();
		puppet.shoot();
	}
	
	private void turnToPlayer() {
		puppet.setRotation(calculatePlayerAngle(humanPlayer.getX(), humanPlayer.getY(), puppet.getX(),puppet.getY()));
	}
	
	private float calculatePlayerAngle(float mouseX, float mouseY, float playerX, float playerY) {
		return ((float)Math.atan2((float)mouseY-playerY, (float)mouseX-playerX));
	}
	
}
