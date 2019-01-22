package com.dungeon.misc;

import com.dungeon.geometry.*;

public abstract class Default {
	public static final int MAX_HORIZONTAL = 440; // 800
	public static final int MAX_VERTICAL = 340; // 600
	public static final int INSET = 20;

	public static final int TOTAL_AREA = MAX_HORIZONTAL * MAX_VERTICAL;
	public static final double AREA_TO_MAP = 66.67;

	public static final Point MIDPOINT = new Point(MAX_HORIZONTAL * 0.5, MAX_VERTICAL * 0.5);

	public static Cursor cursor;

	/*
	 * OPTIONS
	 */
	public static boolean showGrid = true;
	public static boolean showPassageIndex = false;
	public static boolean showOrigins = false;

	/*
	 * INITIALIZE
	 */
	static {
		cursor = new Cursor(Default.MIDPOINT);

	}

	/*
	 * STATIC METHODS
	 */
	public static boolean inBounds(Polygon polygon) {
		boolean inBounds = true;

		int left = (int) polygon.origin.x;
		int top = (int) polygon.origin.y;
		int right = (int) polygon.origin.x + polygon.length;
		int bottom = (int) polygon.origin.y + polygon.height;

		if (left < INSET)
			inBounds = false;

		if (right > MAX_HORIZONTAL - INSET)
			inBounds = false;

		if (top < 2*INSET)
			inBounds = false;

		if (bottom > MAX_VERTICAL - INSET)
			inBounds = false;

		return inBounds;
	}
}
