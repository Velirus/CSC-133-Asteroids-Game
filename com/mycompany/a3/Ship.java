package com.mycompany.a3;


public abstract class Ship extends MoveableGameObject {
	//~~~~~~Ship variables~~~~~~~~
	private int missileCount;
	private int width = 30;
	private int height = 40;
	
	//get and set missiles
	public int getMissiles() {
		return missileCount;
	}
	public void setMissiles(int m) {
		this.missileCount = m;
	}
	//get the width of the Ship
	public int getWidth() {
		return width;
	}
	//get the height of the ship
	public int getHeight() {
		return height;
	}
}
