package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;

public class Stairs extends Passage {

	public boolean goingUp;

	/*
	 * CONSTRUCTORS
	 */
	private Stairs(Floor dungeon, Point position, boolean goingUp) {
		this(position, 10, 10);

		this.dungeon = dungeon;
		this.orient = Orientation.NORTH;

		this.goingUp = goingUp;
	}

	public Stairs(Point origin, int length, int height) {
		super(origin, length, height);
	}

	/*
	 * INSTANCE METHODS
	 */
	public void advance() {
		if (advanced != true) {
			advanced = true;

			/*
			 * FIXME - testing
			 */
		}
	}

	@Override
	public void paint(Graphics g) {
		int top = (int) origin.y;
		int left = (int) origin.x;
		int bottom = (int) origin.y + length;
		int right = (int) origin.x + length;

		int midpoint = (left + right) / 2;

		g.setColor(Color.WHITE);
		g.fillRect(left, top, length, height);

		g.setColor(Color.BLACK);
		g.drawRect(left, top, length, height);

		int[] xpts = new int[] { left, midpoint, right }, ypts;
		int npts = 3;

		if (goingUp)
			ypts = new int[] { bottom, top + 1, bottom };
		else
			ypts = new int[] { top, bottom - 1, top };

		g.fillPolygon(xpts, ypts, npts);
	}

	/*
	 * STATIC METHODS
	 */
	public static Stairs makeStairs(Floor f, Point position) {
		// TODO
		return new Stairs(f, position, false);
	}

}
