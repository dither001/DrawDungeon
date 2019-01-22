package com.dungeon.geometry;

import com.dungeon.structures.*;

import view.Default;

public abstract class Polygon implements Shape {
	public Point[] shape;
	public Point origin;
	public Orientation orient;

	public int length;
	public int height;

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

	// Uses ray-casting algorithm
	public boolean contains(Point point) {
		Point[] points = shape.clone();
		double crossingNumber = 0;

		for (int i = 0, j = 1; i < shape.length; i++, j = (j + 1) % shape.length) {
			if ((((points[i].x < point.x) && (point.x <= points[j].x))
					|| ((points[j].x < point.x) && (point.x <= points[i].x)))
					&& (point.y > points[i].y
							+ (points[j].y - points[i].y) / (points[j].x - points[i].x) * (point.x - points[i].x))) {
				crossingNumber++;
			}
		}
		return crossingNumber % 2 == 1;
	}

	public boolean collision(Polygon other) {
		boolean collision = false;

		for (Point el : other.shape.clone()) {
			if (this.contains(el)) {
				collision = true;
				break;
			}
		}

		return collision;
	}

	/*
	 * PRIVATE METHODS
	 */
	private double findArea() {
		double sum = 0;

		for (int i = 0, j = 1; i < shape.length; i++, j = (j + 1) % shape.length) {
			sum += shape[i].x * shape[j].y - shape[j].x * shape[i].y;
		}

		return Math.abs(sum / 2);
	}
}
