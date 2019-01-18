package model;

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
		Point[] pts = getPoints();
		int[] xpts = new int[pts.length];
		int[] ypts = new int[pts.length];
		int npts = pts.length;

		for (int i = 0; i < npts; i++) {
			xpts[i] = (int) pts[i].x;
			ypts[i] = (int) pts[i].y;
		}

		g.setColor(Color.BLUE);
		g.drawPolygon(xpts, ypts, npts);
		g.fillPolygon(xpts, ypts, npts);
	}

	public void setShape(Polygon polygon) {
		this.origin = polygon.origin;
		int l = polygon.length, h = polygon.height;

		Point tl = new Point(origin.x, origin.y);
		Point tr = new Point(origin.x + l, origin.y);
		Point br = new Point(origin.x + l, origin.y + h);
		Point bl = new Point(origin.x, origin.y + h);

		this.shape = new Point[] { tl, tr, br, bl };
	}

}
