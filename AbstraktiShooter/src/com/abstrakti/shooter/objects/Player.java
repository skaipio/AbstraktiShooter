package com.abstrakti.shooter.objects;

import com.abstrakti.shooter.components.CControlledMovement;
import com.abstrakti.shooter.components.CMapPlace;
import com.abstrakti.shooter.components.CSprite;
import com.abstrakti.shooter.components.Component;

public class Player extends GameObject {
	private static Player player;
	private float speed = 100f; //per second
	
	private Player(){
		CMapPlace place = new CMapPlace(10, 10);
		this.addComponent(place);
		
		CControlledMovement movement = new CControlledMovement(place);
		movement.setSpeed(speed);
		this.addComponent(movement);
		
		CSprite sprite = new CSprite(place);
		sprite.setTextureRegion("entities/testPlayer-01");
		this.addComponent(sprite);
	}
	
	public static Player getInstance(){
		if (player == null){
			player = new Player();
		}
		return player;
	}
	
	public void handleInput(){
		// jos Hiiren vasen nappi painettu, niin luo Luoti-entiteetti
	}
	
	public void setPosition(float x, float y){
		CMapPlace place = (CMapPlace)this.getComponent(Component.Type.MapPlace);
		place.setPlace(x, y);
	}
}
