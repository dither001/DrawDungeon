package com.dungeon.geometry;

public abstract class Polygon implements Shape {
	public Point[] shape;
	public Point origin;
	public Orientation orient;

	public int length;
	public int height;

	/*
	 * CONSTRUCTORS
	 */
	public Polygon(Point origin, int length, int height) {
		this.origin = origin;
		this.length = length;
		this.height = height;

		Point tl = new Point(origin.x, origin.y);
		Point tr = new Point(origin.x + length, origin.y);
		Point br = new Point(origin.x + length, origin.y + height);
		Point bl = new Point(origin.x, origin.y + height);

		this.shape = new Point[] { tl, tr, br, bl };
	}

	/*
	 * INSTANCE METHODS
	 */
	public boolean collision(Polygon other) {
		boolean collision = false;

		for (Point el : other.shape) {
			if (this.contains(el)) {
				collision = true;
				break;
			}
		}

		return collision;
	}

	// Uses ray-casting algorithm
	public boolean contains(Point other) {
		Point[] points = shape;

		double crossingNumber = 0;
		for (int i = 0, j = 1; i < shape.length; i++, j = (j + 1) % shape.length) {
			if ((((points[i].x < other.x) && (other.x <= points[j].x))
					|| ((points[j].x < other.x) && (other.x <= points[i].x)))
					&& (other.y > points[i].y
							+ (points[j].y - points[i].y) / (points[j].x - points[i].x) * (other.x - points[i].x))) {

				crossingNumber++;
			}
		}

		return crossingNumber % 2 == 1;
	}
}
