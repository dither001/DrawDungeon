package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

import model.Dungeon;

public class Passage extends Polygon {
	public Dungeon dungeon;
	public Orientation orient;

	/*
	 * CONSTRUCTORS
	 */
	public Passage(Dungeon dungeon, Point origin, Orientation orient, int length, int height) {
		this(origin, length, height);
		// this(origin, (Orientation.isNorthOrSouth(orient)) ? height : length,
		// (Orientation.isNorthOrSouth(orient)) ? length : height);
		this.dungeon = dungeon;
		this.orient = orient;
	}

	public Passage(Point origin, int length, int height) {
		super(origin, length, height);
	}

	/*
	 * INSTANCE METHODS
	 */
	@Override
	public void paint(Graphics g) {
		int top = (int) origin.y;
		int left = (int) origin.x;
		int bottom = (int) origin.y + height;
		int right = (int) origin.x + length;

		g.setColor(Color.WHITE);
		g.fillRect((int) origin.x, (int) origin.y, length, height);

		g.setColor(Color.BLACK);
		if (Orientation.isNorthOrSouth(orient)) {
			g.drawLine(left, top, left, bottom);
			g.drawLine(right, top, right, bottom);
		} else {
			g.drawLine(left, top, right, top);
			g.drawLine(left, bottom, right, bottom);
		}
	}

	public int area() {
		return length * height;
	}

	public int perimeter() {
		return (2 * length + 2 * height);
	}

	public int numberOfSegments() {
		return perimeter() / Dungeon.WALL_LENGTH;
	}

	/*
	 * STATIC METHODS
	 */
	public static Passage makePassage(Dungeon d, Point o1, Orientation o2) {
		// TODO - random passage
		return makePassage(d, o1.clone(), o2, 30, 10);
	}

	public static Passage makePassage(Dungeon d, Point o1, Orientation o2, int length, int width) {
		int l, h;
		if (Orientation.isNorthOrSouth(o2)) {
			l = length;
			h = width;
		} else {
			l = length;
			h = width;
		}

		Point p = null;
		switch (o2) {
		case EAST:
			p = o1.clone();
			break;
		case NORTH:
			p = new Point(o1.x, o1.y + l);
			break;
		case SOUTH:
			p = o1.clone();
			break;
		case WEST:
			p = new Point(o1.x + l, o1.y);
			break;
		}

		return new Passage(d, p, o2, l, h);
	}

	// private static Passage makePassage(Dungeon dungeon, Point o1, Orientation o2,
	// int length, int width) {
	// return new Passage(dungeon, o1.clone(), o2, length, width);
	// }

}
