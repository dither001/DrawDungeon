package com.dungeon.geometry;

import com.dungeon.misc.Dice;

public enum Orientation {
	NORTH, SOUTH, EAST, WEST;

	/*
	 * STATIC METHODS
	 */
	public static Orientation random() {
		Orientation[] array = new Orientation[] { NORTH, SOUTH, EAST, WEST };

		return Dice.randomFromArray(array);
	}

	public static boolean isNorthOrSouth(Orientation o) {
		boolean northSouth = false;

		if (o.equals(Orientation.NORTH) || o.equals(Orientation.SOUTH))
			northSouth = true;

		return northSouth;
	}
}
