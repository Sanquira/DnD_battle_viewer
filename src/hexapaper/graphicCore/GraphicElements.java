package hexapaper.graphicCore;

import hexapaper.source.BPolygon;
import hexapaper.source.HPSklad;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import core.Location;

public class GraphicElements {

	public static PolygonD emptyHexagon() {
		PolygonD sprite = new PolygonD();
		for (int i = 0; i < 4; i++) {
			sprite.addPoint(Math.cos(Math.toRadians(60 * i)), Math.sin(Math.toRadians(60 * i)));
		}
		return sprite;
	}

	// public BPolygon emptyHexagon(Location loc) {
	// return emptyHexagon(loc.getPoint());
	// }
	//
	// public ArrayList<BPolygon> entity(Location loc) {
	// ArrayList<BPolygon> sprite = new ArrayList<BPolygon>();
	// Polygon poly = new Polygon();
	// int cislo = loc.getDir() * 60;
	// poly.addPoint(loc.getX() + pol2kar(-60 + cislo, sk.c.RADIUS, 0), loc.getY() + pol2kar(-60 + cislo, sk.c.RADIUS, 1));
	// poly.addPoint(loc.getX() + pol2kar(-120 + cislo, sk.c.RADIUS, 0), loc.getY() + pol2kar(-120 + cislo, sk.c.RADIUS, 1));
	// poly.addPoint(loc.getX() + pol2kar(-90 + cislo, sk.c.RADIUS / 2, 0), loc.getY() + pol2kar(-90 + cislo, sk.c.RADIUS / 2, 1));
	// sprite.add(new BPolygon(poly, true));
	// sprite.add(emptyHexagon(loc));
	// return sprite;
	// }
	//
	// public BPolygon wall(Location loc) {
	// return emptyHexagon(loc).setFilled();
	// }
	//
	// public ArrayList<BPolygon> artefact(Location loc) {
	// ArrayList<BPolygon> arr = new ArrayList<BPolygon>();
	// arr.add(emptyHexagon(loc));
	// Polygon sprite = new Polygon();
	// double newRadius = sk.c.RADIUS * 0.85;
	// for (int i = 0; i < 6; i++) {
	// sprite.addPoint((int) Math.round(loc.getX() + Math.cos(Math.toRadians(60 * i)) * newRadius),
	// (int) Math.round(loc.getY() + Math.sin(Math.toRadians(60 * i)) * newRadius));
	// }
	// arr.add(new BPolygon(sprite));
	// return arr;
	// }


}
