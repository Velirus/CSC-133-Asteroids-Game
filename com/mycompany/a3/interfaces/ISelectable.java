package com.mycompany.a3.interfaces;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface ISelectable {
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
	public void setSelected(boolean yesNo);
	public boolean isSelected(); 
	public void draw(Graphics g, Point pCmpRelPrnt); 
}
