package com.abstrakti.shooter.states;

import com.abstrakti.shooter.Level;
import com.abstrakti.shooter.io.GameScreen;
import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.managers.StateManager;
import com.abstrakti.shooter.objects.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

class MainGameState extends State {
	private GameScreen gameScreen;
	private Player player;
	private Level currentLevel;

	public MainGameState(StateManager manager, SpriteBatch batch) {
		super(manager);
	}

	@Override
	public void create() {
		AssetManager.getInstance().loadSpriteSheet();
		this.currentLevel = new Level("testlevel.tmx");
		
		this.player = new Player();
		GameScreen.getInstance().lockCameraOn(this.player);
		//TiledMapTileLayer layer = (TiledMapTileLayer)this.map.getLayers().get(0);
	}

	@Override
	public void dispose() {
		this.gameScreen.dispose();
	}

	@Override
	public void render() {
		this.update();
		GameScreen.getInstance().render(Gdx.graphics.getDeltaTime());
	}

	private void update() {
		this.player.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
}
