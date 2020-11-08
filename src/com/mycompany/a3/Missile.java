package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.ISelectable;

public class Missile extends MoveableGameObject implements ISelectable, ICollider {
	private String missileType;
	private int fuel;
	private int time;
	private boolean selected;
	private boolean collided;
	
	private int width = 10, height = 30;
	
	//~~~~~~CONSTRUCTOR~~~~~~~~
	//Missiles can be fired from either a PS or NPS. Location, speed, and direction determined
	//by which is firing the missile
	public Missile(double X, double Y, int speed, int direction, String type) {
		//RGB Green
		this.setColor(ColorUtil.GREEN);
		this.setLocX(X);
		this.setLocY(Y);
		this.setSpeed(speed);
		this.setDirection(direction);
		this.missileType = type;
		this.fuel = 10;
	}
	//Return the type of Missile it is (a 'PS' or 'NPS' Missile)
	public String getMissileType() {
		return this.missileType;
	}
	//Getter for the Missile's fuel
	public int getFuel() {
		return this.fuel;
	}
	
	//Override move() method in MoveableGameObject
	//and decrease the fuel by 1
	public void move(int elapsedTime) {
		super.move(elapsedTime);
		time = time + elapsedTime;
		//Missile uses up a fuel every 0.6 seconds elapsed
		if (time % 600 == 0) {
			this.fuel = this.fuel - 1;
		}
	}
	
	//~~~~~~~String override~~~~~~~
	public String toString() {
		String thisString = missileType + "'s Missile: " + super.toString() + " fuel=" + fuel;
		return thisString;
	}
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int x = (int)pCmpRelPrnt.getX() + (int)this.getLocX();
		int y = (int)pCmpRelPrnt.getY() + (int)this.getLocY();
		
		int minX = x - width/2;
		int minY = y - height/2;
		int maxX = minX + width;
		int maxY = minY + height;
				
		//Change the missile color to magenta if it's a NPS Missile
		if (missileType == "NPS") {
			this.setColor(ColorUtil.MAGENTA);
		}
		
		
		g.setColor(this.getColor());
		if (isSelected()) {
			g.drawRect(minX, minY, width, height);
		}
		else {
			g.fillRect(minX, minY, width, height);
		}
	
	}
	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		//Retrieve and store the minimum border values of the Asteroid (square) for the x and y-axis
		int shapeXMin = (int)pCmpRelPrnt.getX() + (int)this.getLocX() - (width/2);
		int shapeYMin = (int)pCmpRelPrnt.getY() + (int)this.getLocY() - height;
				
		//Retrieve and store the maximum border values of the Asteroid (square) for the x and y-axis
		int shapeXMax = shapeXMin + width;
		int shapeYMax = shapeYMin + height;
		
		//Retrieve and store the pointer values
		int pointerX = (int)pPtrRelPrnt.getX(); 
		int pointerY = (int)pPtrRelPrnt.getY();
		
		//Check to see if the pointer values is within the x-values of the Asteroid (square)
		if (pointerX >= shapeXMin && pointerX <= shapeXMax) {
			//Check to see if the pointer value is within the y-values of the Asteroid (square)
			if (pointerY >= shapeYMin && pointerY <= shapeYMax) {
				//Set 'selected' to true if the pointer is within the Asteroid (square)
				selected = true;
			}
		}
		//Set 'selected' to false if the pointer is not within the Asteroid (square)
		else {
			selected = false;
		}
		
		return selected;
	}
	@Override
	public void setSelected(boolean yesNo) {
		// TODO Auto-generated method stub
		selected = yesNo;
		
	}
	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return selected;
	}
	
	public void refuelMissile() {
		this.fuel = 10;
		//reset the counter when a Missile is refuled
		time = 0;
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
		
		//PS Missile
		if (this.getMissileType() == "PS") {
			//collides with an Asteroid
			if (otherObject instanceof Asteroid) {
				this.setColissionStatus(true);
				otherObject.setColissionStatus(true);
				GameWorld.increaseScore(200);
				GameWorld.playMissileAsteroidSound();
			}
			//collides with a NonPlayerSHip
			else if (otherObject instanceof NonPlayerShip) {
				this.setColissionStatus(true);
				otherObject.setColissionStatus(true);
				GameWorld.increaseScore(500);
			}
		}
		//NPS Missile
		else {
			//collides with an Asteroid
			if (otherObject instanceof Asteroid) {
				this.setColissionStatus(true);
				otherObject.setColissionStatus(true);
				GameWorld.playMissileAsteroidSound();
			}
			//collides with a PlayerShip
			else if (otherObject instanceof PlayerShip) {
				this.setColissionStatus(true);
				otherObject.setColissionStatus(true);
			}
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
	//Return the largest value between width and height
	public int getSize() {
		// TODO Auto-generated method stub
		if (width > height) {
			return width;
		}
		else {
			return height;
		}
	}
}
