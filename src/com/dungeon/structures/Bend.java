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
	public Bend(Dungeon dungeon, Point p, Orientation orient, boolean isRightTurn, int width) {
		this(p, width, width);

		this.dungeon = dungeon;
		this.orient = orient;
		this.isRightTurn = isRightTurn;

		this.advanced = false;
	}

	public Bend(Point p, int length, int height) {
		super(p, length, height);
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
		if (Orientation.isNorthOrSouth(orient)) {
			g.drawLine(left, top, left, bottom);
			g.drawLine(right, top, right, bottom);
		} else {
			g.drawLine(left, top, right, top);
			g.drawLine(left, bottom, right, bottom);
		}

		if (Dungeon.showOrigins) {
			g.setColor(Color.green);
			g.fillOval((int) origin.x, (int) origin.y, 10, 10);
		}
	}

}
