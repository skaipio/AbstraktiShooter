package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.components.CControlledMovement;
import com.abstrakti.shooter.components.CMapPlace;
import com.abstrakti.shooter.components.CSprite;

public class Player extends GameObject {
	private float speed = 100f; //per second
	
	public Player(){
		CMapPlace place = new CMapPlace(10, 10);
		this.addComponent(place);
		
		CControlledMovement movement = new CControlledMovement(place);
		movement.setSpeed(speed);
		this.addComponent(movement);
		
		CSprite sprite = new CSprite(place);
		sprite.setTextureRegion("entities/testPlayer-01");
		this.addComponent(sprite);
	}
	
	public void handleInput(){
		// jos Hiiren vasen nappi painettu, niin luo Luoti-entiteetti
	}
}
