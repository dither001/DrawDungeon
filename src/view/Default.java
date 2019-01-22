package view;

import com.dungeon.geometry.Point;

import model.Cursor;

public class Default {
	public static final int MAX_HORIZONTAL = 440; // 800
	public static final int MAX_VERTICAL = 340; // 600

	public static final int TOTAL_AREA = MAX_HORIZONTAL * MAX_VERTICAL;
	public static final double AREA_TO_MAP = 66.67;

	public static final Point MIDPOINT = new Point(MAX_HORIZONTAL * 0.5, MAX_VERTICAL * 0.5);

	public static Cursor cursor;

	/*
	 * OPTIONS
	 */
	public static boolean showGrid = false;
	public static boolean showOrigins = false;

	/*
	 * INITIALIZE
	 */
	static {
		cursor = new Cursor(Default.MIDPOINT);

	}

}
