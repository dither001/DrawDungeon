package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;
import com.dungeon.misc.Dice;

import model.Dungeon;

public class Chamber extends Polygon {
	public Dungeon dungeon;
	private boolean isLargeRoom;
	private boolean[] edges;

	/*
	 * CONSTRUCTORS
	 */
	public Chamber(Dungeon dungeon, Point origin, Orientation orient, int length, int height) {
		this(origin, length, height);

		this.dungeon = dungeon;
		this.isLargeRoom = (length * height > 1500) ? true : false;
		this.edges = new boolean[numberOfSegments()];

		int doors = Door.numberOfExits(this);
		for (int i = 0; i < doors; ++i) {
			// TODO - place doors
		}

		initializeDoors();
		checkForDoors();

		System.out.println(orient.toString());
		System.out.println(isLargeRoom);
		System.out.println(edges.length);
		System.out.println(doors);
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

		if (Dungeon.showOrigins) {
			g.setColor(Color.green);
			g.fillOval((int) origin.x, (int) origin.y, 10, 10);
		}
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
		return perimeter() / Dungeon.WALL_LENGTH;
	}

	private void initializeDoors() {
		for (int i = 0; i < edges.length; ++i)
			edges[i] = false;
	}

	public void checkForDoors() {
		int l = length / Dungeon.WALL_LENGTH;
		int h = height / Dungeon.WALL_LENGTH;

		double x = origin.x, y = origin.y;

		boolean corner = false;
		int counter = 0;
		Orientation orient = Orientation.NORTH;
		for (int i = 0; i < edges.length; ++i) {
			// roll for door
			if (edges[i] != true && Dice.roll(10) > 5)
				edges[i] = true;

			// make door
			if (edges[i] && i == 0)
				Dungeon.doors.add(Door.makeDoor(dungeon, origin.clone(), orient));
			else if (edges[i])
				Dungeon.doors.add(Door.makeDoor(dungeon, new Point(x, y), orient));

			// adjust position after second corner
			if (i + 1 == l + h) {
				x -= Dungeon.WALL_LENGTH;
			}

			// adjust position after third corner
			if (i + 1 == 2 * l + h) {
				x += Dungeon.WALL_LENGTH;
				y -= Dungeon.WALL_LENGTH;
			}

			if (i + counter < l) {
				x += Dungeon.WALL_LENGTH;
			} else if (i + counter < l + h) {
				y += Dungeon.WALL_LENGTH;
			} else if (i < l + h + l) {
				x -= Dungeon.WALL_LENGTH;
			} else {
				y -= Dungeon.WALL_LENGTH;
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

	/*
	 * STATIC METHODS
	 */
	public static Chamber makeChamber(Dungeon dungeon, Point origin, Orientation orient) {
		int dice = Dice.roll(20);
		int length = 10, height = 10;

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

		return new Chamber(dungeon, origin.clone(), orient, (Orientation.isNorthOrSouth(orient)) ? length : height,
				(Orientation.isNorthOrSouth(orient)) ? height : length);
	}

}
