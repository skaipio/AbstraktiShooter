package com.abstrakti.shooter.managers;

import java.util.HashMap;
import java.util.Map;

import com.abstrakti.shooter.io.StaticDrawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class AssetManager {
	private static AssetManager manager;
	private TextureAtlas entityAtlas;
	private TextureAtlas tileAtlas;
	private Map<String, Sprite> spriteMap = new HashMap<String, Sprite>();
	private Sound pistolSound;
	private Sound bulletWallSound;
	private Sound bulletFleshSound;
	
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
		for (AtlasRegion region : this.entityAtlas.getRegions()) {
			System.out.println(region.name);
			Sprite sprite = new Sprite();
			sprite.setRegion(region);
			int width = region.getRegionWidth();
			int height = region.getRegionHeight();
			sprite.setOrigin(width/2,height/2);
			sprite.setBounds(0, 0, width, height);
			this.spriteMap.put(region.name, sprite);
		}
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
	
	public Sprite getSprite(String name){
		return this.spriteMap.get(name);
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
