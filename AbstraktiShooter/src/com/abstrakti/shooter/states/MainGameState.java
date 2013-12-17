package com.abstrakti.shooter.states;

import com.abstrakti.shooter.MainGameScreen;
import com.abstrakti.shooter.StateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class MainGameState extends State {
	private SpriteBatch batch;
	private Screen mainGameScreen;

	public MainGameState(StateManager manager, SpriteBatch batch) {
		super(manager);
		this.batch = batch;
	}

	@Override
	public void create() {
		this.mainGameScreen = new MainGameScreen(batch);
	}

	@Override
	public void dispose() {
		this.mainGameScreen.dispose();
	}

	@Override
	public void render() {
		this.mainGameScreen.render(Gdx.graphics.getDeltaTime());
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
