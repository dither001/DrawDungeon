package com.dungeon.misc;

import java.util.Random;

public class Dice {
	private static final Random RAND = new Random();

	/*
	 * STATIC METHODS
	 */
	public static int roll(int faces) {
		return roll(1, faces);
	}

	public static int roll(int dice, int faces) {
		int result = 0;

		dice = (dice < 1) ? 1 : dice;
		faces = (faces < 1) ? 1 : faces;

		for (int i = 0; i < dice; ++i) {
			result += RAND.nextInt(faces) + 1;
		}

		return result;
	}

	/*
	 * UTILITY METHODS
	 */
	public static <T> T randomFromArray(T[] array) {
		T choice = array[Dice.roll(array.length) - 1];

		return choice;
	}


}
