package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.interfaces.ISteerable;

public class MissileLauncher extends MoveableGameObject implements ISteerable {
	
	//~~~~~~CONSTRUCTOR~~~~~~~~
	public MissileLauncher(double x, double y) {
		//RGB Yellow
		this.setColor(ColorUtil.YELLOW);
		this.setLocX(x);
		this.setLocY(y);
	}
	
	//Get MissileLauncher's direction for firing missiles
	public int getLauncherDirection() {
		return this.getDirection();
	}
	
	//~~~~~Implement ISteerable interface~~~~~~
	public void changeDirection(int d) {
		//Update the Missile Launcher with the new direction
		int newMLDir = this.getDirection() + d;
		//Prevent the new Missile Launcher direction from exceeding 359 whilst turning clockwise
		if (newMLDir >= 360) {
			newMLDir = newMLDir - 360;
		}
		//Prevent the new Missile Launcher direction from reaching negative whilst turning counter-clockwise
		else if (newMLDir <= 0) {
			newMLDir = newMLDir + 360;
		}
		this.setDirection(newMLDir);
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int x = (int)pCmpRelPrnt.getX() + (int)this.getLocX();
		int y = (int)pCmpRelPrnt.getY() + (int)this.getLocY();
		
		int width = 6, height = 40;
		
		int launcherX = x - width/2;
		int launcherY = y - height;
		
		g.setColor(this.getColor());
		g.fillRect(launcherX, launcherY, width, height);
		
	}
}
