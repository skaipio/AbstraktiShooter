package com.abstrakti.shooter.components;

import com.badlogic.gdx.math.Vector2;


public class CMapPlace extends Component{
	private final Vector2 place;
	
	public CMapPlace(float x, float y){
		super(Type.MapPlace);
		this.place = new Vector2(x, y);
	}
	
	public Vector2 getPlace(){
		return this.place;
	}
	
	public void setPlace(float x, float y){
		this.place.set(x, y);
	}

	public float getY() {
		return this.place.y;
	}

	public void setY(float y) {
		this.place.set(this.place.x, y);
	}

	public float getX() {
		return this.place.x;
	}

	public void setX(float x) {
		this.place.set(x, this.place.y);
	}
	
	@Override
	public String toString(){
		return "Map Place component";
	}
}
