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
	private static final int MAX_HORIZONTAL = 800;
	private static final int MAX_VERTICAL = 600;

	private static final Point MIDPOINT = new Point(MAX_HORIZONTAL * 0.5, MAX_VERTICAL * 0.5);

	private static final int WALL_LENGTH = 10;

	/*
	 * 
	 */
	private static double scale = 1.0;
	private static List<Chamber> chambers;
	private static List<Passage> passages;
	private static List<Door> doors;

	static {
		Point p = MIDPOINT.clone();
		chambers = new ArrayList<Chamber>();
		chambers.add(Chamber.makeChamber(MIDPOINT, WALL_LENGTH * 3, WALL_LENGTH * 3));
		
		passages = new ArrayList<Passage>();
//		passages.add(Passage.makePassage(p, Orientation.WEST, 30, 10));

		doors = new ArrayList<Door>();
	}

	/*
	 * CONSTRUCTORS
	 */
	public Dungeon() {
		super("Dungeon");

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

		for (Passage el : passages)
			el.paint(g);

		for (Door el : doors)
			el.paint(g);

		/*
		 * LEFTOVER TESTING
		 */

		// g.setColor(Color.BLACK);
		// g.fillOval((int) MIDPOINT.x, (int) MIDPOINT.y, WALL_LENGTH, WALL_LENGTH);

		// g.setColor(Color.BLACK);
		// g.drawOval(40, 40, 60, 60); // FOR CIRCLE
		//
		// g.setColor(Color.WHITE);
		// g.fillRect(80, 30, 200, 200); // FOR SQUARE
		// g.setColor(Color.BLACK);
		// g.drawRect(80, 30, 200, 200); // FOR SQUARE
		//
		// g.setColor(Color.WHITE);
		// g.fillRect(200, 100, 100, 200); // FOR RECT
		// g.setColor(Color.BLACK);
		// g.drawRect(200, 100, 100, 200); // FOR RECT
	}

	/*
	 * STATIC METHODS
	 */
	public static void main(String... args) {
		Dungeon dungeon = new Dungeon();
		dungeon.setVisible(true);
	}

}
