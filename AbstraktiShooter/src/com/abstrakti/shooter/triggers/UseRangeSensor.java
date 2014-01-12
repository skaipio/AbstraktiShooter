package com.abstrakti.shooter.triggers;

import java.util.SortedSet;
import java.util.TreeSet;

import com.abstrakti.shooter.objects.Door;
import com.abstrakti.shooter.objects.DynamicObject;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.util.RangeComparator;

public class UseRangeSensor extends Trigger {
	private SortedSet<Door> doorsInRange;

	public UseRangeSensor(DynamicObject owner) {
		this.doorsInRange = new TreeSet<Door>(new RangeComparator(owner));
	}

	@Override
	public void contact(GameObject collider) {
		if (collider instanceof Door) {
			Door door = (Door) collider;
			this.doorsInRange.add(door);
		}
	}

	@Override
	public void endOfContact(GameObject collider) {
		if (collider instanceof Door) {
			Door door = (Door) collider;
			this.doorsInRange.remove(door);
		}
	}

	public void useClosestDoor() {
		if (!this.doorsInRange.isEmpty())
			this.doorsInRange.first().use();
	}

}