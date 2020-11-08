package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class ResupplyCommand extends Command {
	private GameWorld gw;
	
	//~~~~~~Constructor~~~~~~
	public ResupplyCommand(GameWorld gw) {
		super("Load PS");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			gw.resupply();
		}
	}
}
