package hexapaper.gui;

import hexapaper.entity.Entity;
import hexapaper.source.BPolygon;
import hexapaper.source.Location;
import hexapaper.source.Sklad;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Gprvky {

	Sklad sk = Sklad.getInstance();

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
		double newRadius = sk.RADIUS *0.85;
		for (int i = 0; i < 6; i++) {
			sprite.addPoint((int) Math.round(loc.getX() + Math.cos(Math.toRadians(60 * i)) * newRadius),
					(int) Math.round(loc.getY() + Math.sin(Math.toRadians(60 * i)) * newRadius));
		}
		arr.add(new BPolygon(sprite));
		return arr;
	}

	public Cursor zmenKurzor(Entity tvar) {
		if (tvar == null) {
			return Cursor.getDefaultCursor();
		}
		Location loc = new Location(sk.RADIUS, sk.RADIUS, 0);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension dim = kit.getBestCursorSize(sk.RADIUS * 2, sk.RADIUS * 2);
		BufferedImage buffered = new BufferedImage(dim.width, dim.height, BufferedImage.TRANSLUCENT);
		Graphics2D g = buffered.createGraphics();
		g.setFont(g.getFont().deriveFont((float) sk.RADIUS).deriveFont(Font.BOLD));
		FontMetrics fm = g.getFontMetrics();
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		// g2.setColor(tvar.background);
		// g2.fillPolygon(new Gprvky().emptyHexagon(Sklad.LocDontCare).poly);
		g2.setColor(Color.black);
		g2.drawString(tvar.tag, Math.round(loc.getX() - (fm.getStringBounds(tvar.tag, g).getWidth() / 2)),
				Math.round(loc.getY() + (fm.getStringBounds(tvar.tag, g).getHeight() / 3)));
		fm.getStringBounds(tvar.getText(), g).getWidth();
		for (BPolygon poly : tvar.prvek) {
			if (!poly.isFilled) {
				g2.drawPolygon(poly);
			} else {
				g2.fillPolygon(poly);
			}
		}
		int centerX = (dim.width - 1) / 2;
		int centerY = (dim.height - 1) / 2;
		Cursor cursor = kit.createCustomCursor(buffered, new Point(centerX, centerY), "myCursor");
		return cursor;
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
