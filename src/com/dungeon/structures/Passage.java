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

	public int numberOfSegments() {
		return perimeter() / Dungeon.WALL_LENGTH;
	}

	public boolean validPassage() {
		return true;
	}

}
