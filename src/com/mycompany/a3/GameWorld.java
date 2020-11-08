package com.mycompany.a3;


import java.util.Observable;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.mycompany.a3.commands.*;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IGameWorld;
import com.mycompany.a3.interfaces.IIterator;
import com.mycompany.a3.interfaces.IMoveable;
import com.mycompany.a3.sound.*;

public class GameWorld extends Observable implements IGameWorld {	
	private Game g;
	//Game World Proxy
	private GameWorldProxy gwProxy = new GameWorldProxy(this);
	
	//~~~~~~GameWorld game state values~~~~~~~~
	private int timer = 0;
	private int elapsedTime;
	private static int score = 0;
	private int lives = 3;
	private static boolean sound = true;
	private boolean soundCheckboxStatus = true;
	private boolean gamePlayStatus = true;
	
	//Game World width and height (based off of MapView)
	private static int width;
	private static int height;
	
	//Variable to keep track of the Player Ship
	private static PlayerShip curPS;
	
	//Create a new GameCollection instead of using the previous ArrayList in A1
	private GameWorldCollection newStore = new GameWorldCollection();
	
	//Sounds
	private BGSound backgroundMusic = new BGSound("SpaceFighterLoop.mp3");;
	private Sound missileLaunch = new Sound("PloxisMissile.mp3");
	private Sound launcherSound = new Sound("railcanon_clipout.wav");
	private static Sound missileExplode = new Sound("break.wav");
	private Sound gameOver = new Sound("Retro_game_over_sound_effect.mp3");
	
	//~~~~~~~Constructor~~~~~
	public GameWorld (Game g) {
		this.g = g;
		backgroundMusic.play();
	}
	
	//Start the game off with a Player Ship
	public void init() {
		curPS = PlayerShip.getPS(this.getMapWidth(), this.getMapHeight());
		newStore.add(curPS);
		setChanged();
		notifyObservers(gwProxy);
	}
	
	//Add an asteroid
	public void addAsteroid() {
			newStore.add(new Asteroid(this.getMapWidth(), this.getMapHeight()));
			setChanged();
			notifyObservers(gwProxy);
		
	}
	//Add a Space Station
	public void addStation() {
			newStore.add(new SpaceStation(this.getMapWidth(), this.getMapHeight()));
			setChanged();
			notifyObservers(gwProxy);
		
	}
	//Add a Player Ship
	public void addPS() {
		if (curPS != null) {
			System.out.println("Error: Player Ship already created.");
		}
		else {
			curPS = PlayerShip.getPS(this.getMapWidth(), this.getMapHeight());
			newStore.add(curPS);
			setChanged();
			notifyObservers(gwProxy);
		}
	}
	//Increase the score based on the value passed in
	public static void increaseScore(int points) {
		score = score + points;
	}
	
	//Add a Non-Player Ship
	public void addNPS() {
			newStore.add(new NonPlayerShip(this.getMapWidth(), this.getMapHeight()));
			setChanged();
			notifyObservers(gwProxy);
	}
	//increase PS speed
	public void upPSSpeed() {
		if (curPS == null) {
			System.out.println("Error: No Player Ship created.");
		}
		else {
			curPS.accelPS();
			setChanged();
			notifyObservers(gwProxy);
		}
	}
	//decrease PS Speed
	public void downPSSpeed() {
		if (curPS == null) {
			System.out.println("Error: No Player Ship created.");
		}
		else {
			curPS.decelPS();
			setChanged();
			notifyObservers(gwProxy);
		}
	}
	//turn PS left by 15 degrees
	public void turnPSLeft() {
		if (curPS == null) {
			System.out.println("Error: No Player Ship created.");
		}
		else {
			curPS.changeDirection(-15);
			setChanged();
			notifyObservers(gwProxy);
		}
	}
	//turn PS right by 15 degrees
	public void turnPSRight() {
		if (curPS == null) {
			System.out.println("Error: No Player Ship created.");
		}
		else {
			curPS.changeDirection(15);
			setChanged();
			notifyObservers(gwProxy);
		}
	}

	//turn Missile Launcher left
	public void turnLauncherLeft() {if (curPS == null) {
			System.out.println("Error: No Player Ship created.");
		}
		else {
			curPS.turnMissileLauncher(-10);
			if (sound) {
				launcherSound.play();
			}
			setChanged();
			notifyObservers(gwProxy);
			
		}
	}
	//turn Missile Launcher right
	public void turnLauncherRight() {if (curPS == null) {
			System.out.println("Error: No Player Ship created.");
		}
		else {
			curPS.turnMissileLauncher(10);
			if (sound) {
				launcherSound.play();
			}
			setChanged();
			notifyObservers(gwProxy);
		}
	}
	//launch a PS missile
	public void launchPSMissile() {
		if (curPS == null ) {
			System.out.println("Error: No Player Ship created.");
		}
		else if (curPS.getMissiles() == 0) {
			System.out.println("Player Ship out of missiles.");
		}
		else {
			//Add a new PS Missile with the PS's location, speed+1, and Missile Launcher's direction
			newStore.add(new Missile(curPS.getLocX(), curPS.getLocY(), curPS.getSpeed() + 1,
					  curPS.getLauncherDirection(), "PS"));
			curPS.setMissiles(curPS.getMissiles() - 1);
			if (sound) {
				missileLaunch.play();
			}
			setChanged();
			notifyObservers(gwProxy);
		}
	}
	//launch a NPS missile
	public void launchNPSMissile() {
		NonPlayerShip curNPS = null;
		IIterator gameIterator = newStore.getIterator();
		while (gameIterator.hasNext() && curNPS == null) {
			if (gameIterator.getNext() instanceof NonPlayerShip) {
				//Set curNPS the current NPS in the iterator
				curNPS = (NonPlayerShip)gameIterator.getThis();
				if (curNPS.getMissiles() != 0) {
					curNPS.setMissiles(curNPS.getMissiles() - 1);
					//Add a new NPS Missile with the NPS's location, speed+1, and direction
					newStore.add(new Missile(curNPS.getLocX(), curNPS.getLocY(), curNPS.getSpeed() + 1,
							  curNPS.getDirection(), "NPS"));
					if (sound) {
						missileLaunch.play();
					}
					setChanged();
					notifyObservers(gwProxy);
					//Break from loop if the NPS fired a missile
					break;
				}
			}
			//Set curNPS to null if a NPS has not fired a missile
			curNPS = null;
		}
	}
	//Player Ship jumps through hyperspace
	public void jump() {
		if (curPS == null) {
			System.out.println("Error: No Player Ship created.");
		}
		else {
			curPS.setLocX(width/2);
			curPS.setLocY(height/2);
			//Set the PS's speed back to 0 on jump through hyperspace
			curPS.setSpeed(0);
			setChanged();
			notifyObservers(gwProxy);
		}
	}
	//Player Ship resupply missiles
	public static void resupply() {
		curPS.setMissiles(10);
	}
	
	//game clock has ticked
	public void tick(int elapsedTime) {
		this.elapsedTime = this.elapsedTime + elapsedTime;
		if (this.elapsedTime % 1000 == 0){
			timer = timer + 1;
		}
		IIterator gameIterator = newStore.getIterator();
		//Run through Iterator and update the respective commands for all Game Objects when the clock 'ticks'
		while(gameIterator.hasNext()) {
			//Go to the next index of the GameCollection
			gameIterator.getNext();
			//Execute move() for any instance of Missile
			if (gameIterator.getThis() instanceof Missile) {
				Missile missileObj = (Missile)gameIterator.getThis();
				missileObj.move(elapsedTime);
				//remove the Missile if the Missile is out of fuel (fuel = 0)
				if (missileObj.getFuel() <= 0) {
					int i = gameIterator.getThisIndex();
					newStore.remove(i);
					//Decrease the gameIterator index by 1
					gameIterator.decreaseIndex();
				}
			}
			//Execute move() for any other MoveableGameObject
			else if (gameIterator.getThis() instanceof IMoveable) {
				IMoveable mObj = (IMoveable)gameIterator.getThis();
				mObj.move(elapsedTime);
			}
			//Execute tickStation() for any Space Station
			else if (gameIterator.getThis() instanceof SpaceStation) {
				SpaceStation stationObj = (SpaceStation)gameIterator.getThis();
				stationObj.tickStation(elapsedTime);
			}
		}
		//Iterate through the GameWorldCollection to check for collisions
		gameIterator = newStore.getIterator();
		//Initialize an index for the second Iterator
		int iteratorIndex = 0;
		while(gameIterator.hasNext()) {
			//Go to the next index of the GameCollection
			ICollider object1 = (ICollider)gameIterator.getNext();
			IIterator secondIterator = newStore.getIterator();
			//set the index of the second Iterator to iteratorIndex
			secondIterator.setIndex(iteratorIndex);
			while (secondIterator.hasNext()) {
				ICollider object2 = (ICollider)secondIterator.getNext();
				//Make sure they're not the same objects
				if (object1 != object2) {
					if (object1.collidesWith(object2)) {
						object1.handleCollsion(object2);
					}
				}
			}
			//Increase the iteratorIndex by 1 once there are no other objects in the second Iterator
			iteratorIndex = iteratorIndex + 1;
		}
		//Remove any objects that have been marked as 'collided'
		gameIterator = newStore.getIterator();
		int i = 0;
		while(gameIterator.hasNext()) {
			ICollider object = (ICollider)gameIterator.getNext();
			if (object.getCollisionStatus()) {
				//If the PlayerShip is removed, then take away 1 life
				if (object instanceof PlayerShip) {
					lives = lives - 1;
					curPS.setPSNull();
					curPS = null;
					if (lives == 0) {
						g.gameOver();
					}
				}
				//Remove the object at the index and decrease the index if an object has been removed
				newStore.remove(i);
				gameIterator.decreaseIndex();
			}
			else {
				//Increase the index
				i = i + 1;
			}
		}
		setChanged();
		notifyObservers(gwProxy);	
	}
	
	
	public void quit() {
		Boolean bOk = Dialog.show("Confirm Quit", "Are you sure you want to quit the game?", "Cancel", "Ok");
		//Close the game if the user presses "Ok"
		if (!bOk) {
			Display.getInstance().exitApplication();
		}
		//Resume the game if the user presses "Cancel"
		else {
			System.out.println("Game will now resume!");
		}
	}
	//Set the width by passing the width of the MapView container
	public void setMapWidth(int x) {
		width = x;
	}
	//Set the height by passing the height of the MapView container
	public void setMapHeight(int y) {
		height = y;
	}
	//Return the width of the map
	public static int getMapWidth() {
		return width;
	}
	//Return the height of the map
	public static int getMapHeight() {
		return height;
	}
	//Return the Game Collection
	public GameWorldCollection getGameWorldCollection() {
		return newStore;
	}
	//Return the number of lives
	public int getLives() {
		return lives;
	}
	//Return the score
	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
	//Return the time
	public int getTime() {
		// TODO Auto-generated method stub
		return timer;
	}
	//Return the number of missiles the Player Ship has
	public int getMissiles() {
		// TODO Auto-generated method stub
		int missileAmount = 0;
		if (curPS != null) {
			missileAmount = curPS.getMissiles();
		}
		return missileAmount;
	}
	//Return the state of the sound (true = on, false = off)
	public boolean getSound() {
		// TODO Auto-generated method stub
		return sound;
	}
	//Change the state of the sound 
	public void setSound(boolean sound) {
		// TODO Auto-generated method stub
		this.sound = sound;
		setChanged();
		notifyObservers(gwProxy);
		
	}
	//Pause the game
	public void setPauseMode() {
		gamePlayStatus = false;
		setSound(false);
		stopSound();
		g.updateCommandsOnPause();
		g.getPlayPauseButton().setCommand(new PlayCommand(this));
		setChanged();
		notifyObservers(gwProxy);
	}
	//Resume the game
	public void setPlayMode() {
		//Set the sound of the game whether the SoundCheckbox is selected or not selected
		sound = soundCheckboxStatus;
		if (sound) {
			resumeSound();
		}
		gamePlayStatus = true;
		g.updateCommandsOnPlay();
		g.getPlayPauseButton().setCommand(new PauseCommand(this));
		setChanged();
		notifyObservers(gwProxy);
	}
	//Stop the background music
	public void stopSound() {
		backgroundMusic.pause();
	}
	//Resume the background music
	public void resumeSound() {
		backgroundMusic.play();
	}
	//Get the status of the game
	public boolean getGamePlayStatus() {
		return gamePlayStatus;
	}
	//Set the status of the Sound Checkbox
	public void setSoundCheckboxStatus(boolean s) {
		soundCheckboxStatus = s;
	}

	@Override
	//Refuel any Missile that has been selected
	public void refuel() {
		// TODO Auto-generated method stub
		IIterator itr = newStore.getIterator();
		while(itr.hasNext()) {
			GameObject obj = itr.getNext();
			if (obj instanceof Missile) {
				Missile missile = (Missile)obj;
				if (missile.isSelected()) {
					missile.refuelMissile();
				}
			}
			
		}
		
	}
	//Play the Missile-Asteroid collision sound if sound is enabled
	public static void playMissileAsteroidSound() {
		if (sound) {
			missileExplode.play();
		}
	}
	//Play the Game Over sound if sound is enabled
	public void playGameOverSound() {
		if (sound) {
			gameOver.play();
		}
	}
}
