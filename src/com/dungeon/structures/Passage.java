package com.dungeon.structures;

import com.dungeon.geometry.*;

import model.Dungeon;

public abstract class Passage extends Polygon {
	public Dungeon dungeon;
	public Orientation orient;
	public boolean advanced;

	public Passage(Point origin, int length, int height) {
		super(origin, length, height);
	}

	/*
	 * INSTANCE METHODS
	 */
	public int area() {
		return length * height;
	}

	public int perimeter() {
		return (2 * length + 2 * height);
	}

	public int numberOfSegments() {
		return perimeter() / Dungeon.WALL_LENGTH;
	}

}
