package com.abstrakti.shooter.components;

import com.abstrakti.shooter.managers.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CSprite extends Component {
	private Sprite sprite;
	private CMapPlace place;

	public CSprite(CMapPlace place) {
		super(Component.Type.Sprite);
		this.place = place;
		this.sprite = new Sprite();
	}
	
	public void setTextureRegion(String name){
		TextureRegion region = AssetManager.getInstance().getTextureRegion(name);
		sprite.setRegion(region);
		sprite.setBounds(0, 0, region.getRegionWidth(), region.getRegionHeight());
	}
	
	@Override
	public void update(float deltaTime){
		this.sprite.setPosition(place.getX(), place.getY());
	}
	
	@Override
	public void draw(SpriteBatch batch){
		// TODO: Sprite bounds need to be set before drawing
		this.sprite.draw(batch);
	}

}
