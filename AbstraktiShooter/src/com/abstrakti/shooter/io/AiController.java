package com.abstrakti.shooter.io;

import com.abstrakti.shooter.objects.EnemyAiState;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.PlayerState;

public class AiController {
	private Player puppet, humanPlayer;
	private EnemyAiState currentState;
	private double observationRadius;

	public AiController(Player puppet) {
		this.puppet = puppet;
		this.currentState = EnemyAiState.IDLE;
		this.observationRadius = 200;
	}

	public void act() {
		if (puppet.getStatus() != PlayerState.DEAD) {	
			switch (this.currentState) {
			case IDLE:
				idle();
				break;
			case ATTACKING:
				attack();
				break;
			default:
				idle();
				break;
			}
		}
	}

	public void setPlayer(Player humanPlayer) {
		this.humanPlayer = humanPlayer;
	}

	public Player getPuppet(){
		return this.puppet;
	}

	private void idle() {
		if ( isHumanPlayerClose() == true) {
			this.currentState = EnemyAiState.ATTACKING;
		} else {
			this.puppet.stopMovement();
		}
	}

	private boolean isHumanPlayerClose() {
		double playerDistance = calculateDistance(humanPlayer.getX(), humanPlayer.getY(), puppet.getX(),puppet.getY());
		if (playerDistance < this.observationRadius) {
			return true;
		} else { 
			return false;
		}
	}

	private void attack() {
		turnToPlayer();
		puppet.shoot();
		puppet.moveForward(1);
		this.currentState = EnemyAiState.IDLE;
	}

	private double calculateDistance(float x1, float y1, float x2, float y2) {
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}

	private void turnToPlayer() {
		this.puppet.setRotation(-calculatePlayerAngle(humanPlayer.getX(), humanPlayer.getY(), this.puppet.getX(), this.puppet.getY()));
	}

	private float calculatePlayerAngle(float mouseX, float mouseY, float playerX, float playerY) {
		return ((float)Math.atan2(mouseY-playerY, mouseX-playerX));
	}

	public PlayerState getPuppetState() {
		return this.puppet.getStatus();
	}

	public PlayerState getPlayerState() {
		return this.humanPlayer.getStatus();
	}
}
