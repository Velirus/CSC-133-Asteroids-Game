package com.mycompany.a3;


import java.util.ArrayList;

import com.mycompany.a3.interfaces.ICollection;
import com.mycompany.a3.interfaces.IIterator;

public class GameWorldCollection implements ICollection {
	private ArrayList<GameObject> store = new ArrayList<GameObject>();
	/*
	 * Add objects into collection
	 */
	public void add (GameObject g) {
		store.add(g);
	}
	/*
	 * Remove one object in collection
	 */
	public void remove(int i) {
		store.remove(i);
	}
	
	/*
	 * Remove two objects in collection
	 */
	public void remove(int i, int j) {
		/*
		 * Remove the object in the higher position first, and then remove the other object
		 */
		if (i > j) {
			store.remove(i);
			store.remove(j);
		}
		else {
			store.remove(j);
			store.remove(i);
		}
	}
	@Override
	/*
	 * Return a new GameCollectionIterator
	 */
	public IIterator getIterator() {
		return new GameCollectionIterator();
	}
	
	private class GameCollectionIterator implements IIterator{
		private int index = -1;
		
		/*
		 * Check and see if the ArrayList has any other objects in queue
		 */
		public boolean hasNext() {
			if (store.size() <= 0) {
				return false;
			}
			else if (index == store.size() - 1) {
				return false;
			}
			else {
				return true;
			}
		}
		/*
		 * Increase the index by 1 and get the next GameObject in the ArrayList
		 */
		public GameObject getNext() {
			index = index + 1;
			return store.get(index);
		}
		/*
		 * Get the current (this) GameObject in the ArrayList without increasing the index
		 */
		public GameObject getThis() {
			return store.get(index);
		}
		/*
		 * Get the current (this) index
		 */
		public int getThisIndex() {
			return index;
		}
		/*
		 * Decrease the index by 1 in the case a PS or NPS Missile runs out of fuel and is removed
		 * since the ArrayList size will decrease by 1
		 */
		public void decreaseIndex() {
			index = index - 1;
		}
		public void setIndex(int index) {
			this.index = this.index + index;
			
		}
	}

}
