package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class UndoCommand extends Command {
	//~~~~~~Constructor~~~~~~
	public UndoCommand() {
		super("Undo");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			System.out.println("'Undo' item has been pressed.");
		}
	}
}
