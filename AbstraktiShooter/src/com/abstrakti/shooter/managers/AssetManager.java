package com.abstrakti.shooter.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class AssetManager {
	private static AssetManager manager;
	private TextureAtlas atlas;
	//private Texture textureSheet;
	
	private AssetManager(){}
	
	
	public static AssetManager getInstance(){
		if (manager == null){
			manager = new AssetManager();
		}
		return manager;
	}
	
	public void loadSpriteSheet(){
		//this.textureSheet = new Texture(Gdx.files.internal("../AbstraktiShooter-android/assets/textureAtlas.png"));
		this.atlas = new TextureAtlas(Gdx.files.internal("../AbstraktiShooter-android/assets/textureAtlas.atlas"));
		for (Texture texture : this.atlas.getTextures()) {
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	/**
	 * Note: Remember to load the sprite sheet before using this method.
	 */
	public TextureRegion getTextureRegion(String name){
		// TODO: Add default "not-found" region
		return this.atlas.findRegion(name);	
	}
}
