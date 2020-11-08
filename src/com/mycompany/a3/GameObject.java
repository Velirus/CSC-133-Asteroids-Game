package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.mycompany.a3.interfaces.IDrawable;

public abstract class GameObject implements IDrawable {
	//~~~~~~GameObject variables~~~~~~~~
	private double locX;
	private double locY;
	private int color;
	
	//get and set color
	public int getColor() {
		return color;
	}
	public void setColor(int c) {
		this.color = c;
	}
	
	//get and set X location
	public double getLocX() {
		return locX;
	}
	public void setLocX(double x) {
		this.locX = x;
	}
	//get and set Y location
	public double getLocY() {
		return locY;
	}
	public void setLocY(double y) {
		this.locY = y;
	}
	
	//~~~~~~~String override~~~~~~~
	//Print out the location and color of Game Objects
	public String toString() {
		String myString = "location= " + Math.round(locX*10.0)/10.0 + ","+ Math.round(locY*10.0)/10.0 + 
						  " color=[" + ColorUtil.red(color) + "," + ColorUtil.green(color) + 
						  "," + ColorUtil.blue(color) + "]";
		return myString;
		
	}
	
}
