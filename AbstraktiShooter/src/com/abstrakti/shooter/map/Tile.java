package com.abstrakti.shooter.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

public class Tile implements TiledMapTile {
	private int id;
	private TextureRegion texture;
	
	Tile(TextureRegion texture){
		this.id = id;
		this.texture = texture;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public BlendMode getBlendMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBlendMode(BlendMode blendMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public TextureRegion getTextureRegion() {
		return this.texture;
	}

	@Override
	public MapProperties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

}
