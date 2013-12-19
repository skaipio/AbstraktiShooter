package com.abstrakti.shooter.states;

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
	private TiledMap map;
	private TmxMapLoader loader;
	private Player player;

	public MainGameState(StateManager manager, SpriteBatch batch) {
		super(manager);
	}

	@Override
	public void create() {
		AssetManager.getInstance().loadSpriteSheet();
		this.gameScreen = new GameScreen(new SpriteBatch()); 
		this.loader = new TmxMapLoader();
		this.map = loader.load("testlevel.tmx");
		this.gameScreen.setMap(map);
		this.player = new Player();
		this.gameScreen.lockCameraOn(this.player);
		//TiledMapTileLayer layer = (TiledMapTileLayer)this.map.getLayers().get(0);
	}

	@Override
	public void dispose() {
		this.gameScreen.dispose();
		this.map.dispose();
		this.loader = null;
	}

	@Override
	public void render() {
		this.update();
		this.gameScreen.render(Gdx.graphics.getDeltaTime());
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
