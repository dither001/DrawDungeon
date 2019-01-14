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

		if (dice >= 1 && dice <= 3)
			numberOfExits = 0;
		else if (dice == 4 || dice == 5)
			numberOfExits = (c.isLargeRoom()) ? 0 : 1;
		else if (dice >= 6 && dice <= 8)
			numberOfExits = 1;
		else if (dice >= 9 && dice <= 11)
			numberOfExits = (c.isLargeRoom()) ? 1 : 2;
		else if (dice == 12 || dice == 13)
			numberOfExits = 2;
		else if (dice == 14 || dice == 15)
			numberOfExits = (c.isLargeRoom()) ? 2 : 3;
		else if (dice == 16 || dice == 17)
			numberOfExits = 3;
		else if (dice == 18)
			numberOfExits = (c.isLargeRoom()) ? 3 : 4;
		else if (dice == 19)
			numberOfExits = (c.isLargeRoom()) ? 4 : 5;
		else if (dice == 20)
			numberOfExits = (c.isLargeRoom()) ? 4 : 6;

		return numberOfExits;
	}
}
