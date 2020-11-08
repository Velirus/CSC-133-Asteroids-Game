package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AddStationCommand extends Command {
	private GameWorld gw;
	
	//~~~~~~Constructor~~~~~~
	public AddStationCommand(GameWorld gw) {
		super("+ Space Station");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			gw.addStation();
		}
	}
}