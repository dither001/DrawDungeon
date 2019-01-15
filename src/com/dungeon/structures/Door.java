package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;
import com.dungeon.misc.*;

public class Door extends Polygon {
	public Orientation orient;

	/*
	 * CONSTRUCTORS
	 */
	public Door(Point origin, Orientation orient) {
		this(origin, Orientation.isNorthOrSouth(orient) ? 8 : 4, Orientation.isNorthOrSouth(orient) ? 4 : 8);
		this.orient = orient;
	}

	public Door(Point origin, int length, int height) {
		super(origin, length, height);
	}

	@Override
	public void paint(Graphics g) {
		int top, left;

		if (Orientation.isNorthOrSouth(orient)) {
			left = (int) (origin.x + (length / 4));
			top = (int) (origin.y - (height / 4));
		} else {
			left = (int) (origin.x - (length / 4));
			top = (int) (origin.y + (height / 4));
		}

		g.setColor(Color.WHITE);
		g.fillRect(left, top, length, height);

		g.setColor(Color.BLACK);
		g.drawRect(left, top, length, height);
	}

	/*
	 * 
	 */
	public static Door makeDoor(Point origin, Orientation orient) {
		return new Door(origin, orient);
	}

	public static int numberOfExits(Chamber c) {
		int numberOfExits = 0, dice = Dice.roll(20);

		switch (dice) {
		case 1:
		case 2:
		case 3:
			numberOfExits = 0;
			break;
		case 4:
		case 5:
			numberOfExits = (c.isLargeRoom()) ? 1 : 0;
			break;
		case 6:
		case 7:
		case 8:
			numberOfExits = 1;
			break;
		case 9:
		case 10:
		case 11:
			numberOfExits = (c.isLargeRoom()) ? 2 : 1;
			break;
		case 12:
		case 13:
			numberOfExits = 2;
			break;
		case 14:
		case 15:
			numberOfExits = (c.isLargeRoom()) ? 3 : 2;
			break;
		case 16:
		case 17:
			numberOfExits = 3;
			break;
		case 18:
			numberOfExits = (c.isLargeRoom()) ? 4 : 3;
			break;
		case 19:
			numberOfExits = (c.isLargeRoom()) ? 5 : 4;
			break;
		case 20:
			numberOfExits = (c.isLargeRoom()) ? 6 : 4;
			break;
		}

		return numberOfExits;
	}
}
