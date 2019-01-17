package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;
import com.dungeon.misc.*;

import model.Dungeon;

public class Door extends Polygon {
	private Dungeon dungeon;
	public Orientation orient;
	public boolean rolled;

	/*
	 * CONSTRUCTORS
	 */
	public Door(Dungeon dungeon, Point origin, Orientation orient) {
		this(origin, Orientation.isNorthOrSouth(orient) ? 8 : 4, Orientation.isNorthOrSouth(orient) ? 4 : 8);
		this.dungeon = dungeon;
		this.orient = orient;
		this.rolled = false;
	}

	public Door(Point origin, int length, int height) {
		super(origin, length, height);
	}

	@Override
	public void paint(Graphics g) {
		int top, left;

		if (Orientation.isNorthOrSouth(orient)) {
			left = (int) (origin.x + (length / 4) - 1);
			top = (int) (origin.y - (height / 4) - 1);
		} else {
			left = (int) (origin.x - (length / 4) - 1);
			top = (int) (origin.y + (height / 4) - 1);
		}

		g.setColor(Color.WHITE);
		g.fillRect(left, top, length, height);

		g.setColor(Color.BLACK);
		g.drawRect(left, top, length, height);
	}

	public void checkOtherSideOfDoor() {
		if (rolled != true) {
			rolled = true;
			beyondDoor();
		}
	}

	public void beyondDoor() {

		Point p = null;
		switch (orient) {
		case EAST:
			p = origin.clone();
			break;
		case NORTH:
			p = origin.clone();
			// p = new Point(origin.x, origin.y);
			break;
		case SOUTH:
			p = origin.clone();
			break;
		case WEST:
			p = origin.clone();
			// p = new Point(origin.x - 10, origin.y);
			break;
		}

		int dice = Dice.roll(20);

		switch (dice) {
		case 1:
		case 2:
			// TODO t-intersection
			// System.out.printf("Opened door %s to t-intersection.\n", orient.toString());
			// Dungeon.passages.add(Passage.makePassage(dungeon, p, orient));
			break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			/*
			 * TODO - FINISHED; there should be multiple steps here -- 1) create a passage
			 * or chamber, 2) validate its placement, 3) add the passage to the List
			 */
			// System.out.printf("Opened door %s to passage.\n", orient.toString());
			// Dungeon.passages.add(Passage.makePassage(dungeon, p, orient, 20, 10));
			break;
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
			// TODO - chambers
			// System.out.printf("Opened door to chamber.\n", orient.toString());
			// Dungeon.chambers.add(Chamber.makeChamber(dungeon, origin, orient));
			break;
		case 19:
			// TODO - stairs
			// System.out.printf("Opened door %s to stairs.\n", orient.toString());
			break;
		case 20:
			// TODO - false door w/trap
			// System.out.printf("False %s door.\n", orient.toString());
			break;
		}

		// FIXME
		System.out.printf("Opened door %s to passage.\n", orient.toString());
		Dungeon.passages.add(Segment.makePassage(dungeon, p, orient, 30, 10));
	}

	/*
	 * 
	 */
	public static Door makeDoor(Dungeon dungeon, Point origin, Orientation orient) {
		return new Door(dungeon, origin, orient);
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
