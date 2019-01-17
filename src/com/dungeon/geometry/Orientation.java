package com.dungeon.geometry;

import com.dungeon.misc.Dice;

public enum Orientation {
	NORTH, SOUTH, EAST, WEST;

	public Orientation clockwise() {
		Orientation direction = null;

		switch (this) {
		case EAST:
			direction = SOUTH;
			break;
		case NORTH:
			direction = EAST;
			break;
		case SOUTH:
			direction = WEST;
			break;
		case WEST:
			direction = NORTH;
			break;
		default:
			break;
		}

		return direction;
	}

	public Orientation counterClockwise() {
		Orientation direction = null;

		switch (this) {
		case EAST:
			direction = NORTH;
			break;
		case NORTH:
			direction = WEST;
			break;
		case SOUTH:
			direction = EAST;
			break;
		case WEST:
			direction = SOUTH;
			break;
		default:
			break;
		}

		return direction;
	}

	public boolean isNorth() {
		return this.equals(NORTH);
	}

	public boolean isSouth() {
		return this.equals(SOUTH);
	}

	public boolean isEast() {
		return this.equals(EAST);
	}

	public boolean isWest() {
		return this.equals(WEST);
	}

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
