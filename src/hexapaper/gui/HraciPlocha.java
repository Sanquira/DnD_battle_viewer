package hexapaper.gui;

import hexapaper.entity.FreeSpace;
import hexapaper.entity.HPEntity;
import hexapaper.source.BPolygon;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.prvekkNN;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.Grids;
import core.Location;

public class HraciPlocha extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HPSklad sk = HPSklad.getInstance();
	HPEntity cursor = null;
	public int oldIdx = -1;

	int fontSize = (int) Math.round(sk.c.RADIUS * 0.75);

	public HraciPlocha() {
		init(0,0,0);
	}
	public HraciPlocha(int gridSl, int gridRa, int RADIUS){
		init(gridSl, gridRa, RADIUS);
	}

	public void init(int gridSl, int gridRa, int RADIUS) {
		setPreferredSize(new Dimension((int) Math.round((Math.round(gridSl / 2.0) * 2 + ((int) gridSl / 2.5) + 2) * RADIUS),
				(int) Math.round(Math.cos(Math.toRadians(30)) * RADIUS * 2 * (gridRa + 0.5))));
		setBackground(Color.black);
		sk.souradky = genGrid(gridSl, gridRa);
	}

	private ArrayList<HPEntity> genGrid(int sloupcu, int radku) {
		ArrayList<HPEntity> grid = new ArrayList<HPEntity>();
		int[][] souradky = Grids.gridHexa(sloupcu, radku, sk.c.RADIUS);
		for (int i = 0; i < souradky.length; i++) {
			souradky[i][0] += sk.c.RADIUS;
			souradky[i][1] += Math.round(sk.c.RADIUS * Math.cos(Math.toRadians(30)));
			grid.add(new FreeSpace(new Location(souradky[i][0], souradky[i][1], 0)));
		}
		return grid;
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setFont(getFont().deriveFont((float) fontSize).deriveFont(Font.BOLD));
		FontMetrics fm = g.getFontMetrics();
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		createBackground(g2, g, fm, sk.c.gridSl, sk.c.gridRa, sk.c.RADIUS);
	}
//	@Override
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		g.setFont(getFont().deriveFont((float) fontSize).deriveFont(Font.BOLD));
//		FontMetrics fm = g.getFontMetrics();
//		Graphics2D g2 = (Graphics2D) g;
//		g2.setStroke(new BasicStroke(2));
//		for (HPEntity ent : sk.souradky) {
//			if (sk.scroll.getViewport().getViewRect().contains(ent.loc.getPoint())) {
//				g2.setColor(ent.background);
//				g2.fillPolygon(new Gprvky().emptyHexagon(ent.loc));
//				g2.setColor(Color.black);
//				g2.drawString(ent.tag, Math.round(ent.loc.getX() - (fm.getStringBounds(ent.tag, g).getWidth() / 2)),
//						Math.round(ent.loc.getY() + (fm.getStringBounds(ent.tag, g).getHeight() / 3)));
//				fm.getStringBounds(ent.tag, g).getWidth();
//				for (BPolygon poly : ent.prvek) {
//					if (!poly.isFilled) {
//						g2.drawPolygon(poly);
//					} else {
//						g2.fillPolygon(poly);
//					}
//				}
//			}
//		}
//		if (cursor != null) {
//			g2.setColor(cursor.background);
//			if (!sk.isConnected && !sk.isPJ) {
//				g2.fillPolygon(new Gprvky().emptyHexagon(cursor.loc));
//				g2.setColor(Color.black);
//				g2.drawString(cursor.tag, Math.round(cursor.loc.getX() - (fm.getStringBounds(cursor.tag, g).getWidth() / 2)),
//						Math.round(cursor.loc.getY() + (fm.getStringBounds(cursor.tag, g).getHeight() / 3)));
//				fm.getStringBounds(cursor.tag, g).getWidth();
//			}
//			for (BPolygon poly : cursor.prvek) {
//				if (!poly.isFilled) {
//					g2.drawPolygon(poly);
//				} else {
//					g2.fillPolygon(poly);
//				}
//			}
//		}
//	}

	private void createBackground(Graphics2D g2, Graphics g, FontMetrics fm, int columns, int rows, int radius) {
		Integer x,y;
		Point point;
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				x = radius;
				y = (int) Math.round(radius * Math.cos(Math.toRadians(30)));
				if (i % 2 == 0) {
					x += ((i / 2) * 3 * radius);
					y += (int) Math.round(j * 2 * Math.cos(Math.toRadians(30)) * radius);
				} else {
					x += (int) ((1.5 + (i / 2) * 3) * radius);
					y += (int) Math.round(radius * Math.cos(Math.toRadians(30)) +
							j * 2 * Math.cos(Math.toRadians(30)) * radius);
				}
				point = new Point(x,y);
				if (sk.scroll.getViewport().getViewRect().contains(point)) {
					BPolygon poly = new Gprvky().emptyHexagon(point);
					g2.setColor(Color.white);
					g2.fillPolygon(poly);
					g2.setColor(Color.black);
					g2.drawPolygon(poly);
				}
			}
		}	
		
	}
//	public static int[][] gridHexa(int sloupcu, int radku, int radius) {
//		int[][] souradky = new int[sloupcu * radku][2];
//		int l = 0;
//		for (int i = 0; i < sloupcu; i++) {
//			for (int j = 0; j < radku; j++) {
//				if (i % 2 == 0) {
//					souradky[l][0] = (i / 2) * 3 * radius;
//					souradky[l][1] = (int) Math.round(j * 2 * Math.cos(Math.toRadians(30)) * radius);
//				} else {
//					souradky[l][0] = (int) ((1.5 + (i / 2) * 3) * radius);
//					souradky[l][1] = (int) Math.round(radius * Math.cos(Math.toRadians(30)) +
//							j * 2 * Math.cos(Math.toRadians(30)) * radius);
//				}
//				l++;
//			}
//		}
//		return souradky;
//	}
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
		for (HPEntity ent : sk.souradky) {
			if (ent.loc.getX() == idx.get(0).getX1() && ent.loc.getY() == idx.get(0).getY1() && ent.isRotateable) {
				if ((sk.isConnected && sk.isPJ) || !sk.isConnected) {
					ent.loc.setDir(smer);
					ent.recreateGraphics();
					Integer[] obj = { ent.loc.getX(), ent.loc.getY(), ent.loc.getDir() };
					// System.out.println(obj[0]+":"+obj[1]+":"+obj[2]);
					sk.send(obj, "rotateEnt",true);
					// System.out.println(ent.loc.getDir());
					return;
				}
				System.out.println("No permission: Rotate");
				break;
			}
		}
		//this.revalidate();
		//this.repaint();
	}
	public boolean insertEntity(Integer idx, HPEntity type, Boolean hard) {
		if (type == null) {
			return false;
		}
		
		HPEntity old = sk.souradky.get(idx).clone();
		HPEntity nw = type.clone();
		if (old.isColidable || hard) {
			Location loc = old.loc;
//			System.out.println(loc.getX());
//			System.out.println(loc.getY());
			nw.loc.setX(loc.getX());
			nw.loc.setY(loc.getY());
			if(sk.colorAdd){
				//System.out.println("barvím");
				nw.setBcg(sk.color);
			}
			nw.recreateGraphics();
			sk.souradky.set(idx, nw);
			if (sk.isConnected && sk.isPJ && type != null) {
				Object[] o = { idx, nw };
				sk.client.send(o, "insertEnt");
			}
			if (!sk.repeatableInsert) {
				type = null;
				sk.setupInserting(null, false);
				//revalidate();
				//repaint();
			}
			return true;
		}
		 else {
			if (oldIdx != -1) {
				insertEntity(oldIdx, nw, true);
				oldIdx = -1;
				return false;
			}
		}
		//revalidate();
		//repaint();
		return false;

	}

	public void drawCursor(double x, double y) {
		if (sk.insertingEntity) {
			cursor = sk.insertedEntity.clone();
			cursor.setBcg(Color.white);
			cursor.loc = new Location((int) x, (int) y, cursor.loc.getDir());
			//cursor.background=Color.BLACK;
			cursor.recreateGraphics();
			revalidate();
			repaint();
		} else if(cursor != null) {
			cursor = null;
			revalidate();
			repaint();
		}
	}
	public void moveEntity(int oldIdx, int newIdx){
		System.out.println(oldIdx);
		System.out.println(newIdx);		
		HPEntity old = sk.souradky.get(oldIdx);
		HPEntity nw = sk.souradky.get(newIdx);
		if(nw instanceof FreeSpace){
			FreeSpace free = new FreeSpace(old.loc);
			old.loc = nw.loc;
			old.recreateGraphics();
			free.recreateGraphics();
			sk.souradky.set(oldIdx, free);
			sk.souradky.set(newIdx, old);
		}
		sk.setupInserting(null, false);
		drawCursor(-1, -1);
		revalidate();
		this.repaint();
	}
	public void saveEntity(int idx) {
		// System.out.println(sk.souradky.get(idx));
		if (sk.souradky.get(idx) instanceof FreeSpace) {
			sk.setupInserting(null, false);
			return;
		}
		HPEntity ent = sk.souradky.get(idx);
		sk.setupInserting(ent, false);
		Location loc = ent.loc;
		oldIdx = idx;
		drawCursor(loc.getX(), loc.getY());

	}

	public void releaseEntity(int idx) {
		HPEntity en = sk.insertedEntity;
		if (insertEntity(idx, en, false) && idx != oldIdx) {
			System.out.println("Nové místo");
//			System.out.println(en.loc.getX());
//			System.out.println(en.loc.getY());
			System.out.println(oldIdx);
			System.out.println(idx);
			insertEntity(oldIdx, new FreeSpace(sk.LocDontCare), true);
		} 
		drawCursor(-1, -1);
		revalidate();
		this.repaint();
	}
	
}
