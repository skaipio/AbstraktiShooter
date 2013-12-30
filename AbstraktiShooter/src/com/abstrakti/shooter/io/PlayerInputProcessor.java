package com.abstrakti.shooter.io;

import com.badlogic.gdx.InputProcessor;

public class PlayerInputProcessor implements InputProcessor {
	private boolean[] KEYS = new boolean[603];
	private boolean[] MOUSEBUTTONS = new boolean[3];

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
	public boolean[] getMouseButtons() {
		return this.MOUSEBUTTONS;
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
		MOUSEBUTTONS[button] = true;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		MOUSEBUTTONS[button] = false;
		return true;
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