package com.mycompany.a3.view;


import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.interfaces.IGameWorld;

public class PointsView extends Container implements Observer {
	private Label livesLabel = new Label(" ");
	private Label pointsLabel = new Label(" ");
	private Label timeLabel = new Label(" ");
	private Label missileLabel = new Label(" ");
	private Label soundLabel = new Label(" ");
	
	public PointsView() {
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLUE));
		this.add(new Label("Lives: "));
		this.add(livesLabel);
		this.add(new Label("Points: "));
		this.add(pointsLabel);
		this.add(new Label("Time: "));
		this.add(timeLabel);
		this.add(new Label("Missiles: "));
		this.add(missileLabel);
		this.add(new Label("Sound: "));
		this.add(soundLabel);
	}
	
	@Override
	public void update (Observable o, Object arg) {   
		 // code here to update labels from data in the Observable (a GameWorldPROXY)  
		IGameWorld gw = (IGameWorld)arg;
		this.livesLabel.setText("" + gw.getLives());
		this.pointsLabel.setText("" + gw.getScore());
		this.timeLabel.setText("" + gw.getTime());
		this.missileLabel.setText("" + gw.getMissiles());
		//Set the sound text (ON if true, OFF if false)
		if (gw.getSound()) {
			this.soundLabel.setText("" + "ON");
		}
		else {
			this.soundLabel.setText("" + "OFF");
		}
		this.repaint();
	 }
	

}
