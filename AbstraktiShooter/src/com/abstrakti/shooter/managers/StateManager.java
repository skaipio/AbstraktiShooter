package com.abstrakti.shooter.managers;

import com.abstrakti.shooter.states.State;

public interface StateManager {
	void pushState(State state);
	State popState(State state);
}
