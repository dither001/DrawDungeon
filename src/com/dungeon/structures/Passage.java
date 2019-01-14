package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

public class Passage extends Polygon {
	public Orientation orient;

	/*
	 * CONSTRUCTORS
	 */
	public Passage(Point origin, Orientation orient, int length, int height) {
		this(origin, (Orientation.isNorthOrSouth(orient)) ? height : length,
				(Orientation.isNorthOrSouth(orient)) ? length : height);
		this.orient = orient;
	}

	public Passage(Point origin, int length, int height) {
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
		if (Orientation.isNorthOrSouth(orient)) {
			g.drawLine(left, top, left, bottom);
			g.drawLine(right, top, right, bottom);
		} else {
			g.drawLine(left, top, right, top);
			g.drawLine(left, bottom, right, bottom);
		}
	}

	/*
	 * STATIC METHODS
	 */
	public static Passage makePassage(Point origin, Orientation orient, int length, int height) {
		return new Passage(origin.clone(), orient, length, height);
	}

}
