package com.mycompany.a3;

import java.util.Random;

import com.mycompany.a3.interfaces.IMoveable;


public abstract class MoveableGameObject extends GameObject implements IMoveable {
	private Random rand = new Random();
	//~~~~~~MoveableGameObject variables~~~~~~~~
	//initial speed between 1 to 7
	private int speed = rand.nextInt(7) + 1;
	//initial direction between 0 to 359
	private int direction = rand.nextInt(360);
	
	//get and set speed
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int s) {
		this.speed = s;
	}
	
	//get and set direction
	public int getDirection() {
		return direction;
	}
	public void setDirection(int d) {
		this.direction = d;
	}
	
	//~~~~~Implement IMoveable interface~~~~~~
	public void move(int elapsedTime) {
		double deltaX = Math.cos(Math.toRadians(90 - this.direction));
		double deltaY = Math.sin(Math.toRadians(90 - this.direction));
		double newX = this.getLocX() + deltaX * this.speed;
		double newY = this.getLocY() + deltaY * this.speed;
		
		/*
		 * Check to make sure the Moveable Object does not go out of bounds
		 * and if so, have the object "touch" the boundary and then
		 * "bounce" in the other direction at the next call
		 */
		
		//Out of bounds in x-direction at maximum MapView width or 0
		if (newX >= GameWorld.getMapWidth() || newX <= 0) {
			if (newX <= 0) {
				newX = 0;
			}
			else {
				newX = GameWorld.getMapWidth();
			}
			//Change the direction on the x-axis
			this.setDirection(360 - this.direction);
		}

		//Out of bounds in y-direction at maximum MapView height or 0
		if (newY >= GameWorld.getMapHeight() || newY <= 0) {
			if (newY <= 0) {
				newY = 0;
			}
			else {
				newY = GameWorld.getMapHeight();
			}
			//Change the direction on the y-axis
			this.setDirection(180 - this.direction);
		}
		
		this.setLocX(newX);
		this.setLocY(newY);
	}
	
	//~~~~~~~String override~~~~~~~
	//Print out the location, color, speed, and direction of Moveable Game Objects
	//while also using the parent method in Game Object
	public String toString() {
		String thisString = super.toString() + " speed=" + speed + " dir=" + direction;
		return thisString;
			
	}
}
