package com.dungeon.geometry;

public class Point implements Cloneable {
	public double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point clone() {
		return new Point(x, y);
	}

	/*
	 * STATIC METHODS
	 */
	public static Point midpoint(Point a, Point b) {
		return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
	}
}
