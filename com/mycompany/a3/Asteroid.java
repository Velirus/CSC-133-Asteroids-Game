package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.ISelectable;

import java.util.Random;

public class Asteroid extends MoveableGameObject implements ISelectable, ICollider {
	private int size;
	private Random rand = new Random();
	private boolean selected;
	private boolean collided;
	
	//~~~~~~CONSTRUCTOR~~~~~~~~
	public Asteroid(int mapX, int mapY) {
		//RGB Black
		this.setColor(ColorUtil.BLACK);
		this.setLocX((double)rand.nextInt(mapX));
		this.setLocY((double)rand.nextInt(mapY));
		this.setDirection(rand.nextInt(360));
		this.setSize();
				
	}
	//setter and getter for size.
	//Fixed-size on creation between 15 to 30
	private void setSize() {
		this.size = (rand.nextInt(16) + 15);
	}
	//Get the size of the object
	public int getSize() {
		return this.size;
			
	}
	//~~~~~~~String override~~~~~~~
	public String toString() {
		String thisString = "Asteroid: " + super.toString() + " size=" + this.getSize();
		return thisString;
						
	}
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int x = (int)pCmpRelPrnt.getX() + (int)this.getLocX();
		int y = (int)pCmpRelPrnt.getY() + (int)this.getLocY();
		
		int minX = x - this.getSize()/2;
		int minY = y - this.getSize()/2;
		
		g.setColor(this.getColor());
		//Draw a filled square if the Asteroid is selected
		if (isSelected()) {
			g.fillRect(minX, minY, this.getSize(), this.getSize());
		}
		else {
			//Draw an unfilled square for all unselected Asteroids
			g.drawRect(x - this.getSize()/2, y - this.getSize()/2, this.getSize(), this.getSize());
		}
		
	}
	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		//Retrieve and store the minimum border values of the Asteroid (square) for the x and y-axis
		int shapeXMin = (int)pCmpRelPrnt.getX() + (int)this.getLocX() - this.getSize()/2;
		int shapeYMin = (int)pCmpRelPrnt.getY() + (int)this.getLocY() - this.getSize()/2;
		
		//Retrieve and store the maximum border values of the Asteroid (square) for the x and y-axis
		int shapeXMax = (int)pCmpRelPrnt.getX() + (int)this.getLocX() + this.getSize()/2;
		int shapeYMax = (int)pCmpRelPrnt.getY() + (int)this.getLocY() + this.getSize()/2;
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
		//Set Asteroid and PlayerShip to have collided and remove a life
		if (otherObject instanceof PlayerShip) {
			this.setColissionStatus(true);
			otherObject.setColissionStatus(true);
		} 
		//Set Asteroid and NonPlayerShip/another Asteroid to have collided
		else if (otherObject instanceof NonPlayerShip || otherObject instanceof Asteroid){
			this.setColissionStatus(true);
			otherObject.setColissionStatus(true);
		}
		//Set Asteroid and Missile to have collided
		else if (otherObject instanceof Missile) {
			this.setColissionStatus(true);
			otherObject.setColissionStatus(true);
			
			//Check if it's a PS missile
			Missile missile = (Missile)otherObject;
			if(missile.getMissileType() == "PS") {
				//Increase the score by 200 points
				GameWorld.increaseScore(200);
			}
			GameWorld.playMissileAsteroidSound();
		}
		
	}
	@Override
	public void setColissionStatus(boolean status) {
		collided = status;
	}
	@Override
	public boolean getCollisionStatus() {
		// TODO Auto-generated method stub
		return collided;
	}
}
