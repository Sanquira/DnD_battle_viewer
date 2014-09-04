package hexapaper.source;

import hexapaper.hexapaper;
import hexapaper.entity.HPEntity;
import hexapaper.entity.FreeSpace;
import hexapaper.file.LoadFile;
import hexapaper.gui.Gprvky;
import hexapaper.gui.HraciPlocha;
import hexapaper.gui.PraveMenu;

import java.util.ArrayList;

import core.Location;

public class HPSklad {

	private static HPSklad instance = null;

	public Gprvky prvky;
	public HraciPlocha hraciPlocha;
	public PraveMenu RMenu;

	public int RADIUS = 25;
	public int gridSl = 0;
	public int gridRa = 0;
	public ArrayList<HPEntity> souradky;
	public boolean insertingEntity = false;
	public Location LocDontCare = new Location(RADIUS, RADIUS, 0);
	public ArrayList<HPEntity> databazePostav = new ArrayList<>();
	public ArrayList<HPEntity> databazeArtefaktu = new ArrayList<>();
	public HPEntity insertedEntity;
	public boolean hidePlayerColor = false;
	public boolean hideNPCColor = false;
	public boolean repeatableInsert = false;
	public boolean canEvent = false;

	public final String VERSION = "v0.1";

	protected HPSklad() {
	}

	public void init() {
		new LoadFile();
		hraciPlocha = new HraciPlocha();
		
		prvky = new Gprvky();
		RMenu = new PraveMenu();
		
	}

	public static HPSklad getInstance() {
		if (instance == null) {
			instance = new HPSklad();
		}
		return instance;
	}

	public void setupInserting(HPEntity insert, boolean repeat) {
		if (insert == null) {
			insertedEntity = null;
			insertingEntity = false;
			repeatableInsert = false;
		} else {
			insertedEntity = insert;
			insertingEntity = true;
			repeatableInsert = repeat;
		}
	}

	public void initLoad(ArrayList<HPEntity> souradky) {
		hraciPlocha = new HraciPlocha();
		for (int i = 0; i < souradky.size(); i++) {
			if (souradky.get(i) instanceof FreeSpace) {
			} else {
				//System.out.println(i);
				hraciPlocha.insertEntity(i, souradky.get(i), true);
			}
		}
		odblokujListenery();
		hexapaper.frm.repaint();
	}

	public void odblokujListenery() {
		canEvent = true;
	}

	public static class prvekkNN implements Cloneable {
		private double x1, y1, vzd;
		private int idx;

		public prvekkNN(int index, double xp, double yp, double dist) {
			x1 = xp;
			y1 = yp;
			vzd = dist;
			idx = index;
		}

		public double getX1() {
			return x1;
		}

		public void setX1(double x1) {
			this.x1 = x1;
		}

		public double getY1() {
			return y1;
		}

		public void setY1(double y1) {
			this.y1 = y1;
		}

		public double getVzd() {
			return vzd;
		}

		public void setVzd(double vzd) {
			this.vzd = vzd;
		}

		public int getIdx() {
			return idx;
		}

		public void setIdx(int idx) {
			this.idx = idx;
		}

		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

		public String toString() {
			return idx + ", " + x1 + ", " + y1 + ", " + vzd + "; ";
		}
	}

	public static class PropPair implements Cloneable {
		public String name;
		public String value;

		public PropPair(String name, String value) {
			this.name = name;
			this.value = value;
		}

		@Override
		public String toString() {
			return name + ": " + value;
		}

		@Override
		public PropPair clone() throws CloneNotSupportedException {
			return (PropPair) super.clone();
		}

	}

}