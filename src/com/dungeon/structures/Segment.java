package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;
import com.dungeon.misc.Dice;

import model.Dungeon;

public class Segment extends Passage {
	/*
	 * CONSTRUCTORS
	 */
	private Segment(Dungeon dungeon, Point origin, Orientation orient, int length, int height) {
		this(origin, length, height);

		this.dungeon = dungeon;
		this.orient = orient;

		this.isDeadEnd = false;
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

		if (isDeadEnd) {
			switch (orient) {
			case EAST:
				g.drawLine(right, top, right, bottom);
				break;
			case NORTH:
				g.drawLine(left, top, right, top);
				break;
			case SOUTH:
				g.drawLine(left, bottom, right, bottom);
				break;
			case WEST:
				g.drawLine(left, top, left, bottom);
				break;
			}
		}

		if (Dungeon.showOrigins) {
			g.setColor(Color.green);
			g.fillOval((int) origin.x, (int) origin.y, 10, 10);
		}
	}

	@Override
	public void advance() {
		if (advanced != true) {
			advanced = true;

			int l = (Orientation.isNorthOrSouth(orient)) ? height : length;
			int w = (Orientation.isNorthOrSouth(orient)) ? length : height;

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

			int dice = Dice.roll(20);
			Passage pass = null;
			switch (dice) {
			case 1:
			case 2:
				// FINISHED
				pass = makePassage(dungeon, p, orient, 30, 10);
				if (pass.validPassage())
					Dungeon.passages.add(pass);
				break;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				// DEAD END
				pass = makePassage(dungeon, p, orient, 20, 10);
				pass.makeDeadEnd();
				if (pass.validPassage())
					Dungeon.passages.add(pass);
				break;
			case 11:
			case 12:
				// LEFT TURN
				pass = Bend.makeBend(dungeon, p, orient, false, w);
				if (pass.validPassage()) {
					Dungeon.passages.add(pass);
					pass.advance();
				}
				break;
			case 13:
			case 14:
				// RIGHT TURN
				pass = Bend.makeBend(dungeon, p, orient, true, w);
				if (pass.validPassage()) {
					Dungeon.passages.add(pass);
					pass.advance();
				}
				break;
			case 15:
			case 16:
			case 17:
			case 18:
			case 19:
			case 20:
				break;
			}

		}
	}

	@Override
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
