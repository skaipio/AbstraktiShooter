package com.abstrakti.shooter;

import java.util.Stack;

import com.abstrakti.shooter.managers.StateManager;
import com.abstrakti.shooter.states.State;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game implements ApplicationListener, StateManager {
	private SpriteBatch batch;	
	private Stack<State> states = new Stack<State>();
	
	@Override
	public void create() {
		this.batch = new SpriteBatch();	
		this.states.push(State.createMainGameState(this, batch));
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		this.states.peek().render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void pushState(State state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State popState(State state) {
		// TODO Auto-generated method stub
		return null;
	}
}
