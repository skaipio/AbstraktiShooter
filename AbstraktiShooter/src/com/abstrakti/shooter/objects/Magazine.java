package com.abstrakti.shooter.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Magazine extends DynamicObject {

	
	
	public Magazine() {
		super(PlayerState.values().length);
		// TODO Auto-generated constructor stub
	}

	public void draw(SpriteBatch batch){
		Texture t = new Texture(Gdx.files.internal("../AbstraktiShooter-desktop/textures/entities/bullet.png"));
		batch.draw(t, this.getPosition().x, this.getPosition().y);
	}
}
