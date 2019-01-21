package com.dungeon.structures;

import com.dungeon.geometry.*;

import model.Floor;
import view.Default;

public abstract class Passage extends Polygon {
	public Floor dungeon;

	public boolean isDeadEnd;
	public boolean advanced;

	/*
	 * CONSTRUCTORS
	 */
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
		return perimeter() / dungeon.WALL_LENGTH;
	}

	public boolean validPassage() {
		boolean valid = true;
		int l = (Orientation.isNorthOrSouth(orient)) ? height : length;

		switch (orient) {
		case EAST:
			valid = (origin.x + l > Default.MAX_HORIZONTAL - 20) ? false : valid;
			break;
		case NORTH:
			valid = (origin.y < 40) ? false : valid;
			break;
		case SOUTH:
			valid = (origin.y + l > Default.MAX_VERTICAL - 20) ? false : valid;
			break;
		case WEST:
			valid = (origin.x < 20) ? false : valid;
			break;
		}

		Default.cursor.setShape(this);

		if (valid) {
			for (Passage el : dungeon.passages) {
				if (el.collision(Default.cursor)) {
					valid = false;
					break;
				}
			}
		}

		if (valid) {
			for (Chamber el : dungeon.chambers) {
				if (el.collision(Default.cursor)) {
					valid = false;
					break;
				}
			}
		}

		/*
		 * TODO - TESTING
		 */
		if (valid != true)
			System.out.println("Invalid placement");

		return valid;
	}


}
