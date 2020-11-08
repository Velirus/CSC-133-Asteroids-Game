package com.mycompany.a3.interfaces;

import com.mycompany.a3.GameWorldCollection;

public interface IGameWorld {
	public void init();
	public void addAsteroid();
	public void addStation();
	public void addPS();
	public static void increaseScore(int points) {
	}
	public void addNPS();
	public void upPSSpeed();
	public void downPSSpeed();
	public void turnPSLeft();
	public void turnPSRight();
	public void turnLauncherLeft();
	public void turnLauncherRight();
	public void launchPSMissile();
	public void launchNPSMissile();
	public void jump();
	public static void resupply() {}
	public void tick(int elapsedTime);
	public void quit();
	
	public void setMapWidth(int x);
	public void setMapHeight(int y);
	public static int getMapWidth() {
		return 0;
	}
	public static int getMapHeight() {
		return 0;
	}
	public GameWorldCollection getGameWorldCollection();
	public int getLives();
	public int getScore();
	public int getTime();
	public int getMissiles();
	public boolean getSound();
	public void setSound(boolean sound);
	
	public void setPauseMode();
	public void setPlayMode();
	public void stopSound();
	public void resumeSound();
	public boolean getGamePlayStatus();
	public void setSoundCheckboxStatus(boolean s);
	public void refuel();
	public static void playMissileAsteroidSound() {
	}
	public void playGameOverSound();
	
	
}
