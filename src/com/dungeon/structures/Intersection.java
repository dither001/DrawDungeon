package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

public class Intersection extends Passage {

	/*
	 * CONSTRUCTORS
	 */
	private Intersection(Floor dungeon, Point p, Orientation orient, int width) {
		this(p, width, width);

		this.dungeon = dungeon;
		this.orient = orient;

		this.isDeadEnd = false;
		this.advanced = false;
	}

	public Intersection(Point origin, int length, int height) {
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
		if (orient.isNorth())
			g.drawLine(left, top, right, top);
		else if (orient.isSouth())
			g.drawLine(left, bottom, right, bottom);
		else if (orient.isEast())
			g.drawLine(right, top, right, bottom);
		else if (orient.isWest())
			g.drawLine(left, top, left, bottom);
	}

	@Override
	public void advance() {
		if (advanced != true) {
			advanced = true;

			Orientation lo = orient.clockwise(), ro = orient.counterClockwise();
			Point lp = null, rp = null;
			int l = dungeon.WALL_LENGTH;

			if (orient.isNorth()) {
				rp = origin.clone();
				lp = new Point(origin.x + l, origin.y);

			} else if (orient.isSouth()) {
				rp = new Point(origin.x + l, origin.y);
				lp = origin.clone();

			} else if (orient.isEast()) {
				rp = origin.clone();
				lp = new Point(origin.x, origin.y + l);

			} else if (orient.isWest()) {
				rp = new Point(origin.x, origin.y + l);
				lp = origin.clone();

			}

			Passage lPass = Segment.makePassage(dungeon, lp, lo, 10, 10);
			if (lPass.validPassage())
				dungeon.passages.add(lPass);

			Passage rPass = Segment.makePassage(dungeon, rp, ro, 10, 10);
			if (rPass.validPassage())
				dungeon.passages.add(rPass);

		}
	}

	/*
	 * STATIC METHODS
	 */
	public static Intersection makeIntersection(Floor d, Point p, Orientation o, int width) {
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

		return new Intersection(d, point, o, width);
	}
}
