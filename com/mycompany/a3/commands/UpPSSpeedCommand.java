package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class UpPSSpeedCommand extends Command {
	private GameWorld gw;
	
	//~~~~~~Constructor~~~~~~
	public UpPSSpeedCommand(GameWorld gw) {
		super("PS Speed (+)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			gw.upPSSpeed();
		}
	}
}
