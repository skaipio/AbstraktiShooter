package com.abstrakti.shooter;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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
		
//		TexturePacker2.process(settings, "textures", "../AbstraktiShooter-android/assets", "textureAtlas");
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "AbstraktiShooter";
		cfg.useGL20 = false;

		cfg.width = 1024;
		cfg.height = 768;
		
		new LwjglApplication(new Game(), cfg);
	}
}
	
