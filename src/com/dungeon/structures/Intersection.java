package com.dungeon.structures;

import java.awt.Graphics;

import com.dungeon.geometry.*;

import model.Dungeon;

public class Intersection extends Passage {

	private Intersection(Dungeon dungeon, Point p, Orientation orient, int width) {
		this(p, width, width);

		this.dungeon = dungeon;
		this.orient = orient;

		this.isDeadEnd = false;
		this.advanced = false;
	}

	public Intersection(Point origin, int length, int height) {
		super(origin, length, height);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub

	}

	/*
	 * STATIC METHODS
	 */
	public static Intersection makeIntersection(Dungeon d, Point p, Orientation o, int width) {
		return new Intersection(d, p, o, width);
	}
}
