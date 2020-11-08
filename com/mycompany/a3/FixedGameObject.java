package com.mycompany.a3;


public abstract class FixedGameObject extends GameObject {
	private static int uniqueID = 1;
	private int ID;
	
	//~~~~~~CONSTRUCTOR~~~~~~~~
	public FixedGameObject() {
		this.ID = uniqueID;
		uniqueID++;
	}
	
	//~~~~~~~String override~~~~~~~
	//Print out the ID of a Fixed Game Object
	//while  using the parent method in Game Object to print location and color
	public String toString() {
		String thisString = super.toString() + " ID=" + ID;
		return thisString;					
	}
}
