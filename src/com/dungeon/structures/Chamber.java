package com.dungeon.structures;

import java.awt.Color;
import java.awt.Graphics;

import com.dungeon.geometry.*;
import com.dungeon.misc.Dice;

import model.Dungeon;

public class Chamber extends Polygon {
	private Dungeon dungeon;
	private boolean isLargeRoom;
	private boolean[] edges;

	/*
	 * CONSTRUCTORS
	 */
	public Chamber(Dungeon dungeon, Point origin, Orientation orient, int length, int height) {
		this(origin, length, height);

		this.dungeon = dungeon;
		this.isLargeRoom = (length * height > 1500) ? true : false;
		this.edges = new boolean[numberOfSegments()];

		int doors = Door.numberOfExits(this);
		for (int i = 0; i < doors; ++i) {
			// TODO - place doors
		}

		System.out.println(orient.toString());
		System.out.println(isLargeRoom);
		System.out.println(edges.length);
		System.out.println(doors);
	}

	public Chamber(Point origin, int length, int height) {
		super(origin, length, height);
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

	public int perimeter() {
		return (2 * length + 2 * height);
	}
	
	public int numberOfSegments() {
		return perimeter() / Dungeon.WALL_LENGTH;
	}
	/*
	 * STATIC METHODS
	 */
	public static boolean[] checkForDoors(boolean[] array) {
		return array;
	}

	public static Chamber makeChamber(Dungeon dungeon, Point origin, Orientation orient) {
		int dice = Dice.roll(20);
		int length = 10, height = 10;

		switch (dice) {
		case 1:
		case 2:
			length = 20;
			height = 20;
			break;
		case 3:
		case 4:
			length = 30;
			height = 30;
			break;
		case 5:
		case 6:
			length = 40;
			height = 40;
			break;
		case 7:
		case 8:
		case 9:
			length = 20;
			height = 30;
			break;
		case 10:
		case 11:
		case 12:
			length = 30;
			height = 40;
			break;
		case 13:
		case 14:
			length = 40;
			height = 50;
			break;
		case 15:
			length = 50;
			height = 80;
			break;
		case 16:
			length = 30;
			height = 30;
			break;
		case 17:
			length = 50;
			height = 50;
			break;
		case 18:
			length = 40;
			height = 40;
			break;
		case 19:
			length = 60;
			height = 60;
			break;
		case 20:
			length = 40;
			height = 60;
			break;
		}

		return new Chamber(dungeon, origin.clone(), orient, (Orientation.isNorthOrSouth(orient)) ? length : height,
				(Orientation.isNorthOrSouth(orient)) ? height : length);
	}

}
