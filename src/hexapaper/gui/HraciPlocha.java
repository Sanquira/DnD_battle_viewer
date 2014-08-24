package hexapaper.gui;

import hexapaper.entity.Entity;
import hexapaper.entity.FreeSpace;
import hexapaper.source.BPolygon;
import hexapaper.source.Location;
import hexapaper.source.Sklad;
import hexapaper.source.Sklad.prvekkNN;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class HraciPlocha extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Sklad sk = Sklad.getInstance();
	Entity cursor = null;

	public HraciPlocha() {
		init();
	}

	public void init() {
		setPreferredSize(new Dimension((int) Math.round((Math.round(sk.gridSl / 2.0) * 2 + ((int) sk.gridSl / 2.5) + 2) * sk.RADIUS),
				(int) Math.round(Math.cos(Math.toRadians(30)) * sk.RADIUS * 2 * (sk.gridRa + 0.5))));
		setBackground(Color.black);
		sk.souradky = genGrid(sk.gridSl, sk.gridRa);
	}

	private ArrayList<Entity> genGrid(int sloupcu, int radku) {
		ArrayList<Entity> grid = new ArrayList<Entity>();
		int[][] souradky = new int[sloupcu * radku][2];
		int l = 0;
		for (int i = 0; i < sloupcu; i++) {
			for (int j = 0; j < radku; j++) {
				if (i % 2 == 0) {
					souradky[l][0] = (i / 2) * 3 * sk.RADIUS;
					souradky[l][1] = (int) Math.round(j * 2 * Math.cos(Math.toRadians(30)) * sk.RADIUS);
				} else {
					souradky[l][0] = (int) ((1.5 + (i / 2) * 3) * sk.RADIUS);
					souradky[l][1] = (int) Math.round(sk.RADIUS * Math.cos(Math.toRadians(30)) +
							j * 2 * Math.cos(Math.toRadians(30)) * sk.RADIUS);
				}
				l++;
			}
		}
		for (int i = 0; i < souradky.length; i++) {
			souradky[i][0] += sk.RADIUS;
			souradky[i][1] += Math.round(sk.RADIUS * Math.cos(Math.toRadians(30)));
			grid.add(new FreeSpace(new Location(souradky[i][0], souradky[i][1], 0)));
		}
		return grid;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(getFont().deriveFont((float) sk.RADIUS).deriveFont(Font.BOLD));
		FontMetrics fm = g.getFontMetrics();
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		for (Entity ent : sk.souradky) {
			g2.setColor(ent.background);
			g2.fillPolygon(new Gprvky().emptyHexagon(ent.loc));
			g2.setColor(Color.black);
			g2.drawString(ent.tag, Math.round(ent.loc.getX() - (fm.getStringBounds(ent.tag, g).getWidth() / 2)),
					Math.round(ent.loc.getY() + (fm.getStringBounds(ent.tag, g).getHeight() / 3)));
			fm.getStringBounds(ent.tag, g).getWidth();
			for (BPolygon poly : ent.prvek) {
				if (!poly.isFilled) {
					g2.drawPolygon(poly);
				} else {
					g2.fillPolygon(poly);
				}
			}
		}
		if (cursor != null) {
			g2.setColor(cursor.background);
			g2.fillPolygon(new Gprvky().emptyHexagon(cursor.loc));
			g2.setColor(Color.black);
			g2.drawString(cursor.tag, Math.round(cursor.loc.getX() - (fm.getStringBounds(cursor.tag, g).getWidth() / 2)),
					Math.round(cursor.loc.getY() + (fm.getStringBounds(cursor.tag, g).getHeight() / 3)));
			fm.getStringBounds(cursor.tag, g).getWidth();
			for (BPolygon poly : cursor.prvek) {
				if (!poly.isFilled) {
					g2.drawPolygon(poly);
				} else {
					g2.fillPolygon(poly);
				}
			}
		}
	}

	public int countDir(ArrayList<prvekkNN> prvky) {
		int[] u = new int[2];
		u[0] = (int) Math.signum(prvky.get(1).getX1() - prvky.get(0).getX1());
		u[1] = (int) Math.signum(prvky.get(1).getY1() - prvky.get(0).getY1());

		if (u[1] == 1) {
			switch (u[0]) {
			case -1:
				return 4;
			case 0:
				return 3;
			case 1:
				return 2;
			}
		}
		if (u[1] == -1) {
			switch (u[0]) {
			case -1:
				return 5;
			case 0:
				return 0;
			case 1:
				return 1;
			}
		}

		System.err.println("HraciPlocha.countDir(nejblizsi prvky) Chyba je ve vektoru. Vysel nula i kdyz nemel.");
		System.exit(-1);
		return -1;
	}

	public void rotateEntity(ArrayList<prvekkNN> idx) {
		int smer = countDir(idx);
		for (Entity ent : sk.souradky) {
			if (ent.loc.getX() == idx.get(0).getX1() && ent.loc.getY() == idx.get(0).getY1() && ent.isRotateable) {
				ent.loc.setDir(smer);
				ent.recreateGraphics();
				break;
			}
		}
		this.repaint();
	}

	public void insertEntity(ArrayList<prvekkNN> idx, Entity type, boolean hard) {
		if (type == null) {
			return;
		}
		for (Entity ent : sk.souradky) {
			if (ent.loc.getX() == idx.get(0).getX1() && ent.loc.getY() == idx.get(0).getY1()
					&& (ent.isColidable || hard)) {
				type.loc.setX(ent.loc.getX());
				type.loc.setY(ent.loc.getY());
				type.recreateGraphics();
				sk.souradky.set(idx.get(0).getIdx(), type.clone());
				if (!sk.repeatableInsert) {
					type = null;
					sk.setupInserting(null, false);
				}
				repaint();
				break;
			}
		}
	}

	public void insertEntity(int idx, Entity type) {
		Location loc = sk.souradky.get(idx).loc;
		type.loc.setX(loc.getX());
		type.loc.setY(loc.getY());
		sk.souradky.set(idx, type);
		type.recreateGraphics();
		repaint();
	}

	public void drawCursor(int x, int y) {
		if (sk.insertingEntity) {
			cursor = sk.insertedEntity.clone();
			cursor.loc = new Location(x, y, cursor.loc.getDir());
			cursor.recreateGraphics();
		} else {
			cursor = null;
		}
		repaint();
	}

	public void saveEntity(int idx) {
		sk.setupInserting(sk.souradky.get(idx).clone(), false);
		Location loc = sk.souradky.get(idx).clone().loc;
		insertEntity(idx, new FreeSpace(loc));
		drawCursor(loc.getX(), loc.getY());

	}

	public void releaseEntity(int idx) {
		insertEntity(idx, sk.insertedEntity.clone());
		sk.setupInserting(null, false);

	}
}
