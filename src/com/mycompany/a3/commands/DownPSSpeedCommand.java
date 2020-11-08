package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class DownPSSpeedCommand extends Command {
	private GameWorld gw;
	
	//~~~~~~Constructor~~~~~~
	public DownPSSpeedCommand(GameWorld gw) {
		super("PS Speed (-)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			gw.downPSSpeed();
		}
	}
}