package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

import model.Floor;
import view.Default;

public class SidePassage extends Passage {
	public boolean isRightTurn;

	/*
	 * CONSTRUCTORS
	 */
	private SidePassage(Floor dungeon, Point origin, Orientation orient, boolean isRightTurn, int length,
			int height) {
		this(origin, length, height);

		this.dungeon = dungeon;
		this.orient = orient;

		this.isRightTurn = isRightTurn;
		this.isDeadEnd = false;
		this.advanced = false;
	}

	public SidePassage(Point origin, int length, int height) {
		super(origin, length, height);
	}

	/*
	 * INSTANCE METHODS
	 */
	@Override
	public void advance() {
		if (advanced != true) {
			advanced = true;

			Orientation o = isRightTurn ? orient.clockwise() : orient.counterClockwise();
			Point forP = nextPoint(), sideP = null;
			int l = Floor.WALL_LENGTH;

			switch (orient) {
			case EAST:
				sideP = isRightTurn ? new Point(origin.x, origin.y + l) : origin.clone();
				break;
			case NORTH:
				sideP = isRightTurn ? new Point(origin.x + l, origin.y) : origin.clone();
				break;
			case SOUTH:
				sideP = isRightTurn ? origin.clone() : new Point(origin.x + l, origin.y);
				break;
			case WEST:
				sideP = isRightTurn ? origin.clone() : new Point(origin.x, origin.y + l);
				break;
			}

			Passage pass = Segment.makePassage(dungeon, forP, orient, 10, 10);
			if (pass.validPassage())
				dungeon.passages.add(pass);

			pass = Segment.makePassage(dungeon, sideP, o, 10, 10);
			if (pass.validPassage())
				dungeon.passages.add(pass);
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

	@Override
	public void paint(Graphics g) {
		int top = (int) origin.y;
		int left = (int) origin.x;
		int bottom = (int) origin.y + height;
		int right = (int) origin.x + length;

		g.setColor(Color.WHITE);
		g.fillRect((int) origin.x, (int) origin.y, length, height);

		g.setColor(Color.BLACK);
		if (isRightTurn && orient.isNorth())
			g.drawLine(left, top, left, bottom);
		else if (orient.isNorth())
			g.drawLine(right, top, right, bottom);
		else if (isRightTurn && orient.isSouth())
			g.drawLine(right, top, right, bottom);
		else if (orient.isSouth())
			g.drawLine(left, top, left, bottom);
		else if (isRightTurn && orient.isEast())
			g.drawLine(left, top, right, top);
		else if (orient.isEast())
			g.drawLine(left, bottom, right, bottom);
		else if (isRightTurn && orient.isWest())
			g.drawLine(left, bottom, right, bottom);
		else if (orient.isWest())
			g.drawLine(left, top, right, top);

		if (Default.showOrigins) {
			g.setColor(Color.green);
			g.fillOval((int) origin.x, (int) origin.y, 10, 10);
		}
	}

	/*
	 * STATIC METHODS
	 */
	public static SidePassage makeSidePassage(Floor d, Point p, Orientation o, boolean isRightTurn, int width) {
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

		return new SidePassage(d, point, o, isRightTurn, width, width);
	}

}
