package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

public class Chamber extends Polygon {
	private boolean isLargeRoom;
	private boolean[] edges;

	/*
	 * CONSTRUCTORS
	 */
	public Chamber(Point origin, int length, int height) {
		super(origin, length, height);

		this.isLargeRoom = (length * height > 1500) ? true : false;
		this.edges = new boolean[2 * length + 2 * height];
	}

	/*
	 * INSTANCE METHODS
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int) origin.x, (int) origin.y, length, height);

		g.setColor(Color.BLACK);
		g.drawRect((int) origin.x, (int) origin.y, length, height);
	}

	public boolean isLargeRoom() {
		return isLargeRoom;
	}

	/*
	 * STATIC METHODS
	 */
	public static Chamber makeChamber(Point origin, int length, int height) {
		return new Chamber(origin.clone(), length, height);
	}

}
