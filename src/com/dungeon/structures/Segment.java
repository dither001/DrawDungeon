package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

import model.Dungeon;

public class Segment extends Passage {
	/*
	 * CONSTRUCTORS
	 */
	public Segment(Dungeon dungeon, Point origin, Orientation orient, int length, int height) {
		this(origin, length, height);

		this.dungeon = dungeon;
		this.orient = orient;
		this.advanced = false;
	}

	public Segment(Point origin, int length, int height) {
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

		if (Dungeon.showOrigins) {
			g.setColor(Color.green);
			g.fillOval((int) origin.x, (int) origin.y, 10, 10);
		}
	}

	public void advance() {
		if (advanced != true) {
			advanced = true;
			int l = (Orientation.isNorthOrSouth(orient)) ? height : length;

			Point p = null;
			switch (orient) {
			case EAST:
				p = new Point(origin.x + l, origin.y);
				break;
			case NORTH:
				p = origin;
				break;
			case SOUTH:
				p = new Point(origin.x, origin.y + l);
				break;
			case WEST:
				p = origin;
				break;
			}

			// TODO - should be more complex than this
			Segment pass = makePassage(dungeon, p, orient, 30, 10);
			if (pass.validPassage())
				Dungeon.passages.add(pass);
		}
	}

	private boolean validPassage() {
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

	/*
	 * STATIC METHODS
	 */
	public static Segment makePassage(Dungeon d, Point o1, Orientation o2) {
		// TODO - random passage
		return makePassage(d, o1.clone(), o2, 30, 10);
	}

	public static Segment makePassage(Dungeon d, Point o1, Orientation o2, int length, int width) {
		int l, h;

		if (Orientation.isNorthOrSouth(o2)) {
			l = width;
			h = length;
		} else {
			l = length;
			h = width;
		}

		Point p = null;
		switch (o2) {
		case EAST:
			p = new Point(o1.x, o1.y);
			break;
		case NORTH:
			p = new Point(o1.x, o1.y - length);
			break;
		case SOUTH:
			p = o1.clone();
			break;
		case WEST:
			p = new Point(o1.x - length, o1.y);
			break;
		}

		return new Segment(d, p, o2, l, h);
	}

}
