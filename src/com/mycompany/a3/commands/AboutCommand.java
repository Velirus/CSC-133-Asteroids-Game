package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command {
	//~~~~~~Constructor~~~~~~
	public AboutCommand() {
		super("About");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			Command cOk = new Command("OK");
			Dialog.show("About", "Jeremy Tom - CSC 133-02", cOk);
		}
	}
}
