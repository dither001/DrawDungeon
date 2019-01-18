package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

import model.Dungeon;

public class Bend extends Passage {
	public boolean isRightTurn;

	/*
	 * CONSTRUCTORS
	 */
	private Bend(Dungeon dungeon, Point p, Orientation orient, boolean isRightTurn, int width) {
		this(p, width, width);

		this.dungeon = dungeon;
		this.orient = orient;
		this.isRightTurn = isRightTurn;

		this.isDeadEnd = false;
		this.advanced = false;
	}

	public Bend(Point p, int length, int height) {
		super(p, length, height);
	}

	/*
	 * INSTANCE METHODS
	 */

	@Override
	public void advance() {
		if (advanced != true) {
			advanced = true;

			Orientation o = isRightTurn ? orient.clockwise() : orient.counterClockwise();
			int l = Dungeon.WALL_LENGTH;

			Point p = null;
			// p = new Point(origin.x - Dungeon.WALL_LENGTH, origin.y);

			if (isRightTurn && orient.isNorth()) {
				p = new Point(origin.x + l, origin.y);

			} else if (orient.isNorth()) {
				p = origin.clone();

			} else if (isRightTurn && orient.isSouth()) {
				p = origin.clone();

			} else if (orient.isSouth()) {
				p = new Point(origin.x + l, origin.y);

			} else if (isRightTurn && orient.isEast()) {
				p = new Point(origin.x, origin.y + l);

			} else if (orient.isEast()) {
				p = origin.clone();

			} else if (isRightTurn && orient.isWest()) {
				p = new Point(origin.x, origin.y);

			} else if (orient.isWest()) {
				p = new Point(origin.x, origin.y + l);

			}

			Passage pass = Segment.makePassage(dungeon, p, o, 10, 10);
			if (pass.validPassage()) {
				Dungeon.passages.add(pass);
				System.out.printf("%s-facing passage turns %s\n", orient.toString(), o.toString());
				System.out.printf("New passage continues %s\n", o.toString());
			}

		}

	}

	@Override
	public void paint(Graphics g) {
		int top = (int) origin.y;
		int left = (int) origin.x;
		int bottom = (int) origin.y + height;
		int right = (int) origin.x + length;

		g.setColor(Color.WHITE);
		g.fillRect((int) origin.x, (int) origin.y, length, height);

		g.setColor(Color.BLACK);

		if (isRightTurn && orient.isNorth()) {
			g.drawLine(left, top, right, top);
			g.drawLine(left, top, left, bottom);
		} else if (orient.isNorth()) {
			g.drawLine(left, top, right, top);
			g.drawLine(right, top, right, bottom);
		} else if (isRightTurn && orient.isSouth()) {
			g.drawLine(left, bottom, right, bottom);
			g.drawLine(right, top, right, bottom);
		} else if (orient.isSouth()) {
			g.drawLine(left, bottom, right, bottom);
			g.drawLine(left, top, left, bottom);
		} else if (isRightTurn && orient.isEast()) {
			g.drawLine(right, top, right, bottom);
			g.drawLine(left, top, right, top);
		} else if (orient.isEast()) {
			g.drawLine(right, top, right, bottom);
			g.drawLine(left, bottom, right, bottom);
		} else if (isRightTurn && orient.isWest()) {
			g.drawLine(left, top, left, bottom);
			g.drawLine(left, bottom, right, bottom);
		} else if (orient.isWest()) {
			g.drawLine(left, top, left, bottom);
			g.drawLine(left, top, right, top);
		}

		if (Dungeon.showOrigins) {
			g.setColor(Color.green);
			g.fillOval((int) origin.x, (int) origin.y, 10, 10);
		}
	}

	/*
	 * STATIC METHODS
	 */
	public static Bend makeBend(Dungeon d, Point p, Orientation o, boolean isRightTurn, int width) {
		Point point = null;
		switch (o) {
		case EAST:
			point = new Point(p.x, p.y);
			break;
		case NORTH:
			point = new Point(p.x, p.y - width);
			break;
		case SOUTH:
			point = p.clone();
			break;
		case WEST:
			point = new Point(p.x - width, p.y);
			break;
		}

		return new Bend(d, point, o, isRightTurn, width);
	}

}
