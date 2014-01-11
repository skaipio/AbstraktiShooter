package com.abstrakti.shooter.util;

import java.util.Comparator;

import com.abstrakti.shooter.objects.Door;
import com.abstrakti.shooter.objects.DynamicObject;
import com.badlogic.gdx.math.Vector2;

public class RangeComparator implements Comparator<Door> {
	private DynamicObject toCompareTo;
	
	public RangeComparator(DynamicObject toCompareTo){
		this.toCompareTo = toCompareTo;
	}

	@Override
	public int compare(Door o1, Door o2) {
		if (o1 == o2) return 0;
		float distanceFromFirst = toCompareTo.getPosition().dst(o1.getPosition());
		float distanceFromSecond = toCompareTo.getPosition().dst(o2.getPosition());
		
		return Float.compare(distanceFromFirst, distanceFromSecond);
	}
}
