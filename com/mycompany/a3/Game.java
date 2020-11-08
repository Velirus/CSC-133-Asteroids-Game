package com.mycompany.a3;


import com.codename1.ui.*;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.commands.*;
import com.mycompany.a3.view.MapView;
import com.mycompany.a3.view.PointsView;


public class Game extends Form implements Runnable {
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	
	private UITimer timer;
	private int timeMillis = 20;
	
	//Container for buttons
	private Container buttonContainer;
	
	//Buttons
	private CustomButton addAsteroidButton;
	private CustomButton addStationButton;
	private CustomButton addPSButton;
	private CustomButton upPSSpeedButton;
	private CustomButton downPSSpeedButton;
	private CustomButton turnPSLeftButton;
	private CustomButton turnPSRightButton;
	private CustomButton turnLauncherLeftButton;
	private CustomButton turnLauncherRightButton;
	private CustomButton launchPSMissileButton;
	private CustomButton jumpButton;
	private CustomButton resupplyButton;
	private CustomButton playPauseButton;
	private CustomButton refuelButton;
	private static CheckBox soundCheckBox;
	
	//Commands
	private AddStationCommand addStationCommand;
	private AddPSCommand addPSCommand;
	private UpPSSpeedCommand upPSSpeedCommand;
	private DownPSSpeedCommand downPSSpeedCommand;
	private TurnPSLeftCommand turnPSLeftCommand;
	private TurnPSRightCommand turnPSRightCommand;
	private TurnLauncherLeftCommand turnLauncherLeftCommand;
	private TurnLauncherRightCommand turnLauncherRightCommand;
	private LaunchPSMissileCommand launchPSMissileCommand;
	private JumpCommand jumpCommand;
	private ResupplyCommand resupplyCommand;
	private PauseCommand pauseCommand;
	private RefuelCommand refuelCommand;
	
	
	//~~~~~~CONSTRUCTOR~~~~~~~~
	public Game() {
		//pass Game to GameWorld
		gw = new GameWorld(this);
		mv = new MapView();
		pv = new PointsView();
		
		//set layout
		this.setLayout(new BorderLayout());
		
		//add observers to layout
		this.add(BorderLayout.NORTH, pv);
		this.add(BorderLayout.CENTER, mv);


		//create and set up Container for buttons
		buttonContainer = new Container();
		buttonContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		buttonContainer.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLUE));
		add(BorderLayout.WEST, buttonContainer);
		
		//~~~~~~~~~Commands and Key Bindings~~~~~~~~~~
		
		/* Add a new Asteroid
		 * Declare Add Asteroid Button and Command; set the button command to add addAsteroidCommand 
		 */
		AddAsteroidCommand addAsteroidCommand = new AddAsteroidCommand(gw);
		addAsteroidButton = new CustomButton("+ Asteroid");
		addAsteroidButton.setCommand(addAsteroidCommand);
		buttonContainer.add(addAsteroidButton);
		
		/* Add a new Space Station
		 * Declare Space Station Button and Command; set the button command to addStationCommand 
		 */
		addStationCommand = new AddStationCommand(gw);
		addStationButton = new CustomButton("+ Space Station");
		addStationButton.setCommand(addStationCommand);
		buttonContainer.add(addStationButton);
		
		/* Add a new Player Ship
		 * Declare Player Ship Button and command; set the button to addPSCommand 
		 */
		addPSCommand = new AddPSCommand(gw);
		addPSButton = new CustomButton("+ PS (1)");
		addPSButton.setCommand(addPSCommand);
		buttonContainer.add(addPSButton);
		
		/* Accelerate the Player Ship
		 * Declare Acceleration (upPSSpeed) button and command; set the button to upPSSpeedCommand
		 * Set 'up arrow' and 'i' key bindings to upPSSpeedCommand 
		 */
		upPSSpeedCommand = new UpPSSpeedCommand(gw);
		upPSSpeedButton = new CustomButton("PS Speed (+)");
		upPSSpeedButton.setCommand(upPSSpeedCommand);
		buttonContainer.add(upPSSpeedButton);
		addKeyListener(-91, upPSSpeedCommand);
		addKeyListener('i', upPSSpeedCommand);
		
		/* Decelerate the Player Ship
		 * Declare Deceleration (downPSSpeed) button  and command; set the button to downPSSpeedCommand
		 * Set 'down arrow' and 'd' key bindings to upPSSpeedCommand 
		 */
		downPSSpeedCommand = new DownPSSpeedCommand(gw);
		downPSSpeedButton = new CustomButton("PS Speed (-)");
		downPSSpeedButton.setCommand(downPSSpeedCommand);
		buttonContainer.add(downPSSpeedButton);
		addKeyListener(-92, downPSSpeedCommand);
		addKeyListener('d', downPSSpeedCommand);
		
		/* Turn  the Player Ship left
		 * Declare TurnPSLeft button and command; set the button to turnPSLeftCommand
		 * Set 'left arrow' and 'l' key bindings to turnPSLeftCommand 
		 */
		turnPSLeftCommand = new TurnPSLeftCommand(gw);
		turnPSLeftButton = new CustomButton("PS Left");
		turnPSLeftButton.setCommand(turnPSLeftCommand);
		buttonContainer.add(turnPSLeftButton);
		addKeyListener(-93, turnPSLeftCommand);
		addKeyListener('l', turnPSLeftCommand);
		
		/* Turn the Player Ship right
		 * Declare TurnPSRight button and command; set the button to turnPSRightCommand
		 * Set 'right arrow' and 'r' key bindings to turnPSRightCommand 
		 */
		turnPSRightCommand = new TurnPSRightCommand(gw);
		turnPSRightButton = new CustomButton("PS Right");
		turnPSRightButton.setCommand(turnPSRightCommand);
		buttonContainer.add(turnPSRightButton);
		addKeyListener(-94, turnPSRightCommand);
		addKeyListener('r', turnPSRightCommand);
		
		/* Turn the Missile Launcher left
		 * Declare TurnLauncherLeft button and command; set the button to turnLauncherLeftCommand
		 * Set '<' key binding to turnLauncherLeftCommand 
		 */
		turnLauncherLeftCommand = new TurnLauncherLeftCommand(gw);
		turnLauncherLeftButton = new CustomButton("MSL Left");
		turnLauncherLeftButton.setCommand(turnLauncherLeftCommand);
		buttonContainer.add(turnLauncherLeftButton);
		addKeyListener(44, turnLauncherLeftCommand);
		
		/* Turn the Missile Launcher right
		 * Declare TurnLauncherRight button and command; set the button to turnLauncherRightCommand
		 * Set '>' key binding to turnLauncherLeftCommand 
		 */
		turnLauncherRightCommand = new TurnLauncherRightCommand(gw);
		turnLauncherRightButton = new CustomButton("MSL Right");
		turnLauncherRightButton.setCommand(turnLauncherRightCommand);
		buttonContainer.add(turnLauncherRightButton);
		addKeyListener(46, turnLauncherRightCommand);
		
		/* Player Ship launch a missile
		 * Declare LaunchPSMissile button and command; set the button to turnLauncherRightCommand
		 * Set Spacebar key binding to launchPSMissileCommand 
		 */
		launchPSMissileCommand = new LaunchPSMissileCommand(gw);
		launchPSMissileButton = new CustomButton("PS Fire");
		launchPSMissileButton.setCommand(launchPSMissileCommand);
		buttonContainer.add(launchPSMissileButton);
		addKeyListener(-90, launchPSMissileCommand);
		
		/* Player Ship jumps into hyper space
		 * Declare Jump button and command; set the button to JumpCommand 
		 */
		jumpCommand = new JumpCommand(gw);
		jumpButton = new CustomButton("Jump");
		jumpButton.setCommand(jumpCommand);
		addKeyListener('j', jumpCommand);
		buttonContainer.add(jumpButton);
		
		/* Player Ship resupply with missiles
		 * Declare Resupply button and command; set the button to ResupplyCommand
		 */
		resupplyCommand = new ResupplyCommand(gw);
		resupplyButton = new CustomButton("Load PS");
		resupplyButton.setCommand(resupplyCommand);
		resupplyButton.setEnabled(false);
		buttonContainer.add(resupplyButton);

		/*
		 * Play/Pause Button
		 */
		playPauseButton = new CustomButton("Pause");
		pauseCommand = new PauseCommand(gw);
		//Game should be in 'play' mode initially, so 'pause' command is set first
		playPauseButton.setCommand(pauseCommand);
		buttonContainer.add(playPauseButton);
		
		/*
		 * Refuel button
		 */
		refuelButton = new CustomButton("Refuel");
		refuelCommand = new RefuelCommand(gw);
		refuelButton.setCommand(refuelCommand);
		refuelButton.setEnabled(false);
		buttonContainer.add(refuelButton);
		
		
		//~~~~~~~~~~Side Menu~~~~~~~~~
		Toolbar sideToolbar = new Toolbar();
		this.setToolbar(sideToolbar);
		sideToolbar.setTitle("Asteroid Game");
		
		/* 'New' item
		 */
		NewCommand newCommand = new NewCommand();
		sideToolbar.addCommandToSideMenu(newCommand);
		
		/* 'Save' item
		 */
		SaveCommand saveCommand = new SaveCommand();
		sideToolbar.addCommandToSideMenu(saveCommand);
		
		/* 'Undo' item
		 */
		UndoCommand undoCommand = new UndoCommand();
		sideToolbar.addCommandToSideMenu(undoCommand);
		
		/* 'Sound' item
		 */
		soundCheckBox = new CheckBox();
		SoundCommand soundCommand = new SoundCommand(gw);
		soundCheckBox.setSelected(true);
		soundCheckBox.setCommand(soundCommand);
		sideToolbar.addComponentToSideMenu(soundCheckBox);
		
		/* 'About' item
		 */
		AboutCommand aboutCommand = new AboutCommand();
		sideToolbar.addCommandToSideMenu(aboutCommand);
		
		/* 'Quit' item
		 */
		QuitCommand quitCommand = new QuitCommand(gw);
		sideToolbar.addCommandToSideMenu(quitCommand);
		addKeyListener('Q', quitCommand);
		
		this.show();
	
		//Set width and height of the Game World by querying size of MapView container
		gw.setMapWidth(mv.getWidth());
		gw.setMapHeight(mv.getHeight());
		
		gw.addObserver(mv);
		gw.addObserver(pv);
		gw.init();
		
		timer = new UITimer(this);
		timer.schedule(timeMillis, true, this);
	}
	
	//Return the Play/Pause button
	public CustomButton getPlayPauseButton() {
		return playPauseButton;
	}
	//Return the Sound Checkbox
	public static CheckBox getSoundCheckBox() {
		return soundCheckBox;
	}
	//Update the commands/key listeners when the game is paused
	//and pause the timer
	public void updateCommandsOnPause() {
		addAsteroidButton.setEnabled(false);
		addStationButton.setEnabled(false);
		addPSButton.setEnabled(false);
		upPSSpeedButton.setEnabled(false);
		downPSSpeedButton.setEnabled(false);
		turnPSLeftButton.setEnabled(false);
		turnPSRightButton.setEnabled(false);
		turnLauncherLeftButton.setEnabled(false);
		turnLauncherRightButton.setEnabled(false);
		launchPSMissileButton.setEnabled(false);
		jumpButton.setEnabled(false);
		resupplyButton.setEnabled(true);
		refuelButton.setEnabled(true);
		
		removeKeyListener(-91, upPSSpeedCommand);
		removeKeyListener('i', upPSSpeedCommand);
		removeKeyListener(-92, downPSSpeedCommand);
		removeKeyListener('d', downPSSpeedCommand);
		removeKeyListener(-93, turnPSLeftCommand);
		removeKeyListener('l', turnPSLeftCommand);
		removeKeyListener(-94, turnPSRightCommand);
		removeKeyListener('r', turnPSRightCommand);
		removeKeyListener(44, turnLauncherLeftCommand);
		removeKeyListener(46, turnLauncherRightCommand);
		removeKeyListener(-90, launchPSMissileCommand);
		removeKeyListener('j', jumpCommand);
		
		timer.cancel();
	}
	//Update the commands/key listeners when the game is resumed
	//and continue the timer
	public void updateCommandsOnPlay() {
		addAsteroidButton.setEnabled(true);
		addStationButton.setEnabled(true);
		addPSButton.setEnabled(true);
		upPSSpeedButton.setEnabled(true);
		downPSSpeedButton.setEnabled(true);
		turnPSLeftButton.setEnabled(true);
		turnPSRightButton.setEnabled(true);
		turnLauncherLeftButton.setEnabled(true);
		turnLauncherRightButton.setEnabled(true);
		launchPSMissileButton.setEnabled(true);
		jumpButton.setEnabled(true);
		resupplyButton.setEnabled(false);
		refuelButton.setEnabled(false);
		
		addKeyListener(-91, upPSSpeedCommand);
		addKeyListener('i', upPSSpeedCommand);
		addKeyListener(-92, downPSSpeedCommand);
		addKeyListener('d', downPSSpeedCommand);
		addKeyListener(-93, turnPSLeftCommand);
		addKeyListener('l', turnPSLeftCommand);
		addKeyListener(-94, turnPSRightCommand);
		addKeyListener('r', turnPSRightCommand);
		addKeyListener(44, turnLauncherLeftCommand);
		addKeyListener(46, turnLauncherRightCommand);
		addKeyListener(-90, launchPSMissileCommand);
		addKeyListener('j', jumpCommand);
		
		timer.schedule(timeMillis, true, this);
	}
	
	@Override
	//Update the GameWorld objects when run() is called
	//and randomize the creation of NPS and/or when a NPS fires a missile
	public void run() {
		// TODO Auto-generated method stub
		int min = 1;
		int max = 1500;
		int roll = RNG.genRandInt(min, max);
		
		if (roll >= 1490 && roll <= 1500) {
			gw.addNPS();
		}
		if (roll >= 1 && roll <= 10) {
			gw.launchNPSMissile();
		}
		
		gw.tick(timeMillis);
		mv.repaint();
	}
	
	//Play the Game Over sound and disable ALL commands/key listeners
	public void gameOver() {
		gw.playGameOverSound();
		this.updateCommandsOnPause();
		playPauseButton.setEnabled(false);
		refuelButton.setEnabled(false);
		resupplyButton.setEnabled(false);
		Command cOk = new Command("OK");
		//Print out a dialog box showing the final score
		Dialog.show("Game Over", "Final Score: " + gw.getScore(), cOk);
	}
	
}
