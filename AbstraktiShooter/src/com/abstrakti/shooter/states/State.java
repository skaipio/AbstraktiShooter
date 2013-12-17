package com.abstrakti.shooter.states;

import com.abstrakti.shooter.StateManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State implements ApplicationListener {
	protected StateManager stateManager;
	
	protected State(StateManager manager){
		this.stateManager = manager;
	}
	
	public static State createMainGameState(StateManager manager, SpriteBatch batch){
		State state = new MainGameState(manager, batch);
		state.create();
		return state;
	}
	
	@Override
	public abstract void create();

	@Override
	public abstract void dispose();

	@Override
	public abstract void render();

	@Override
	public abstract void resize(int width, int height);

	@Override
	public abstract void pause();

	@Override
	public abstract void resume();
}
