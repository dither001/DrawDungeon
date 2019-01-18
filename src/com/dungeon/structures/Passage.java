package com.dungeon.structures;

import com.dungeon.geometry.*;

import model.Dungeon;

public abstract class Passage extends Polygon {
	public Dungeon dungeon;
	public Orientation orient;

	public boolean isDeadEnd;
	public boolean advanced;

	public Passage(Point origin, int length, int height) {
		super(origin, length, height);
	}

	/*
	 * INSTANCE METHODS
	 */
	public void advance() {
		advanced = true;
	}

	public int area() {
		return length * height;
	}

	public int perimeter() {
		return (2 * length + 2 * height);
	}

	public void makeDeadEnd() {
		isDeadEnd = true;
		advanced = true;
	}

	public Point nextPoint() {
		return null;
	}
	
	public int numberOfSegments() {
		return perimeter() / Dungeon.WALL_LENGTH;
	}

	public boolean validPassage() {
		boolean valid = true;
		int l = (Orientation.isNorthOrSouth(orient)) ? height : length;

		switch (orient) {
		case EAST:
			valid = (origin.x + l > Dungeon.MAX_HORIZONTAL - 20) ? false : valid;
			break;
		case NORTH:
			valid = (origin.y < 40) ? false : valid;
			break;
		case SOUTH:
			valid = (origin.y + l > Dungeon.MAX_VERTICAL - 20) ? false : valid;
			break;
		case WEST:
			valid = (origin.x < 20) ? false : valid;
			break;
		}

		return valid;
	}

}
