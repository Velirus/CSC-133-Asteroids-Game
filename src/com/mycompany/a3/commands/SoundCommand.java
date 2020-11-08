package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameWorld;

public class SoundCommand extends Command {
	
	private GameWorld gw;
	//~~~~~~Constructor~~~~~~
	public SoundCommand(GameWorld gw) {
		super("Sound");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			//Store the status of the SoundCheckbox whether it is check or not
			if (Game.getSoundCheckBox().isSelected()) {
				gw.setSoundCheckboxStatus(true);
			}
			else {
				gw.setSoundCheckboxStatus(false);
				gw.stopSound();
			}
			
			if (gw.getSound() || !gw.getGamePlayStatus()) {
				gw.setSound(false);
				gw.stopSound();
			}
			else {
				gw.setSound(true);
				gw.resumeSound();
			}
			System.out.println("'Sound' has been pressed.");
		}
	}
}
