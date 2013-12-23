package com.abstrakti.shooter.states;

import com.abstrakti.shooter.Level;
import com.abstrakti.shooter.io.GameScreen;
import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.managers.StateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class MainGameState extends State {
	private GameScreen gameScreen;
	private Level currentLevel;

	public MainGameState(StateManager manager, SpriteBatch batch) {
		super(manager);
	}

	@Override
	public void create() {
		AssetManager.getInstance().loadSpriteSheet();
		this.currentLevel = new Level("level1.tmx");
		this.currentLevel.init();
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
		this.currentLevel.update();
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
