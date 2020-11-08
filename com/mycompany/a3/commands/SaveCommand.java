package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SaveCommand extends Command {
	//~~~~~~Constructor~~~~~~
	public SaveCommand() {
		super("Save");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			System.out.println("'Save' item has been pressed.");
		}
	}
}
