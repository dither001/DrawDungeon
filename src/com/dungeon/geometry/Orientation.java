package com.dungeon.geometry;

public enum Orientation {
	NORTH, SOUTH, EAST, WEST;

	/*
	 * STATIC METHODS
	 */
	public static boolean isNorthOrSouth(Orientation o) {
		boolean northSouth = false;

		if (o.equals(Orientation.NORTH) || o.equals(Orientation.SOUTH))
			northSouth = true;

		return northSouth;
	}
}
