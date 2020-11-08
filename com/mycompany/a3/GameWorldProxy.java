package com.mycompany.a3;


import java.util.Observable;

import com.mycompany.a3.interfaces.IGameWorld;

public class GameWorldProxy extends Observable implements IGameWorld {
	private GameWorld gw;
	
	public GameWorldProxy(GameWorld gw) {
		this.gw = gw;
	}


	public void init() {
		// TODO Auto-generated method stub
		
	}


	public void addAsteroid() {
		// TODO Auto-generated method stub
		
	}


	public void addStation() {
		// TODO Auto-generated method stub
		
	}


	public void addPS() {
		// TODO Auto-generated method stub
		
	}


	public void addNPS() {
		// TODO Auto-generated method stub
		
	}


	public void upPSSpeed() {
		// TODO Auto-generated method stub
		
	}


	public void downPSSpeed() {
		// TODO Auto-generated method stub
		
	}


	public void turnPSLeft() {
		// TODO Auto-generated method stub
		
	}


	public void turnPSRight() {
		// TODO Auto-generated method stub
		
	}

	
	public void turnLauncherLeft() {
		// TODO Auto-generated method stub
		
	}

	
	public void turnLauncherRight() {
		// TODO Auto-generated method stub
		
	}

	
	public void launchPSMissile() {
		// TODO Auto-generated method stub
		
	}

	
	public void launchNPSMissile() {
		// TODO Auto-generated method stub
		
	}

	
	public void jump() {
		// TODO Auto-generated method stub
		
	}

	
	public void resupply() {
		// TODO Auto-generated method stub
		
	}
	
	public void tick(int elapsedTime) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void quit() {
		
	}
	
	public void setMapWidth(int x) {

	}
	
	public void setMapHeight(int y) {

	}
	
	public int getMapWidth() {
		return this.gw.getMapWidth();
	}
	
	public int getMapHeight() {
		return this.gw.getMapHeight();
	}

	public GameWorldCollection getGameWorldCollection() {
		// TODO Auto-generated method stub
		return this.gw.getGameWorldCollection();
	}
	
	public int getLives() {
		return this.gw.getLives();
	}
	
	public int getScore() {
		// TODO Auto-generated method stub
		return this.gw.getScore();
	}

	
	public int getTime() {
		// TODO Auto-generated method stub
		return this.gw.getTime();
	}

	
	public int getMissiles() {
		// TODO Auto-generated method stub
		return this.gw.getMissiles();
	}

	
	public boolean getSound() {
		// TODO Auto-generated method stub
		return this.gw.getSound();
	}

	public void setSound(boolean sound) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean getGamePlayStatus() {
		// TODO Auto-generated method stub
		return this.gw.getGamePlayStatus();
	}


	@Override
	public void setPauseMode() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setPlayMode() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void stopSound() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resumeSound() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setSoundCheckboxStatus(boolean s) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void refuel() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playGameOverSound() {
		// TODO Auto-generated method stub
		
	}



}
