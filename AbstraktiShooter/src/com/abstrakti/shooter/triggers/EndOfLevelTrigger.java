package com.abstrakti.shooter.triggers;

import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.states.PlayerState;

public class EndOfLevelTrigger extends Trigger {
	@Override
	public void contact(GameObject collider) {
		Player player = (Player) collider;
		if (player != null) {
			System.out.println("You got to the end alive! Go you!");
			player.setStatus(PlayerState.DEAD);
		}
	}

	@Override
	public void endOfContact(GameObject collider) {
		// TODO Auto-generated method stub
		
	}
}
