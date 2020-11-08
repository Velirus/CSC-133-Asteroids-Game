package com.mycompany.a3;

import java.util.Random;

public class RNG {
	public static int genRandInt(int min, int max) {
		Random r = new Random();
		int x = r.nextInt((max - min) + 1) + min;
		return x;
	}
}
