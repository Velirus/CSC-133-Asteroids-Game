package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.ISelectable;

import java.util.Random;

public class NonPlayerShip extends Ship implements ISelectable, ICollider {
	private int size;
	private Random rand = new Random();
	private boolean selected;
	private boolean collided;
	private MissileLauncher launcher;
		
	//~~~~~~CONSTRUCTOR~~~~~~~~
	public NonPlayerShip(int mapX, int mapY) {
		//RGB Red
		this.setColor(ColorUtil.rgb(255, 0, 0) );
		this.setMissiles(2);
		this.setLocX((double)rand.nextInt(mapX));
		this.setLocY((double)rand.nextInt(mapY));
		this.setDirection(rand.nextInt(360));
		launcher = new MissileLauncher(this.getLocX() / 2, this.getLocY() / 2);
		launcher.setDirection(this.getDirection());
		
	}
	//Return the largest size between width and height
	public int getSize() {
		if (this.getWidth() > this.getHeight()) {
			return this.getWidth();
		} else {
			return this.getHeight();
		}
	}
	
	public void move(int elapsedTime) {
		super.move(elapsedTime);
		launcher.setLocX(this.getLocX());
		launcher.setLocY(this.getLocY());
	}
	
	//~~~~~~~String override~~~~~~~
	//Print out the size of a Non-Player Ship
	//while  using the parent method in Moveable Game Object to print out location, color, speed, and direction
	public String toString() {
		String thisString = "Non-Player Ship: " + super.toString() + " size=" + this.getSize();
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
		
		//Draw an unfilled triangle if the NPS is selected
		if(isSelected()) {
			g.drawPolygon(xList, yList, 3);
		} else {
			//Draw a filled triangle for all unselected NPS
			g.fillPolygon(xList, yList, 3);
		}
		
		launcher.draw(g, pCmpRelPrnt);
	}
	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		//Retrieve and store the minimum border values of the NPS (triangle) for the x and y-axis
		int shapeXMin = (int)pCmpRelPrnt.getX() + (int)this.getLocX() - getWidth()/2;
		int shapeYMin = (int)pCmpRelPrnt.getY() + (int)this.getLocY() - getHeight()/2;
		
		//Retrieve and store the maximum border values of the NPS (triangle) for the x and y-axis
		int shapeXMax = (int)pCmpRelPrnt.getX() + (int)this.getLocX() + getWidth()/2;;
		int shapeYMax = (int)pCmpRelPrnt.getY() + (int)this.getLocY() + getHeight()/2;;
		
		//Retrieve and store the pointer values
		int pointerX = (int)pPtrRelPrnt.getX(); 
		int pointerY = (int)pPtrRelPrnt.getY();
		//Check to see if the pointer values is within the x-values of the NPS (triangle)
		if (pointerX >= shapeXMin && pointerX <= shapeXMax) {
			//Check to see if the pointer value is within the y-values of the NPS (triangle)
			if (pointerY >= shapeYMin && pointerY <= shapeYMax) {
				//Set 'selected' to true if the pointer is within the Asteroid NPS (triangle)
				selected = true;
			}
		}
		//Set 'selected' to false if the pointer is not within the NPS (triangle)
		else {
			selected = false;
		}				
		return selected;
		
	}
	@Override
	public void setSelected(boolean yesNo) {
		selected = yesNo;
		
	}
	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return selected;
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
		
		//NPS collides with Asteroid
		if (otherObject instanceof Asteroid) {
			this.setColissionStatus(true);
			otherObject.setColissionStatus(true);
		} 
		//NPS collides with PlayerShip
		else if (otherObject instanceof PlayerShip) {
			this.setColissionStatus(true);
			otherObject.setColissionStatus(true);
		}
		//NPS collides with PS's Missile
		else if (otherObject instanceof Missile) {
			Missile missile = (Missile)otherObject;
			if (missile.getMissileType() == "PS") {
				this.setColissionStatus(true);
				otherObject.setColissionStatus(true);
				GameWorld.increaseScore(500);
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
}
