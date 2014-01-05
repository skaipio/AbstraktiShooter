package com.abstrakti.shooter.map;

import java.util.Random;

import com.abstrakti.shooter.managers.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class TileFactory {
	private static Random rng = new Random();
	public static Tile createConcreteFloorTile(){
		AssetManager assets = AssetManager.getInstance();
		int randomTexture = rng.nextInt(2);
		TextureRegion texture = null;
		switch(randomTexture){
		case 0:
			texture = assets.getTileTexture("floor_concrete1");
			break;
		case 1:
			texture = assets.getTileTexture("floor_concrete2");
			break;
		}
		Tile floor = new Tile(texture);
		floor.setId(0);
		return floor;
	}
	
	public static Tile createConcreteUpperWallTile(){
		AssetManager assets = AssetManager.getInstance();
		int randomTexture = rng.nextInt(2);
		TextureRegion texture = null;
		switch(randomTexture){
		case 0:
			texture = assets.getTileTexture("wall_concrete_upper1");
			break;
		case 1:
			texture = assets.getTileTexture("wall_concrete_upper2");
			break;
		}
		Tile wall = new Tile(texture);
		wall.setId(1);
		return wall;
	}
}
