package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.dungeon.geometry.*;
import com.dungeon.structures.*;

@SuppressWarnings("serial")
public class Dungeon extends JFrame {
	public static final int MAX_HORIZONTAL = 800;
	public static final int MAX_VERTICAL = 600;
	public static final double AREA_TO_MAP = 66.67;

	private static final int TOTAL_AREA = MAX_HORIZONTAL * MAX_VERTICAL;
	private static final Point MIDPOINT = new Point(MAX_HORIZONTAL * 0.5, MAX_VERTICAL * 0.5);

	public static final int WALL_LENGTH = 10;

	public static boolean showOrigins = false;
	public static int PASSAGE_CUTOFF = 500;

	/*
	 * 
	 */
	private static double scale = 1.0;
	public static Cursor cursor;

	public static List<Chamber> chambers;
	public static List<Passage> passages;
	public static List<Door> doors;

	static {
		cursor = new Cursor(MIDPOINT);

		chambers = new ArrayList<Chamber>();
		passages = new ArrayList<Passage>();
		doors = new ArrayList<Door>();
	}

	/*
	 * CONSTRUCTORS
	 */
	public Dungeon() {
		super("Dungeon");

		Orientation o = Orientation.NORTH;
		// for (int i = 0; i < 4; ++i) {
		// passages.add(Passage.makePassage(this, MIDPOINT, o));
		// System.out.println(o.toString());
		// o = o.clockwise();
		// }

		// doors.add(Door.makeDoor(this, MIDPOINT, o));
		// doors.get(0).advance();

		chambers.add(Chamber.makeChamber(this, MIDPOINT, Orientation.random()));

		int length = doors.size();
		for (int i = 0; i < length; ++i)
			doors.get(i).advance();

		advancePassages();
		// FIXME - for testing
		System.out.println(passages.size());

		cursor.setShape(chambers.get(0));
		for (Point el : cursor.getPoints())
			System.out.println(el.toString());

		/*
		 * LAST STEPS (IN ORDER)
		 */
		setResizable(false);
		setSize(MAX_HORIZONTAL, MAX_VERTICAL);
		// setLocationRelativeTo(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/*
	 * INSTANCE METHODS
	 */
	public void paint(Graphics g) {

		g.setColor(Color.BLUE);
		// for (int i = WALL_LENGTH; i < MAX_HORIZONTAL; i += WALL_LENGTH)
		// g.drawLine(i, 0, i, MAX_VERTICAL);
		//
		// for (int i = WALL_LENGTH; i < MAX_VERTICAL; i += WALL_LENGTH)
		// g.drawLine(0, i, MAX_HORIZONTAL, i);

		for (Chamber el : chambers) {
			el.paint(g);
			// check for collision with Cursor

		}

		for (Passage el : passages) {
			el.paint(g);

		}

		for (Door el : doors)
			el.paint(g);

		g.setColor(Color.RED);
		cursor.paint(g);
	}

	private void advancePassages() {
		int index = 0;

		int length = passages.size();
		do {
			for (; index < length; ++index) {
				Passage p = passages.get(index);
				if (p.isDeadEnd != true)
					p.advance();
				// else
				// ((Chamber) p).checkForDoors();
			}

			length = passages.size();
		} while (index < length && index < PASSAGE_CUTOFF);

	}

	private boolean mappingDone() {
		return mappedArea() / TOTAL_AREA > AREA_TO_MAP;
	}

	private int mappedArea() {
		int area = 0;

		for (Chamber el : chambers)
			area += el.area();

		for (Passage el : passages)
			area += el.area();

		return area;
	}

	/*
	 * STATIC METHODS
	 */
	public static void main(String... args) {
		Dungeon dungeon = new Dungeon();
		dungeon.setVisible(true);
	}

}
