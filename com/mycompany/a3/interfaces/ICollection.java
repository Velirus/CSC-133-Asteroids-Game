package com.mycompany.a3.interfaces;

import com.mycompany.a3.GameObject;

public interface ICollection {
	public void add(GameObject g);
	public void remove(int i);
	public void remove(int i, int j);
	public IIterator getIterator();
}
