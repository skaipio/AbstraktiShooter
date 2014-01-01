package com.abstrakti.shooter.objects;

import java.util.SortedMap;
import java.util.TreeMap;

import com.abstrakti.shooter.components.Component;
import com.abstrakti.shooter.components.ComponentType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {
	
	
	private SortedMap<ComponentType, Component> components = new TreeMap<ComponentType, Component>();
	
	public boolean hasComponent(ComponentType ofType){
		return this.components.containsKey(ofType);
	}
	
	public Component getComponent(ComponentType ofType){
		return this.components.get(ofType);
	}
	
	public void addComponent(Component component){
		this.components.put(component.TYPE, component);
	}
	
	
	
	
	/**
	 * Updates all components of this object.
	 */
	public void update(float deltaTime){
		for (Component cmp : this.components.values()) {
			cmp.update(deltaTime);
		}
	}
	
	public void draw(SpriteBatch batch){
		for (Component cmp : this.components.values()) {
			cmp.draw(batch);
		}
	}
}
