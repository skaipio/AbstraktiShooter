package com.abstrakti.shooter.triggers;

import com.abstrakti.shooter.objects.Player;

public class EndOfLevelTrigger extends Trigger {

	@Override
	public void execute(Player collisionWith) {
		System.out.println("You got to the end alive! Go you!");
		
	}


}
