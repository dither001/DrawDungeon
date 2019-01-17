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
	
	public static boolean showOrigins = true; 

	/*
	 * 
	 */
	private static double scale = 1.0;
	public static List<Chamber> chambers;
	public static List<Segment> passages;
	public static List<Door> doors;

	static {
		chambers = new ArrayList<Chamber>();
		passages = new ArrayList<Segment>();
		doors = new ArrayList<Door>();
	}

	/*
	 * CONSTRUCTORS
	 */
	public Dungeon() {
		super("Dungeon");

		Orientation o = Orientation.WEST;
		// for (int i = 0; i < 4; ++i) {
		// passages.add(Passage.makePassage(this, MIDPOINT, o));
		// System.out.println(o.toString());
		// o = o.clockwise();
		// }

		// doors.add(Door.makeDoor(this, MIDPOINT, o));
		// doors.get(0).beyondDoor();
		// passages.get(0).advance();
		// passages.get(1).advance();
		// passages.get(2).advance();

		chambers.add(Chamber.makeChamber(this, MIDPOINT, Orientation.random()));

		int length = doors.size();
		for (int i = 0; i < length; ++i)
			doors.get(i).beyondDoor();

		advancePassages();
		
//		length = passages.size();
//		for (int i = 0; i < length; ++i)
//			passages.get(i).advance();

		/*
		 * LAST STEPS (IN ORDER)
		 */
		setResizable(false);
		setSize(MAX_HORIZONTAL, MAX_VERTICAL);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/*
	 * INSTANCE METHODS
	 */
	public void paint(Graphics g) {

		g.setColor(Color.BLUE);
		for (int i = WALL_LENGTH; i < MAX_HORIZONTAL; i += WALL_LENGTH)
			g.drawLine(i, 0, i, MAX_VERTICAL);

		for (int i = WALL_LENGTH; i < MAX_VERTICAL; i += WALL_LENGTH)
			g.drawLine(0, i, MAX_HORIZONTAL, i);

		for (Chamber el : chambers)
			el.paint(g);

		for (Segment el : passages)
			el.paint(g);

		for (Door el : doors)
			el.paint(g);

		// g.setColor(Color.RED);
		// g.fillOval((int) MIDPOINT.x, (int) MIDPOINT.y, 10, 10);
	}

	private void advancePassages() {
		int index = 0;

		int length = passages.size();
		do {
			for (; index < length; ++index)
				passages.get(index).advance();

			length = passages.size();
		} while (index < length);

	}

	private boolean mappingDone() {
		return mappedArea() / TOTAL_AREA > AREA_TO_MAP;
	}

	private int mappedArea() {
		int area = 0;

		for (Chamber el : chambers)
			area += el.area();

		for (Segment el : passages)
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
