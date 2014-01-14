package com.abstrakti.shooter.io;

import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.states.EnemyAiState;
import com.abstrakti.shooter.states.PlayerState;
import com.badlogic.gdx.physics.box2d.World;

/*
 * AiController controls a player class. It has simple states called attack and idle. While the class is in idle state 
 * it waits for the human player to get close enough. When its close enough the player starts to follow it by walking and strafing 
 * towards the human player. A direction to strafe is choosen randomly.  When human player gets too far from the Ai player, ai goes
 * back to idle state.
 */


public class AiController {
	private Player puppet, humanPlayer;
	private EnemyAiState currentState;
	private double observationRadius;
	private World physicsWorld;
	private VibilityCallback vision;
	private float visionLength;
	
	private static float defaultBulletDelay = 1;
	private static float time = 0;
	
	private int direction;

	public AiController(Player puppet, World physicsWorld) {
		this.puppet = puppet;
		puppet.setHealth(1);
		this.physicsWorld = physicsWorld;
		this.currentState = EnemyAiState.IDLE;
		this.observationRadius = 200;
		this.direction = chooseDirection();
		this.visionLength  = 20;
	}

	public void act(float delta) {
		if (puppet.getStatus() != PlayerState.DEAD) {	
			switch (this.currentState) {
			case IDLE:
				idle();
				break;
			case ATTACKING:
				attack(delta);
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

	private void attack(float delta) {
		turnToPlayer();
		puppet.stopMovement();
		time -= delta;
		if (time <= 0) {
				puppet.shoot(physicsWorld, delta);
				time = defaultBulletDelay;
				puppet.releaseTrigger();
				this.direction = chooseDirection();
		}
		puppet.moveForward(delta);
		if (direction == 1) {
			puppet.strafeLeft(delta); 
		} else {
			puppet.strafeRight(delta);
		}
			
		this.currentState = EnemyAiState.IDLE;
	}

	private double calculateDistance(float x1, float y1, float x2, float y2) {
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	private void follow() {
		
	}
	
	private void alarm() {
		
	}
	
	private boolean lookForPlayer() {
	//	Vector2 rayEndPoint = new Vector2(puppet.getPosition()+visionLength*(new Vector2( (float)Math.sin(currentRayAngle), (float)Math.cos(currentRayAngle))))
		// vision.reportRayFixture(humanPlayer, puppet.getPosition(), new Vector2(0,0), 1f);
	//	vision = reportRayFixture();
//		world.rayCast(callback, puppet.getPosition(),  humanPlayer.getPosition());
	
		return true;
	}
	private int chooseDirection() {
		return (int) (Math.floor(Math.random() * 2) + 1);
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
