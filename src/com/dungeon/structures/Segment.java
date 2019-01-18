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

			Passage pass = null, side = null;
			Door door = null;
			Point p = nextPoint();
			int dice = Dice.roll(20);

			switch (dice) {
			case 1:
			case 2:
				// FINISHED
				pass = makePassage(dungeon, p, orient, 30, 10);
				pass.advanced = false;
				if (pass.validPassage()) {
					Dungeon.passages.add(pass);
					System.out.println("Passage continues 30 feet.");
				}
				break;
			case 3:
				// TODO: door on the left (testing)
				pass = makePassage(dungeon, p, orient, 40, 10);
				pass.advanced = false;
				if (pass.validPassage()) {
					Dungeon.passages.add(pass);
					door = Door.makeLeftSideDoor(pass);
					Dungeon.doors.add(door);
					System.out.printf("Door on the %s wall.\n", door.orient.toString());
				}
				break;
			case 4:
				// TODO: door on the right (testing)
				pass = makePassage(dungeon, p, orient, 40, 10);
				pass.advanced = false;
				if (pass.validPassage()) {
					Dungeon.passages.add(pass);
					door = Door.makeRightSideDoor(pass);
					Dungeon.doors.add(door);
					System.out.printf("Door on the %s wall.\n", door.orient.toString());
				}
				break;
			case 5:
				// FINISHED: 20-ft passage ends in door
				pass = makePassage(dungeon, p, orient, 20, 10);
				pass.makeDeadEnd();
				if (pass.validPassage()) {
					Dungeon.passages.add(pass);
					System.out.printf("%s-facing passage ends at a door.\n", orient.toString());
					Dungeon.doors.add(Door.makeDoor(dungeon, pass.nextPoint(), orient));
				}
				break;
			case 6:
			case 7:
				// TODO: right side passage (testing)
				pass = makePassage(dungeon, p, orient, 20, 10);
				side = SidePassage.makeSidePassage(dungeon, pass.nextPoint(), orient, true, 10);
				if (pass.validPassage() && side.validPassage()) {
					Dungeon.passages.add(pass);
					pass.advanced = true;
					Dungeon.passages.add(side);
					side.advance();
					System.out.printf("Side passage on %s wall.\n", side.orient.clockwise().toString());
				}
				break;
			case 8:
			case 9:
			case 10:
				// DEAD END
				pass = makePassage(dungeon, p, orient, 20, 10);
				pass.makeDeadEnd();
				if (pass.validPassage()) {
					Dungeon.passages.add(pass);
					System.out.println("20-foot passage is dead end.");
				}
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
				// FIXME - remove after testing
				pass = makePassage(dungeon, p, orient, 30, 10);
				pass.advanced = false;
				if (pass.validPassage()) {
					Dungeon.passages.add(pass);
					System.out.println("Passage continues 30 feet.");
				}
				break;
			}

		}
	}

	@Override
	public Point nextPoint() {
		int l = (Orientation.isNorthOrSouth(orient)) ? height : length;

		Point point = null;
		switch (orient) {
		case EAST:
			point = new Point(origin.x + l, origin.y);
			break;
		case NORTH:
			point = origin.clone();
			break;
		case SOUTH:
			point = new Point(origin.x, origin.y + l);
			break;
		case WEST:
			point = origin.clone();
			break;
		}

		return point;
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
