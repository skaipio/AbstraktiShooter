package com.abstrakti.shooter.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class AssetManager {
	private static AssetManager manager;
	private TextureAtlas entityAtlas;
	private TextureAtlas tileAtlas;
	//private Texture textureSheet;
	
	private AssetManager(){}
	
	
	public static AssetManager getInstance(){
		if (manager == null){
			manager = new AssetManager();
		}
		return manager;
	}
	
	public void loadSpriteSheet(){
		this.entityAtlas = new TextureAtlas(Gdx.files.internal("../AbstraktiShooter-android/assets/entityAtlas.atlas"));
		this.tileAtlas = new TextureAtlas(Gdx.files.internal("../AbstraktiShooter-android/assets/tileAtlas.atlas"));
	}
	
	/**
	 * Note: Remember to load the sprite sheet before using this method.
	 */
	public TextureRegion getTextureRegion(String name){
		// TODO: Add default "not-found" region
		return this.entityAtlas.findRegion(name);	
	}
}
