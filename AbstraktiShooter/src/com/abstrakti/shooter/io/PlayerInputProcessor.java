package com.abstrakti.shooter.io;

import com.badlogic.gdx.InputProcessor;

public class PlayerInputProcessor implements InputProcessor {
	private boolean[] KEYS = new boolean[603];

	@Override
	public boolean keyDown (int keycode) {
		KEYS[keycode] = true;
		return true;
	}

	@Override
	public boolean keyUp (int keycode) {
		KEYS[keycode] = false;
		return true;
	}
	public boolean[] getKeys() {
		return this.KEYS;
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}