package com.mycompany.a3.interfaces;

import com.mycompany.a3.GameObject;

public interface IIterator {
	public boolean hasNext();
	public GameObject getNext();
	public GameObject getThis();
	public int getThisIndex();
	public void decreaseIndex();
	public void setIndex(int index);
}
