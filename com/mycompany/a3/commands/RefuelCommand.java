package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class RefuelCommand extends Command {
	private GameWorld gw;
	
	//~~~~~~Constructor~~~~~~
	public RefuelCommand(GameWorld gw) {
		super("Refuel");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			gw.refuel();
		}
	}
}
