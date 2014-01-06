package com.abstrakti.shooter.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class AssetManager {
	private static AssetManager manager;
	private TextureAtlas entityAtlas;
	private TextureAtlas tileAtlas;
	private Sound pistolSound;
	private Sound bulletWallSound;
	private Sound bulletFleshSound;
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
	
	public void loadSounds(){
		pistolSound = Gdx.audio.newSound(Gdx.files.internal("sound-effects/bullet_launch.wav"));
		bulletWallSound = Gdx.audio.newSound(Gdx.files.internal("sound-effects/bullet_hit_wall.wav"));
		bulletFleshSound = Gdx.audio.newSound(Gdx.files.internal("sound-effects/bullet_hit_flesh.wav"));
	}
	
	/**
	 * Note: Remember to load the sprite sheet before using this method.
	 */
	public TextureRegion getEntityTexture(String name){
		// TODO: Add default "not-found" region
		return this.entityAtlas.findRegion(name);	
	}
	public TextureRegion getTileTexture(String name){
		// TODO: Add default "not-found" region
		return this.tileAtlas.findRegion(name);	
	}
	
	public Sound getPistolSound(){
		return this.pistolSound;
	}
	
	public Sound getbulletWallSound(){
		return this.bulletWallSound;
	}
	public Sound getbulletFleshSound() {
		return this.bulletFleshSound;
	}
}
