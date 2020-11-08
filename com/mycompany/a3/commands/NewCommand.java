package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class NewCommand extends Command {
	//~~~~~~Constructor~~~~~~
	public NewCommand() {
		super("New");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			System.out.println("'New' has been pressed.");
		}
	}
}
