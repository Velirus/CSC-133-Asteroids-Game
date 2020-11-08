package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.ISteerable;

//Class uses Singleton design pattern
public class PlayerShip extends Ship implements ISteerable, ICollider {
	private static PlayerShip ps;
	private MissileLauncher launcher;
	private boolean collided;
	
	
	//~~~~~~CONSTRUCTOR~~~~~~~~
	private PlayerShip(int mapX, int mapY) {
		//RGB Green
		this.setColor(ColorUtil.GREEN);
		this.setMissiles(10);
		this.setLocX(mapX / 2);
		this.setLocY(mapY / 2);
		this.setSpeed(0);
		this.setDirection(0);
		//Create a new Missile Launcher when the Player Ship is created
		launcher = new MissileLauncher(this.getLocX() / 2, this.getLocY() / 2);
	}
	
	public static PlayerShip getPS(int mapX, int mapY) {
		if (ps == null) {
			ps = new PlayerShip(mapX, mapY);
			System.out.println("Created new Player Ship.");
		}
		return ps;
	}
	//set 'ps' to null if the Player Ship is removed from the GameWorld
	public void setPSNull() {
		ps = null;
	}
	//accelerate PS
	public void accelPS() {
		//Acceleration reaches maximum of 6
		if (this.getSpeed() + 1 >= 6) {
			//Prevent speed from exceeding 6
			this.setSpeed(6);
		}
		else {
			this.setSpeed(this.getSpeed() + 1);
		}
	}
	//decelerate PS
	public void decelPS() {
		//Deceleration reaches minimum of 0
		if (this.getSpeed() - 2 <= 0) {
			//Prevent speed from reaching negative
			this.setSpeed(0);
		}
		else {
			this.setSpeed(this.getSpeed() - 2);
		}
	}	
	//turn the missile launcher
	public void turnMissileLauncher(int t) {
		launcher.changeDirection(t);
	}
	//Getter for the Missile Launcher's direction
	public int getLauncherDirection() {
		return launcher.getDirection();
	}
	
	//~~~~~Implement ISteerable interface~~~~~~
	public void changeDirection(int d) {
		//Update the Player Ship with the new direction
		int newPSDir = this.getDirection() + d;
		//Prevent the new Player Ship direction from exceeding 359 whilst turning clockwise
		if (newPSDir <= 0) {
			newPSDir = newPSDir  + 360;
		}
		//Prevent the new Player Ship direction from reaching negative whilst turning counter-clockwise
		else if (newPSDir >= 360) {
			newPSDir = newPSDir - 360;
		}
		this.setDirection(newPSDir);
	}
	//Override move() method in MoveableGameObject
	//Missile Launcher updates to same location as Player Ship
	public void move(int elapsedTime) {
		super.move(elapsedTime);
		launcher.setLocX(this.getLocX());
		launcher.setLocY(this.getLocY());
	}
	
	//~~~~~~~String override~~~~~~~
	//Print out the missiles and Missile Launcher direction of a Non-Player Ship
	//while  using the parent method in Moveable Game Object to print out location, color, speed, and direction
	public String toString() {
		String thisString = "Player Ship: " + super.toString() + " missiles=" + this.getMissiles() + 
							" Missile Launcher dir=" + launcher.getDirection();
		return thisString;
				
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int x = (int)pCmpRelPrnt.getX() + (int)this.getLocX();
		int y = (int)pCmpRelPrnt.getY() + (int)this.getLocY();
				
		int x1 = x - getWidth()/2;
		int x2 = x;
		int x3 = x + getWidth()/2;
		int [] xList = {x1, x2, x3};
		
		int y1 = y - getHeight()/2;
		int y2 = y + getHeight()/2;
		int y3 = y - getHeight()/2;
		int [] yList = {y1, y2, y3};
		
		g.setColor(this.getColor());
		g.drawPolygon(xList, yList, 3);

		launcher.draw(g, pCmpRelPrnt);
	}

	@Override
	public boolean collidesWith(ICollider otherObject) {
		// TODO Auto-generated method stub
		boolean result = false;
		int thisCenterX = (int)this.getLocX() + this.getSize()/2;
		int thisCenterY = (int)this.getLocY() + this.getSize()/2;
		
		GameObject obj2 = (GameObject)otherObject;
		int otherCenterX = (int)obj2.getLocX() + otherObject.getSize()/2;
		int otherCenterY = (int)obj2.getLocY() + otherObject.getSize();
		
		//find dist between centers
		int dx = thisCenterX - otherCenterX; 
		int dy = thisCenterY - otherCenterY; 
		int distBetweenCentersSqr = (dx*dx + dy*dy);
		
		// find square of sum of radii 
		int thisRadius = this.getSize()/2; 
		int otherRadius = otherObject.getSize()/2;
		int radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius); 
		if (distBetweenCentersSqr <= radiiSqr) { 
			result = true ; 
		} 
		
		return result;
	}

	@Override
	public void handleCollsion(ICollider otherObject) {
		// TODO Auto-generated method stub
		//PS collides with Asteroid or NPS
		if (otherObject instanceof Asteroid || otherObject instanceof NonPlayerShip) {
			this.setColissionStatus(true);
			otherObject.setColissionStatus(true);
		} 
		//PS collides with a NPS's missile
		else if (otherObject instanceof Missile) {
			Missile missile = (Missile)otherObject;
			if (missile.getMissileType() == "NPS") {
				this.setColissionStatus(true);
				otherObject.setColissionStatus(true);
			}
		}
		//PS collides with a Space Station
		else if (otherObject instanceof SpaceStation) {
			GameWorld.resupply();
		} 
		
	}

	@Override
	public void setColissionStatus(boolean status) {
		// TODO Auto-generated method stub
		collided = status;
		
	}

	@Override
	public boolean getCollisionStatus() {
		// TODO Auto-generated method stub
		return collided;
	}

	@Override
	//Return the largest size between width and height
	public int getSize() {
		if (this.getWidth() > this.getHeight()) {
			return this.getWidth();
		} else {
			return this.getHeight();
		}
	}

}
