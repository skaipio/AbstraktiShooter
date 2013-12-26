package com.abstrakti.shooter.components;

import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.MapObject;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CSprite extends Component {
	private Sprite sprite;
	private GameObject object;

	public CSprite(GameObject parent) {
		super(ComponentType.Sprite);
		this.sprite = new Sprite();
		this.object = parent;
	}
	
	public void setTextureRegion(String name){
		TextureRegion region = AssetManager.getInstance().getTextureRegion(name);
		sprite.setRegion(region);
		sprite.setBounds(0, 0, region.getRegionWidth(), region.getRegionHeight());
	}
	
	@Override
	public void update(float deltaTime){	
		MapObject physObj = (MapObject)this.object;
		if (physObj != null){
			this.sprite.setPosition(physObj.getPosition().x, physObj.getPosition().y);
		}
	}
	
	@Override
	public void draw(SpriteBatch batch){
		// Sprite bounds need to be set before drawing
		this.sprite.draw(batch);
	}

}
