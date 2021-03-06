package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.dungeon.geometry.*;
import com.dungeon.misc.Default;
import com.dungeon.misc.Dice;

public class Chamber extends Polygon {
	public Floor dungeon;
	private boolean isLargeRoom;
	private boolean[] edges;

	/*
	 * CONSTRUCTORS
	 */
	private Chamber(Floor dungeon, Point origin, Orientation orient, int length, int height) {
		this(origin, length, height);

		this.dungeon = dungeon;
		this.orient = orient;

		this.isLargeRoom = (length * height > 1500) ? true : false;
		this.edges = new boolean[numberOfSegments()];

		int doors = Door.numberOfExits(this);
		for (int i = 0; i < doors; ++i) {
			// TODO - place doors
		}

		initializeDoors();
	}

	public Chamber(Point origin, int length, int height) {
		super(origin, length, height);
	}

	/*
	 * INSTANCE METHODS
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int) origin.x, (int) origin.y, length, height);

		g.setColor(Color.BLACK);
		g.drawRect((int) origin.x, (int) origin.y, length, height);
	}

	public boolean isLargeRoom() {
		return isLargeRoom;
	}

	public int area() {
		return length * height;
	}

	public int perimeter() {
		return (2 * length + 2 * height);
	}

	public int numberOfSegments() {
		return perimeter() / Default.WALL_LENGTH;
	}

	private void initializeDoors() {
		for (int i = 0; i < edges.length; ++i)
			edges[i] = false;
	}

	public void checkForDoors() {
		checkForDoors(9);
	}

	public void checkForDoors(int doors) {
		int waLength = Default.WALL_LENGTH;
		int l = length / waLength;
		int h = height / waLength;

		double x = origin.x, y = origin.y;

		boolean corner = false;
		int counter = 0;
		Orientation orient = Orientation.NORTH;
		for (int i = 0; i < edges.length; ++i) {
			// roll for door
			if (edges[i] != true && Dice.roll(10) > doors)
				edges[i] = true;

			// make door
			if (edges[i] && i == 0)
				dungeon.doors.add(Door.makeDoor(dungeon, origin.clone(), orient));
			else if (edges[i])
				dungeon.doors.add(Door.makeDoor(dungeon, new Point(x, y), orient));

			// adjust position after second corner
			if (i + 1 == l + h) {
				x -= waLength;
			}

			// adjust position after third corner
			if (i + 1 == 2 * l + h) {
				x += waLength;
				y -= waLength;
			}

			if (i + counter < l) {
				x += waLength;
			} else if (i + counter < l + h) {
				y += waLength;
			} else if (i < l + h + l) {
				x -= waLength;
			} else {
				y -= waLength;
			}

			if (corner) {
				corner = false;
				orient = orient.clockwise();
			} else if (i + 2 == l) {
				corner = true;
			} else if (i + 2 == l + h) {
				corner = true;
			} else if (i + 2 == l + h + l) {
				corner = true;
			}
		}
	}

	public boolean validChamber() {
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

	/*
	 * STATIC METHODS
	 */
	public static Chamber makeChamber(Floor d, Point p, Orientation o) {
		int length = 10, height = 10;
		int dice = Dice.roll(20);

		switch (dice) {
		case 1:
		case 2:
			length = 20;
			height = 20;
			break;
		case 3:
		case 4:
			length = 30;
			height = 30;
			break;
		case 5:
		case 6:
			length = 40;
			height = 40;
			break;
		case 7:
		case 8:
		case 9:
			length = 20;
			height = 30;
			break;
		case 10:
		case 11:
		case 12:
			length = 30;
			height = 40;
			break;
		case 13:
		case 14:
			length = 40;
			height = 50;
			break;
		case 15:
			length = 50;
			height = 80;
			break;
		case 16:
			length = 30;
			height = 30;
			break;
		case 17:
			length = 50;
			height = 50;
			break;
		case 18:
			length = 40;
			height = 40;
			break;
		case 19:
			length = 60;
			height = 60;
			break;
		case 20:
			length = 40;
			height = 60;
			break;
		}

		return makeChamber(d, p.clone(), o, length, height);
	}

	public static Chamber makeChamber(Floor dungeon, Point p, Orientation o, int length, int width) {
		ArrayList<Chamber> list = new ArrayList<Chamber>();
		int l, h, waLength = Default.WALL_LENGTH;

		if (Orientation.isNorthOrSouth(o)) {
			l = width;
			h = length;
		} else {
			l = length;
			h = width;
		}

		Chamber c = null;
		Point point = null;
		switch (o) {
		case EAST:
			point = new Point(p.x, p.y);
			list.add(new Chamber(dungeon, point, o, l, h));
			break;
		case NORTH:
			list.add(new Chamber(dungeon, new Point(p.x, p.y - length), o, l, h));
			for (int i = (int) p.x; i > (int) p.x - width; i -= waLength) {
				point = new Point(i, p.y - length);
				c = new Chamber(dungeon, point, o, l, h);
				if (c.validChamber())
					list.add(c);
			}
			break;
		case SOUTH:
			point = p.clone();
			list.add(new Chamber(dungeon, point, o, l, h));
			break;
		case WEST:
			point = new Point(p.x - length, p.y);
			list.add(new Chamber(dungeon, point, o, l, h));
			break;
		}

		return Dice.randomFromList(list);
	}

}
