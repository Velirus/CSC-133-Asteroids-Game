package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.interfaces.ICollider;

import java.util.Random;

public class SpaceStation extends FixedGameObject implements ICollider {
	private Random rand = new Random();
	private boolean light = true;
	private int blinkRate = rand.nextInt(5);
	private String lightStatus;
	private boolean collided;
	private int radius = 12;
	private int time;
	
	
	//~~~~~~CONSTRUCTOR~~~~~~~~
	public SpaceStation(int mapX, int mapY) {
		//RGB Blue
		this.setColor(ColorUtil.BLUE );
		this.setLocX((double)rand.nextInt(mapX));
		this.setLocY((double)rand.nextInt(mapY));
		//Set the time of the SpaceStation to 0
		this.time = 0;
		
	}
	//update the Space Station when a "tick" is invoked
	public void tickStation(int elapsedTime) {
		this.time = this.time + elapsedTime;
		//Update the blink rate every 1.0 seconds
		if (time % 1000 == 0) {
			blinkRate = blinkRate + 1;
		}
		//Turn Space Station light ON or OFF if 4 seconds have passed
		//and reset the blinkRate to 0
		if (blinkRate > 4) {
			blinkRate = 0;
			if (light) {
				light = false;
			}
			else {
				light = true;
			}
		}
	}
	
	//~~~~~~~String override~~~~~~~
	//Print out the blink rate, and light status of Space Station
	//while also using the parent method in FixedGameObject to print location, color, and ID
	public String toString() {
		//Print out the status of the Space Station whether the light is ON or OFF
		if (light) {
			lightStatus = "ON";
		}
		else {
			lightStatus = "OFF";
		}
		String thisString = "Space Station: " + super.toString() + " rate=" + blinkRate + " light=" + lightStatus;
		return thisString;
					
	}
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int x = (int)pCmpRelPrnt.getX() + (int)this.getLocX();
		int y = (int)pCmpRelPrnt.getY() + (int)this.getLocY();
		
		int minX = x - radius;
		int minY = y - radius;
		int maxX = minX + radius;
		int maxY = minY + radius;
		
		g.setColor(this.getColor());
		if (light) {
			g.fillArc(minX, minY, 2*radius, 2*radius, 0, 360);
		}
		g.drawArc(minX, minY, 2*radius, 2*radius, 0, 360);
		
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
		if (otherObject instanceof PlayerShip) {
			GameWorld.resupply();
		}
		
	}
	@Override
	public void setColissionStatus(boolean status) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean getCollisionStatus() {
		// TODO Auto-generated method stub
		
		//Space Station cannot be removed from the Game World
		return false;
	}
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return radius;
	}
	
}
