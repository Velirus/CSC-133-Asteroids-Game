package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AddPSCommand extends Command {
	private GameWorld gw;
	
	//~~~~~~Constructor~~~~~~
	public AddPSCommand(GameWorld gw) {
		super("+ PS (1)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			gw.addPS();
		}
	}
}
