package com.mycompany.a3.view;


import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.Asteroid;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.GameWorldCollection;
import com.mycompany.a3.Missile;
import com.mycompany.a3.NonPlayerShip;
import com.mycompany.a3.interfaces.IDrawable;
import com.mycompany.a3.interfaces.IGameWorld;
import com.mycompany.a3.interfaces.IIterator;
import com.mycompany.a3.interfaces.ISelectable;

public class MapView extends Container implements Observer {
	private GameWorldCollection gwCollection;
	private IGameWorld gw;
	
	public MapView() {
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLUE));
	}
	@Override
	public void update (Observable o, Object arg) {   
		// code here to output current map information 	(based on the data in the Observable)   
		// to the console.  Note that the received “Observable” is a GameWorld PROXY and can    
		// be cast to type IGameWorld in order to access the GameWorld methods in it.   
		gw = (IGameWorld)arg;
		
		//Retrieve the GameWorld's GameCollection and print out the current map information
		gwCollection = gw.getGameWorldCollection();
		this.repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Point pCmpRelPrnt = new Point(getX(), getY());
		IIterator gameIterator = gwCollection.getIterator();
		while (gameIterator.hasNext()) {
			GameObject obj = (GameObject)gameIterator.getNext();
			//Deselect all ISelectable objects when the game is running again
			if (gw.getGamePlayStatus() == true) {
				if (obj instanceof ISelectable) {
					ISelectable selectableObj = (ISelectable)obj;
					selectableObj.setSelected(false);

				}
			}
			((IDrawable)obj).draw(g, pCmpRelPrnt); 
		}
	}
	
	public void pointerPressed(int x, int y) {
		x = x - getParent().getAbsoluteX(); 
		y = y - getParent().getAbsoluteY(); 
		Point pPtrRelPrnt = new Point(x, y); 
		Point pCmpRelPrnt = new Point(getX(), getY()); 
		
		//Only selectable in 'pause' mode
		if(gw.getGamePlayStatus() == false) {
			IIterator itr = gwCollection.getIterator();
			while (itr.hasNext()) {
				GameObject obj = (GameObject)itr.getNext();
				if (obj instanceof ISelectable) {
					ISelectable selectableObj = (ISelectable)obj;
					//Check if the Pointer selects the Selectablee object
					if (selectableObj.contains(pPtrRelPrnt, pCmpRelPrnt)) {
						selectableObj.setSelected(true);
					}
					else {
						selectableObj.setSelected(false);
					}
				}
			}
			this.repaint();
		}
	}
	
}
