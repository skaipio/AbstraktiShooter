package com.abstrakti.shooter.triggers;

import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.PlayerState;

public class EndOfLevelTrigger extends Trigger {

	@Override
	public void execute(Player collisionWith) {
		System.out.println("You got to the end alive! Go you!");
		collisionWith.setStatus(PlayerState.DEAD);
	}
}
