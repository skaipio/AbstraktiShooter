package com.abstrakti.shooter.components;

import com.abstrakti.shooter.animations.PlayerRunAnimation;
import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.DynamicObject;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.PlayerState;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class CSprite extends Component {
	private Sprite sprite;
	private Player owner;
	private PlayerRunAnimation runAnimation;
	private PlayerState previousPlayerState;

	public CSprite(Player owner) {
		super(ComponentType.Sprite);
		this.sprite = new Sprite();
		this.owner = owner;
		this.runAnimation = new PlayerRunAnimation(owner);
	}
	
	public void setTextureRegion(String name){
		TextureRegion region = AssetManager.getInstance().getTextureRegion(name);
		sprite.setRegion(region);
		sprite.setBounds(0, 0, region.getRegionWidth(), region.getRegionHeight());
		//sprite.setOrigin(region.getRegionWidth()/2, region.getRegionHeight()/2);
		sprite.setOrigin(7, region.getRegionHeight()/2);
	}
	
	@Override
	public void update(float deltaTime){	
		if (this.owner != null){
			this.sprite.setPosition(this.owner.getPosition().x, this.owner.getPosition().y);
			this.sprite.setRotation(-(this.owner.getAngle()* MathUtils.radiansToDegrees));
		}
		if (previousPlayerState != owner.getStatus()){
			this.runAnimation.reset();
		}
		this.runAnimation.update(deltaTime);
		previousPlayerState = owner.getStatus();
	}
	
	@Override
	public void draw(SpriteBatch batch){
		// Sprite bounds need to be set before drawing
		if (this.owner.getStatus() == PlayerState.WALKING){
			this.runAnimation.draw(batch);
		}else{
			this.sprite.draw(batch);
		}
		
	}

}
