package com.dungeon.structures;

/*
 * BISMUTH ENGINE V.1
 * 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.dungeon.geometry.*;

import view.Default;

@SuppressWarnings("serial")
public class Floor extends JFrame {
	protected final int WALL_LENGTH = 10;
	public static int PASSAGE_CUTOFF = 500;

	/*
	 * 
	 */
	private static double scale = 1.0;
	public List<Chamber> chambers;
	public List<Passage> passages;
	public List<Door> doors;

	public List<TestRoom> testRooms;

	/*
	 * CONSTRUCTORS
	 */
	public Floor() {
		super("Dungeon");

		chambers = new ArrayList<Chamber>();
		passages = new ArrayList<Passage>();
		doors = new ArrayList<Door>();

		testRooms = new ArrayList<TestRoom>();
		/*
		 * 
		 */
		Chamber chamber = Chamber.makeChamber(this, Default.MIDPOINT, Orientation.random());
		chamber.checkForDoors(5);
		chambers.add(chamber);
		update();

		// Orientation o = Orientation.NORTH;
		// Door door = Door.makeDoor(this, Default.MIDPOINT, o);
		// doors.add(door);
		// door.advance();
		// update();

		/*
		 * LAST STEPS (IN ORDER)
		 */
		setResizable(false);
		setSize(Default.MAX_HORIZONTAL, Default.MAX_VERTICAL);
		// setLocationRelativeTo(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/*
	 * INSTANCE METHODS
	 */
	public void paint(Graphics g) {

		g.setColor(Color.BLUE);
		if (Default.showGrid) {
			for (int i = WALL_LENGTH; i < Default.MAX_HORIZONTAL; i += WALL_LENGTH)
				g.drawLine(i, 0, i, Default.MAX_VERTICAL);

			for (int i = WALL_LENGTH; i < Default.MAX_VERTICAL; i += WALL_LENGTH)
				g.drawLine(0, i, Default.MAX_HORIZONTAL, i);
		}

		for (TestRoom el : testRooms)
			el.paint(g);

		for (Chamber el : chambers)
			el.paint(g);

		for (Passage el : passages)
			el.paint(g);

		for (Door el : doors)
			el.paint(g);

		g.setColor(Color.RED);
		// cursor.paint(g);
	}

	private void update() {
		while (mappedArea() / Default.TOTAL_AREA < Default.AREA_TO_MAP) {
			advancePassages();
			openDoors();

			if (unopenedDoors() != true && unadvancedPassages() != true)
				break;
		}

		System.out.println(mappedArea());
		System.out.println(1.0 * mappedArea() / Default.TOTAL_AREA);
	}

	private boolean unopenedDoors() {
		boolean unopened = false;
		for (Door el : doors) {
			if (el.advanced != true) {
				unopened = true;
				break;
			}
		}

		return unopened;
	}

	private boolean unadvancedPassages() {
		boolean unadvanced = false;
		for (Passage el : passages) {
			if (el.advanced != true) {
				unadvanced = true;
				break;
			}
		}

		return unadvanced;
	}

	private void openDoors() {
		int index = 0, length = doors.size();

		do {
			while (index < length) {
				Door d = doors.get(index);

				if (d.advanced != true)
					d.advance();

				++index;
			}

			length = doors.size();
		} while (index < length);
	}

	private void advancePassages() {
		int index = 0, length = passages.size();

		do {
			while (index < length) {
				Passage p = passages.get(index);

				if (p.isDeadEnd != true)
					p.advance();

				++index;
			}

			length = passages.size();
		} while (index < length);
	}

	private int mappedArea() {
		int area = 0;

		for (Chamber el : chambers)
			area += el.area();

		for (Passage el : passages)
			area += el.area();

		return area;
	}

}
