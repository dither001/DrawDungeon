package com.dungeon.misc;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

public class Cursor extends Polygon {

	/*
	 * CONSTRUCTORS
	 */
	public Cursor(Point position) {
		this(position, 10, 10);
	}

	public Cursor(Point origin, int length, int height) {
		super(origin, length, height);
	}

	/*
	 * INSTANCE METHODS
	 */
	@Override
	public void paint(Graphics g) {
		int[] xpts = new int[shape.length];
		int[] ypts = new int[shape.length];
		int npts = shape.length;

		for (int i = 0; i < npts; i++) {
			xpts[i] = (int) shape[i].x;
			ypts[i] = (int) shape[i].y;
		}

		g.setColor(Color.BLUE);
		g.drawPolygon(xpts, ypts, npts);
		g.fillPolygon(xpts, ypts, npts);
	}

	public void setShape(Polygon polygon) {
		this.origin = polygon.origin;
		int l = polygon.length, h = polygon.height;
		int inset = 2;

		Point tl = new Point(origin.x + inset, origin.y + inset);
		Point tr = new Point(origin.x + l - inset, origin.y + inset);
		Point br = new Point(origin.x + l - inset, origin.y + h - inset);
		Point bl = new Point(origin.x + inset, origin.y + h - inset);

		this.shape = new Point[] { tl, tr, br, bl };
	}

}
