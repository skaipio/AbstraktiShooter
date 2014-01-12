package com.abstrakti.shooter;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.maxHeight = 512;
		settings.maxWidth = 512;
		settings.paddingX = 0;
		settings.paddingY = 0;
		settings.duplicatePadding = true;
		
		//settings.filterMag = TextureFilter.Linear;
		//settings.filterMin = TextureFilter.Linear;
	    
	//	TexturePacker2.process(settings, "textures/tiles", "../AbstraktiShooter-android/assets", "tileAtlas");
//		TexturePacker2.process(settings, "textures/entities", "../AbstraktiShooter-android/assets", "entityAtlas");
		//TexturePacker2.process(settings, "textures/tileset", "../AbstraktiShooter-android/assets", "tileset");
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "AbstraktiShooter";
		cfg.useGL20 = true;


		cfg.width = 1024;
		cfg.height = 768;
		cfg.vSyncEnabled = true;
		cfg.resizable = false;

		new LwjglApplication(new Game(), cfg);
	}

	
}
	
