package com.mycompany.a3.interfaces;

public interface ICollider {
	public boolean collidesWith(ICollider otherObject); 
	public void handleCollsion(ICollider otherObject);
	public void setColissionStatus(boolean status);
	public boolean getCollisionStatus();
	public int getSize();
}
