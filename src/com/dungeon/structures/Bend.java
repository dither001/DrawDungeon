package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

import model.Floor;
import view.Default;

public class Bend extends Passage {
	public boolean isRightTurn;

	/*
	 * CONSTRUCTORS
	 */
	private Bend(Floor dungeon, Point p, Orientation orient, boolean isRightTurn, int width) {
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
			int l = Floor.WALL_LENGTH;

			Point p = null;

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
				dungeon.passages.add(pass);
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

		if (Default.showOrigins) {
			g.setColor(Color.green);
			g.fillOval((int) origin.x, (int) origin.y, 10, 10);
		}
	}

	@Override
	public Point nextPoint() {
		Point point = null;
		int l = Floor.WALL_LENGTH;

		if (isRightTurn && orient.isNorth()) {
			point = new Point(origin.x + l, origin.y);

		} else if (orient.isNorth()) {
			point = origin.clone();

		} else if (isRightTurn && orient.isSouth()) {
			point = origin.clone();

		} else if (orient.isSouth()) {
			point = new Point(origin.x + l, origin.y);

		} else if (isRightTurn && orient.isEast()) {
			point = new Point(origin.x, origin.y + l);

		} else if (orient.isEast()) {
			point = origin.clone();

		} else if (isRightTurn && orient.isWest()) {
			point = new Point(origin.x, origin.y);

		} else if (orient.isWest()) {
			point = new Point(origin.x, origin.y + l);

		}

		return point;
	}

	/*
	 * STATIC METHODS
	 */
	public static Bend makeBend(Floor d, Point p, Orientation o, boolean isRightTurn, int width) {
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
