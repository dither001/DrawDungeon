package com.dungeon.structures;

import com.dungeon.geometry.*;
import com.dungeon.misc.Default;

public abstract class Passage extends Polygon {
	public Floor dungeon;

	public boolean isDeadEnd;
	public boolean advanced;

	/*
	 * CONSTRUCTORS
	 */
	public Passage(Point origin, int length, int height) {
		super(origin, length, height);
		this.isDeadEnd = false;
		this.advanced = false;
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
		return perimeter() / Default.WALL_LENGTH;
	}

	public boolean validPassage() {
		boolean valid = Default.inBounds(this);
		Default.cursor.setShape(this);

		for (Passage el : dungeon.passages) {
			if (el.collision(Default.cursor)) {
				valid = false;
				break;
			}
		}

		for (Chamber el : dungeon.chambers) {
			if (el.collision(Default.cursor)) {
				valid = false;
				break;
			}
		}

		return valid;
	}

}
