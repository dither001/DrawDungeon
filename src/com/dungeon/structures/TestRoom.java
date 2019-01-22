package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

public class TestRoom extends Polygon {
	public Floor dungeon;

	public TestRoom(Polygon polygon) {
		this(polygon.origin, polygon.length, polygon.height);

		if (polygon.getClass().equals(Chamber.class))
			this.dungeon = ((Chamber) polygon).dungeon;
		else if (polygon.getClass().equals(Passage.class))
			this.dungeon = ((Passage) polygon).dungeon;

		this.orient = polygon.orient;

		int l = polygon.length, h = polygon.height;
		int inset = 2;

		Point tl = new Point(origin.x + inset, origin.y + inset);
		Point tr = new Point(origin.x + l - inset, origin.y + inset);
		Point br = new Point(origin.x + l - inset, origin.y + h - inset);
		Point bl = new Point(origin.x + inset, origin.y + h - inset);

		this.shape = new Point[] { tl, tr, br, bl };
	}

	public TestRoom(Point origin, int length, int height) {
		super(origin, length, height);
	}

	@Override
	public void paint(Graphics g) {
		int[] xpts = new int[shape.length];
		int[] ypts = new int[shape.length];
		int npts = shape.length;

		for (int i = 0; i < npts; i++) {
			xpts[i] = (int) shape[i].x;
			ypts[i] = (int) shape[i].y;
		}

		// g.setColor(new Color(255, 0, 0, 127));
		g.setColor(Color.RED);
		g.drawPolygon(xpts, ypts, npts);
		g.fillPolygon(xpts, ypts, npts);
	}

}
