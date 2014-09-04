package hexapaper.gui;

import hexapaper.source.BPolygon;
import hexapaper.source.HPSklad;

import java.awt.Polygon;
import java.util.ArrayList;

import core.Location;

public class Gprvky {

	HPSklad sk = HPSklad.getInstance();

	public BPolygon emptyHexagon(Location loc) {
		Polygon sprite = new Polygon();
		for (int i = 0; i < 6; i++) {
			sprite.addPoint((int) Math.round(loc.getX() + Math.cos(Math.toRadians(60 * i)) * sk.RADIUS),
					(int) Math.round(loc.getY() + Math.sin(Math.toRadians(60 * i)) * sk.RADIUS));
		}

		return new BPolygon(sprite);
	}

	public ArrayList<BPolygon> entity(Location loc) {
		ArrayList<BPolygon> sprite = new ArrayList<BPolygon>();
		Polygon poly = new Polygon();
		int cislo = loc.getDir() * 60;
		poly.addPoint(loc.getX() + pol2kar(-60 + cislo, sk.RADIUS, 0), loc.getY() + pol2kar(-60 + cislo, sk.RADIUS, 1));
		poly.addPoint(loc.getX() + pol2kar(-120 + cislo, sk.RADIUS, 0), loc.getY() + pol2kar(-120 + cislo, sk.RADIUS, 1));
		poly.addPoint(loc.getX() + pol2kar(-90 + cislo, sk.RADIUS / 2, 0), loc.getY() + pol2kar(-90 + cislo, sk.RADIUS / 2, 1));
		sprite.add(new BPolygon(poly, true));
		sprite.add(emptyHexagon(loc));
		return sprite;
	}

	public BPolygon wall(Location loc) {
		return emptyHexagon(loc).setFilled();
	}

	public ArrayList<BPolygon> artefact(Location loc) {
		ArrayList<BPolygon> arr = new ArrayList<BPolygon>();
		arr.add(emptyHexagon(loc));
		Polygon sprite = new Polygon();
		double newRadius = sk.RADIUS * 0.85;
		for (int i = 0; i < 6; i++) {
			sprite.addPoint((int) Math.round(loc.getX() + Math.cos(Math.toRadians(60 * i)) * newRadius),
					(int) Math.round(loc.getY() + Math.sin(Math.toRadians(60 * i)) * newRadius));
		}
		arr.add(new BPolygon(sprite));
		return arr;
	}

	private int pol2kar(int uh, int rad, int xy) {
		if (xy == 0) {
			return (int) Math.round(rad * Math.cos(Math.toRadians(uh)));
		} else if (xy == 1) {
			return (int) Math.round(rad * Math.sin(Math.toRadians(uh)));
		}
		System.err.println("Gprvky.pol2kal(uhel, radius, souradnice) Chyba je v souradnici.");
		System.exit(-1);
		return -1;
	}

}
