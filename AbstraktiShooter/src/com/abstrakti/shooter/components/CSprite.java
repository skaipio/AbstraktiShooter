package com.abstrakti.shooter.components;

import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.objects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class CSprite extends Component {
	private Sprite sprite;
	private CMapPlace place;
	private GameObject object;

	public CSprite(CMapPlace place, GameObject object) {
		super(Component.Type.Sprite);
		this.place = place;
		this.sprite = new Sprite();
		this.object = object;
	}
	
	public void setTextureRegion(String name){
		TextureRegion region = AssetManager.getInstance().getTextureRegion(name);
		sprite.setRegion(region);
		sprite.setBounds(0, 0, region.getRegionWidth(), region.getRegionHeight());
	}
	
	@Override
	public void update(float deltaTime){
		//this.sprite.setPosition(place.getX(), place.getY());
		this.sprite.setPosition(object.getBody().getPosition().x*100, object.getBody().getPosition().y*100);
	}
	
	@Override
	public void draw(SpriteBatch batch){
		// TODO: Sprite bounds need to be set before drawing
		this.sprite.draw(batch);
	}

}
